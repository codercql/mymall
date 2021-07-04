package com.chen.mymall.product.service.impl;

import com.chen.mymall.common.entity.ProductPictureEntity;
import com.chen.mymall.common.service.ProductPictureService;
import com.chen.mymall.common.utils.PageUtils;
import com.chen.mymall.common.utils.Query;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.chen.mymall.product.dao.ProductPictureDao;


//@Service("productPictureService")
public class ProductPictureServiceImpl extends ServiceImpl<ProductPictureDao, ProductPictureEntity> implements ProductPictureService {

    @Autowired
    private ProductPictureDao productPictureDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProductPictureEntity> page = this.page(
                new Query<ProductPictureEntity>().getPage(params),
                new QueryWrapper<ProductPictureEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<ProductPictureEntity> getProductPictureByProductId(String productId) {
        ProductPictureEntity picture = new ProductPictureEntity();
        picture.setProductId(Integer.parseInt(productId));
        QueryWrapper<ProductPictureEntity> wrapper = new QueryWrapper<>(picture);

        List<ProductPictureEntity> list = productPictureDao.selectList(wrapper);
        return list;
    }

}
