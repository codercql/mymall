package com.chen.mymall.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.mymall.common.entity.OrderEntity;
import com.chen.mymall.common.vo.OrderVo;
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
public interface OrderDao extends BaseMapper<OrderEntity> {
    @Select("select `order`.*, product.product_name as productName, product.product_picture as productPicture " +
            "from `order`, product where `order`.product_id = product.product_id and `order`.user_id = #{userId}")
    List<OrderVo> getOrderVoByUserId(Integer userId);

}
