package com.chen.mymall.order.dao;

import com.chen.mymall.common.entity.CollectEntity;
import com.chen.mymall.common.entity.ProductEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 *
 *
 * @author chenqiulu
 * @email 1281371382@qq.com
 * @date 2021-04-29 17:49:55
 */
@Mapper
public interface CollectDao extends BaseMapper<CollectEntity> {

    @Select("select product.* from product, collect where collect.user_id = #{userId} and collect.product_id = product.product_id")
    List<ProductEntity> getCollect(String userId);

}
