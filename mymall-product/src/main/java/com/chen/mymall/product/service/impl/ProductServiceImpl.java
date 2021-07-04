package com.chen.mymall.product.service.impl;

import com.chen.mymall.common.entity.ProductEntity;
import com.chen.mymall.common.service.ProductService;
import com.chen.mymall.common.utils.PageUtils;
import com.chen.mymall.common.utils.Query;
import com.chen.mymall.product.dao.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;



//@Service("productService")
public class ProductServiceImpl extends ServiceImpl<ProductDao, ProductEntity> implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public void updateByPrimaryKey(ProductEntity productEntity){
        productDao.updateById(productEntity);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProductEntity> page = this.page(
                new Query<ProductEntity>().getPage(params),
                new QueryWrapper<ProductEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<ProductEntity> getProductByCategoryId(Integer categoryId) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setCategoryId(categoryId);
        //1、创建一个QueryWrapper对象
        QueryWrapper<ProductEntity> wrapper = new QueryWrapper<>(productEntity);
        //2、构造查询条件
        List<ProductEntity> list = productDao.selectList(wrapper);
        return list;
    }

    @Override
    public List<ProductEntity> getHotProduct() {
        QueryWrapper<ProductEntity> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("product_sales").last("limit 8");
        List<ProductEntity> list = null;
        list = productDao.selectList(wrapper);
        System.out.println(list.toString());
        return list;
    }

    @Override
    public ProductEntity getProductById(String productId) {
        ProductEntity productEntity = null;
        productEntity = productDao.selectById(productId);
        return productEntity;
    }

}
