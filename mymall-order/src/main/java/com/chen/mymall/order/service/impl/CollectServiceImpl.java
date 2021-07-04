package com.chen.mymall.order.service.impl;

import com.chen.mymall.common.entity.CollectEntity;
import com.chen.mymall.common.entity.ProductEntity;
import com.chen.mymall.common.exception.ExceptionEnum;
import com.chen.mymall.common.exception.XmException;
import com.chen.mymall.common.service.CollectService;
import com.chen.mymall.common.utils.PageUtils;
import com.chen.mymall.common.utils.Query;
import com.chen.mymall.order.dao.CollectDao;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.transaction.annotation.Transactional;


//@Service("collectService")
public class CollectServiceImpl extends ServiceImpl<CollectDao, CollectEntity> implements CollectService {

    @Autowired
    private CollectDao collectDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CollectEntity> page = this.page(
                new Query<CollectEntity>().getPage(params),
                new QueryWrapper<CollectEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void addCollect(String userId, String productId) {
        CollectEntity collectEntity = new CollectEntity();
        collectEntity.setUserId(Integer.parseInt(userId));
        collectEntity.setProductId(Integer.parseInt(productId));
        QueryWrapper<CollectEntity> wrapper = new QueryWrapper<>(collectEntity);
        // 先看看是否数据库中已存在
        CollectEntity one = collectDao.selectOne(wrapper);
        if (one != null) {
            throw new XmException(ExceptionEnum.SAVE_COLLECT_REUSE);
        }
        // 不存在，添加收藏
        collectEntity.setCollectTime(new Date().getTime());
        int count = collectDao.insert(collectEntity);
        if (count != 1) {
            throw new XmException(ExceptionEnum.SAVE_COLLECT_ERROR);
        }
    }

    @Override
    public List<ProductEntity> getCollect(String userId) {
        List<ProductEntity> list = null;
        try {
            list = collectDao.getCollect(userId);
            if (ArrayUtils.isEmpty(list.toArray())) {
                throw new XmException(ExceptionEnum.GET_COLLECT_NOT_FOUND);
            }
        } catch (XmException e) {
            e.printStackTrace();
            throw new XmException(ExceptionEnum.GET_COLLECT_ERROR);
        }
        return list;
    }

    @Override
    public void deleteCollect(String userId, String productId) {
        CollectEntity collect = new CollectEntity();
        collect.setUserId(Integer.parseInt(userId));
        collect.setProductId(Integer.parseInt(productId));
        QueryWrapper<CollectEntity> wrapper = new QueryWrapper<>(collect);
        try {
            int count = collectDao.delete(wrapper);
            if (count != 1) {
                throw new XmException(ExceptionEnum.DELETE_COLLECT_ERROR);
            }
        } catch (XmException e) {
            e.printStackTrace();
            throw new XmException(ExceptionEnum.DELETE_COLLECT_ERROR);
        }

    }

}
