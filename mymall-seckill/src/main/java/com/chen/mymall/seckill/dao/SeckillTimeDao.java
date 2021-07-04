package com.chen.mymall.seckill.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.mymall.common.entity.SeckillTimeEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 *
 *
 * @author chenqiulu
 * @email 1281371382@qq.com
 * @date 2021-04-29 17:47:42
 */
@Mapper
public interface SeckillTimeDao extends BaseMapper<SeckillTimeEntity> {

    @Select("select * from seckill_time where end_time > #{time} limit 6")
    List<SeckillTimeEntity> getTime(long time);

    @Delete("delete from seckill_time")
    void deleteAll();

    @Select("select endTime from seckill_time where time_id = #{timeId}")
    Long getEndTime(Integer timeId);

}
