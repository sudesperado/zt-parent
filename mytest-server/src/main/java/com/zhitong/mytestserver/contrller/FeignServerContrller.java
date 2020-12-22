package com.zhitong.mytestserver.contrller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : subs
 * @Project: sbs-parent
 * @Package com.zhitong.feignserver.contrller
 * @Description:
 * @date Date : 2020年12月04日 17:11
 */
@RestController
@RequestMapping("/hello/api")
public class FeignServerContrller {


    @RequestMapping(value = "/getStr",method = RequestMethod.GET)
    public void getString(){
        System.out.println("输出信息:哈哈哈");
    }
}
