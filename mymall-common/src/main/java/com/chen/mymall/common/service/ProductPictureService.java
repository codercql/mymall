package com.chen.mymall.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.mymall.common.entity.ProductPictureEntity;
import com.chen.mymall.common.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author chenqiulu
 * @email 1281371382@qq.com
 * @date 2021-04-29 17:49:00
 */
public interface ProductPictureService extends IService<ProductPictureEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<ProductPictureEntity> getProductPictureByProductId(String productId);

}

