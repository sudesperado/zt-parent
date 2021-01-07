package com.zhitong.mytestserver.service;

import com.zhitong.mytestserver.model.PunchCard;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhitong.mytestserver.model.Result;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author su
 * @since 2021-01-04
 */
public interface IPunchCardService extends IService<PunchCard> {

    Result savePunchCard(List<PunchCard> punchCardList);

    Result queryAll(String userId,Integer week);

    Result getOver(List<String> idList);
}
