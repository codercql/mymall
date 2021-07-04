package com.chen.mymall.seckill.service.impl;

import com.chen.mymall.common.entity.SeckillTimeEntity;
import com.chen.mymall.common.service.SeckillTimeService;
import com.chen.mymall.common.utils.PageUtils;
import com.chen.mymall.common.utils.Query;

import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.chen.mymall.seckill.dao.SeckillTimeDao;


//@Service("seckillTimeService")
public class SeckillTimeServiceImpl extends ServiceImpl<SeckillTimeDao, SeckillTimeEntity> implements SeckillTimeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SeckillTimeEntity> page = this.page(
                new Query<SeckillTimeEntity>().getPage(params),
                new QueryWrapper<SeckillTimeEntity>()
        );

        return new PageUtils(page);
    }

}
