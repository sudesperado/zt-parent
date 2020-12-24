package com.zhitong.mytestserver.netty;

import com.zhitong.mytestserver.model.netty.RpcData;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.nio.charset.Charset;
import java.util.UUID;

@Component
public class NettyClient {
    private Logger logger = LoggerFactory.getLogger(NettyClient.class);

    @Value("${netty.server.port}")
    private String nettyServerPort;

    @Value("${netty.server.host}")
    private String nettyServerHost;

    private ClientHandler clientHandler;

    private Bootstrap bootstrap;

    private Thread nettyThread;

    @Autowired
    private void initialize() throws InterruptedException {
        //启动任务扫描线程
        nettyThread = new Thread(() -> start());
        nettyThread.start();
    }

    private void start(){
        //先休眠５秒钟，等系统启动好再连接netty
        try {
            Thread.sleep(1000 * 5);
        }
        catch (Exception ex) {};
        while (true) {
            EventLoopGroup group = new NioEventLoopGroup();
            clientHandler = new ClientHandler();
            try {
                bootstrap = new Bootstrap();
                bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new StringEncoder(Charset.forName("UTF-8")));
                        // 解决拆包、粘包问题，设置特殊分隔符
                        ByteBuf buf = Unpooled.copiedBuffer(RpcData.Netty_Delimiter.getBytes());
                        pipeline.addLast(new DelimiterBasedFrameDecoder(1024 * 1024 * 100, buf));
                        pipeline.addLast(new StringDecoder(Charset.forName("UTF-8")));
                        //客户端业务处理类
                        pipeline.addLast(clientHandler);
                        }
                    });
                ChannelFuture future = bootstrap.connect(nettyServerHost, Integer.parseInt(nettyServerPort)).sync().addListener(
                    (GenericFutureListener<ChannelFuture>) future1 -> {
                        logger.info("连接Netty服务完成！result：{}, 主机：{}, 端口：{}", future1.isSuccess(), nettyServerHost, nettyServerPort);
                    }
                );
                future.channel().closeFuture().sync();
            }
            catch (Exception ex){
                logger.error("Netty client connect 异常", ex);
            }
            finally {
                group.shutdownGracefully();
                //10秒后重试
                try {
                    Thread.sleep(10 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 提取节点名称
     * @return
     */
    private String prepareNodeName(){
        try {
            //返回IP地址
            InetAddress address = InetAddress.getLocalHost();
            return address.getHostAddress();
        }
        catch (Exception ex){
        }
        return UUID.randomUUID().toString();
    }

}
