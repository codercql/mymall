package com.chen.mymall.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.mymall.common.entity.CollectEntity;
import com.chen.mymall.common.entity.ProductEntity;
import com.chen.mymall.common.utils.PageUtils;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author chenqiulu
 * @email 1281371382@qq.com
 * @date 2021-04-29 17:49:55
 */
public interface CollectService extends IService<CollectEntity> {


    PageUtils queryPage(Map<String, Object> params);

    void addCollect(String userId, String productId);

    List<ProductEntity> getCollect(String userId);

    void deleteCollect(String userId, String productId);
}

