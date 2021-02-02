package com.zhitong.mytestserver.service;

import com.zhitong.mytestserver.model.Blog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhitong.mytestserver.model.Result;
import com.zhitong.mytestserver.model.reqVO.BlogReqVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author su
 * @since 2021-02-01
 */
public interface IBlogService extends IService<Blog> {

    Result saveBlog(Blog blog);

    Result queryById(Integer id);

    Result del(List<Integer> idList);

    Result queryPage(BlogReqVO blogReqVO);

    Result upload(Blog blog);

    Result queryFromES(String id);

    void delFromES(String id);

    Result queryPageFromES(BlogReqVO blogReqVO);

    Result createIndex();

}
