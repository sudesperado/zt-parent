package com.zhitong.mytestserver.es.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : subs
 * @Project: sbs-parent
 * @Package com.zhitong.feignserver.es
 * @Description:
 * @date Date : 2020年11月23日 11:46
 */
@Configuration
public class EsConfig {

    private RestHighLevelClient restHighLevelClient;

    @Value("${elasticsearch.address}")
    private String esAddress;

    @Bean
    public RestHighLevelClient getRestHighLevelClient(){
        String[] addressArr = esAddress.split(",");
        HttpHost[] httpHosts = new HttpHost[addressArr.length];
        for (int i = 0; i < addressArr.length; i++) {
            String address = addressArr[i];
            String[] hostAndPortArr = address.split(":");
            HttpHost httpHost = new HttpHost(hostAndPortArr[0], Integer.valueOf(hostAndPortArr[1]), "http");
            httpHosts[i] = httpHost;
        }

        return new RestHighLevelClient(RestClient.builder(httpHosts));
    }
}
