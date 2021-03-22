package com.zhitong.mytestserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.google.common.collect.Lists;
import com.zhitong.mytestserver.model.PunchCard;
import com.zhitong.mytestserver.dao.PunchCardMapper;
import com.zhitong.mytestserver.model.Result;
import com.zhitong.mytestserver.service.IPunchCardService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author su
 * @since 2021-01-04
 */
@Service
public class PunchCardServiceImpl extends ServiceImpl<PunchCardMapper, PunchCard> implements IPunchCardService {

    @Autowired
    private PunchCardMapper punchCardMapper;

    @Override
    public Result<PunchCard> savePunchCard(List<PunchCard> punchCardList) {
        boolean b = this.saveOrUpdateBatch(punchCardList);
        if (!b) {
            return Result.newInstance().filed("添加打卡记录失败!");
        }
        return Result.newInstance().success("添加打卡记录成功", punchCardList);
    }

    @Override
    public Result queryAll(String userId, Integer week) {
        QueryWrapper<PunchCard> cardQueryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(userId)){
            Long uid = Long.valueOf(userId);
            cardQueryWrapper.eq("user_id", uid);
        }
        cardQueryWrapper.eq(week != null, "week", week);
        List<PunchCard> punchCardList = punchCardMapper.selectList(cardQueryWrapper);
        return Result.newInstance().success("查询打卡记录成功", punchCardList);
    }

    @Override
    public Result getOver(List<String> idList) {
        List<Long> ids = Lists.newArrayList();
        idList.stream().forEach(item -> ids.add(Long.valueOf(item)));
        boolean bool = this.removeByIds(ids);
        if (!bool) {
            return Result.newInstance().filed("批量删除失败!");
        }
        return Result.newInstance().success("批量删除成功", idList);
    }
}
