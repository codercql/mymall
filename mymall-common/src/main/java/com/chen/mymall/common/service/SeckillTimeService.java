package com.chen.mymall.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.mymall.common.entity.SeckillTimeEntity;
import com.chen.mymall.common.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Map;

/**
 *
 *
 * @author chenqiulu
 * @email 1281371382@qq.com
 * @date 2021-04-29 17:47:42
 */
public interface SeckillTimeService extends IService<SeckillTimeEntity> {



    PageUtils queryPage(Map<String, Object> params);
}

