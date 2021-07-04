package com.chen.mymall.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.mymall.common.entity.ShoppingCartEntity;
import com.chen.mymall.common.utils.PageUtils;
import com.chen.mymall.common.vo.CartVo;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author chenqiulu
 * @email 1281371382@qq.com
 * @date 2021-04-29 17:49:00
 */
public interface ShoppingCartService extends IService<ShoppingCartEntity> {

    int delete(ShoppingCartEntity shoppingCartEntity);

    PageUtils queryPage(Map<String, Object> params);

    List<CartVo> getCartByUserId(String userId);

    CartVo addCart(String productId, String userId);

    void updateCartNum(String cartId, String userId, String num);

    void deleteCart(String cartId, String userId);


}

