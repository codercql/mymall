package com.chen.mymall.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.mymall.common.entity.OrderEntity;
import com.chen.mymall.common.utils.PageUtils;
import com.chen.mymall.common.vo.CartVo;
import com.chen.mymall.common.vo.OrderVo;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author chenqiulu
 * @email 1281371382@qq.com
 * @date 2021-04-29 17:49:55
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void addOrder(List<CartVo> cartVoList, Integer userId);


    List<List<OrderVo>> getOrder(Integer userId);

    void addSeckillOrder(String seckillId, String userId);

}

