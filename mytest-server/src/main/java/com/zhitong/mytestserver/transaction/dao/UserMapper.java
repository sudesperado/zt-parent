package com.zhitong.mytestserver.transaction.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhitong.mytestserver.transaction.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author : subs
 * @Project: sbs-parent
 * @Package com.zhitong.feignserver.transaction.dao
 * @Description:
 * @date Date : 2020年11月27日 14:24
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {
}
