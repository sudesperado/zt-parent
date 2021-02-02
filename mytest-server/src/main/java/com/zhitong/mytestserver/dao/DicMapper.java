package com.zhitong.mytestserver.dao;

import com.zhitong.mytestserver.model.Dic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author su
 * @since 2021-02-01
 */
@Repository
public interface DicMapper extends BaseMapper<Dic> {

    @Select("SELECT * from dic WHERE parent_id = (SELECT id FROM dic WHERE name = 'tag');")
    List<Dic> selectByTag();
}
