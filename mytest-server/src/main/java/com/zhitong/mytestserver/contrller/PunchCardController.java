package com.zhitong.mytestserver.contrller;

import com.zhitong.mytestserver.model.PunchCard;
import com.zhitong.mytestserver.model.Result;
import com.zhitong.mytestserver.service.IPunchCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

/**
 * @author : subs
 * @Project: zt-parent
 * @Package com.zhitong.mytestserver.contrller
 * @Description:
 * @date Date : 2021年01月04日 10:22
 */
@RestController
@RequestMapping("/api/punchCard")
public class PunchCardController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IPunchCardService punchCardService;

    /**
     * 打卡记录新增或修改
     * @param punchCardList
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public Result savePunchCard(@RequestBody List<PunchCard> punchCardList){
        try {
            punchCardList.stream().forEach(item->{
                if (item.getStart()!=null && item.getEnd()!=null){
                    String st = new SimpleDateFormat("yyyy-MM-dd").format(item.getStart());
                    String en = new SimpleDateFormat("yyyy-MM-dd").format(item.getEnd());
                    item.setPeriod(st+"~"+en);
                }
            });
            return punchCardService.savePunchCard(punchCardList);
        } catch (Exception e) {
            logger.error("添加打卡记录失败：{}",e);
            return Result.newInstance().filed("添加打卡记录失败!");
        }
    }

    /**
     * 查询列表
     * @param userId
     * @param week
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/queryAll",method = RequestMethod.GET)
    public Result queryAll(@RequestParam(value = "userId",required = false) String userId,
                           @RequestParam(value = "week",required = false) Integer week){
        try {
            return punchCardService.queryAll(userId,week);
        } catch (Exception e) {
            logger.error("查询打卡记录失败：{}",e);
            return Result.newInstance().filed("查询打卡记录失败!");
        }
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/del",method = RequestMethod.GET)
    public Result del(@RequestParam(value = "ids") String ids){
        List<String> idList = Arrays.asList(ids.split(","));
        try {
            return punchCardService.getOver(idList);
        } catch (Exception e) {
            logger.error("批量删除失败：{}",e);
            return Result.newInstance().filed("批量删除失败!");
        }
    }


}
