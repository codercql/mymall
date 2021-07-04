package com.chen.mymall.product.service.impl;

import com.chen.mymall.common.service.CarouselService;
import com.chen.mymall.common.utils.PageUtils;
import com.chen.mymall.common.utils.Query;
import com.chen.mymall.common.entity.CarouselEntity;

import com.chen.mymall.product.dao.CarouselDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;



//@Service("carouselService")
public class CarouselServiceImpl extends ServiceImpl<CarouselDao, CarouselEntity> implements CarouselService {

    @Autowired
    private CarouselDao carouselDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CarouselEntity> page = this.page(
                new Query<CarouselEntity>().getPage(params),
                new QueryWrapper<CarouselEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CarouselEntity> getCarouselList() {
        List<CarouselEntity> list = null;
        list = carouselDao.selectList(null);
        return list;
    }


}
