package com.zhitong.mytestserver.contrller;

import com.google.common.collect.Lists;
import com.zhitong.mytestserver.model.Blog;
import com.zhitong.mytestserver.model.Result;
import com.zhitong.mytestserver.model.reqVO.BlogReqVO;
import com.zhitong.mytestserver.service.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author : subs
 * @Project: zt-parent
 * @Package com.zhitong.mytestserver.contrller
 * @Description:
 * @date Date : 2021年02月01日 11:06
 */
@RestController
@RequestMapping("/api/blog")
public class BlogController {

    @Autowired
    private IBlogService blogService;


    /**
     * 正式文本-保存至ES
     * @param blog
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public Result upload(@RequestBody Blog blog){
        try {
            return blogService.upload(blog);
        } catch (Exception e) {
            return Result.newInstance().filed(e.toString());
        }
    }

    /**
     * 正式文本-创建ES索引
     * @param
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/createIndex",method = RequestMethod.POST)
    public Result createIndex(){
        try {
            return blogService.createIndex();
        } catch (Exception e) {
            return Result.newInstance().filed(e.toString());
        }
    }

    /**
     * 正式文本-内容查询
     * @param id
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/queryFromES",method = RequestMethod.GET)
    public Result queryFromES(@RequestParam("id") String id){
        try {
            return blogService.queryFromES(id);
        } catch (Exception e) {
            return Result.newInstance().filed(e.toString());
        }
    }

    /**
     * 正式文本-删除
     * @param ids
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/delFromES",method = RequestMethod.GET)
    public Result delFromES(@RequestParam("ids") String ids){
        try {
            List<String> list = Arrays.asList(ids.split(","));
            for (String s : list) {
                blogService.delFromES(s);
            }
            return Result.newInstance().success("删除成功",ids);
        } catch (Exception e) {
            return Result.newInstance().filed(e.toString());
        }
    }

    /**
     * 正式文本-blog列表
     * @param blogReqVO
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/queryPageFromES",method = RequestMethod.POST)
    public Result queryPageFromES(@RequestBody BlogReqVO blogReqVO){
        try {
            return blogService.queryPageFromES(blogReqVO);
        } catch (Exception e) {
            return Result.newInstance().filed(e.toString());
        }
    }

    /**
     * 草稿箱-保存
     * @param blog
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public Result save(@RequestBody Blog blog){
        try {
            return blogService.saveBlog(blog);
        } catch (Exception e) {
           return Result.newInstance().filed(e.toString());
        }
    }

    /**
     * 草稿箱-内容查询
     * @param id
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/query",method = RequestMethod.GET)
    public Result query(@RequestParam("id") Integer id){
        try {
            return blogService.queryById(id);
        } catch (Exception e) {
            return Result.newInstance().filed(e.toString());
        }
    }

    /**
     * 草稿箱-删除草稿
     * @param ids
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/del",method = RequestMethod.GET)
    public Result del(@RequestParam("ids") String ids){
        try {
            List<String> list = Arrays.asList(ids.split(","));
            List<Integer> idList = Lists.newArrayList();
            for (String s : list) {
                idList.add(Integer.valueOf(s));
            }
            return blogService.del(idList);
        } catch (Exception e) {
            return Result.newInstance().filed(e.toString());
        }
    }

    /**
     * 草稿箱-blog列表
     * @param blogReqVO
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/queryPage",method = RequestMethod.POST)
    public Result queryPage(@RequestBody BlogReqVO blogReqVO){
        try {
            return blogService.queryPage(blogReqVO);
        } catch (Exception e) {
            return Result.newInstance().filed(e.toString());
        }
    }
}
