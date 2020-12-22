package com.zhitong.mytestserver.es.service;

/**
 * @author : subs
 * @Project: sbs-parent
 * @Package com.zhitong.feignserver.es.service
 * @Description:
 * @date Date : 2020年11月23日 13:40
 */
public interface EsCrudService {
    void createIndex();

    void saveDate();

    void queryDate();

}
