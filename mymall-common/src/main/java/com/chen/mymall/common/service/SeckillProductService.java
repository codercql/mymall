package com.chen.mymall.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.mymall.common.entity.SeckillProductEntity;
import com.chen.mymall.common.entity.SeckillTimeEntity;
import com.chen.mymall.common.utils.PageUtils;
import com.chen.mymall.common.vo.SeckillProductVo;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author chenqiulu
 * @email 1281371382@qq.com
 * @date 2021-04-29 17:47:43
 */
public interface SeckillProductService extends IService<SeckillProductEntity> {

    List<SeckillProductVo> getProduct(String timeId);

    void addSeckillProduct(SeckillProductEntity seckillProduct);

    List<SeckillTimeEntity> getTime();

    SeckillProductVo getSeckill(String seckillId);

    void seckillProduct(String seckillId, Integer userId);

    Long getEndTime(String seckillId);

    SeckillProductEntity selectOne(SeckillProductEntity seckillProductEntity);

    void decrStock(Integer seckillId);

    PageUtils queryPage(Map<String, Object> params);
}

