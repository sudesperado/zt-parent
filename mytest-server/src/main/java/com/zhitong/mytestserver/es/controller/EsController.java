package com.zhitong.mytestserver.es.controller;

import com.zhitong.feignserver.es.service.EsCrudService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : subs
 * @Project: sbs-parent
 * @Package com.zhitong.feignserver.es.controller
 * @Description:
 * @date Date : 2020年11月23日 13:55
 */
@RequestMapping("/api/es")
@RestController
public class EsController {

    @Autowired
    private EsCrudService esCrudService;

    @ApiOperation("创建索引")
    @RequestMapping(value = "/createIndex", method = RequestMethod.GET)
    public void createIndex() {
        esCrudService.createIndex();
    }

    @ApiOperation("新增")
    @RequestMapping(value = "/save", method = RequestMethod.GET)
    public void save() {
        esCrudService.saveDate();
    }

    @ApiOperation("查询")
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public void query() {
        esCrudService.queryDate();
    }
}
