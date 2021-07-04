package com.chen.mymall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.chen.mymall.common.entity.ShoppingCartEntity;
import com.chen.mymall.common.service.ShoppingCartService;
import com.chen.mymall.common.utils.PageUtils;
import com.chen.mymall.common.utils.R;
import com.chen.mymall.common.utils.ResultMessage;
import com.chen.mymall.common.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



/**
 *
 *
 * @author chenqiulu
 * @email 1281371382@qq.com
 * @date 2021-04-29 17:49:00
 */
@RestController
@RequestMapping("/cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;


    /**
     * 获取购物车信息
     * @param userId
     * @return
     */
    @GetMapping("/user/{userId}")
    public ResultMessage cart(@PathVariable String userId) {
        List<CartVo> carts = shoppingCartService.getCartByUserId(userId);
        return ResultMessage.success("001", carts);
    }

    /**
     * 添加购物车
     * @param productId
     * @param userId
     * @return
     */
    @PostMapping("/product/user/{productId}/{userId}")
    public ResultMessage cart(@PathVariable String productId, @PathVariable String userId) {
        CartVo cartVo = shoppingCartService.addCart(productId, userId);
        if (cartVo != null) {
            return ResultMessage.success("001", "添加购物车成功", cartVo);
        }else {
            return ResultMessage.success("002", "该商品已经在购物车，数量+1");
        }
    }

    @PutMapping("/user/num/{cartId}/{userId}/{num}")
    public ResultMessage cart(@PathVariable String cartId, @PathVariable String userId, @PathVariable String num) {
        shoppingCartService.updateCartNum(cartId, userId, num);
        return ResultMessage.success("001", "更新成功");
    }

    @DeleteMapping("/user/{cartId}/{userId}")
    public ResultMessage deleteCart(@PathVariable String cartId, @PathVariable String userId) {
        shoppingCartService.deleteCart(cartId, userId);
        return ResultMessage.success("001", "删除成功");
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = shoppingCartService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		ShoppingCartEntity shoppingCart = shoppingCartService.getById(id);

        return R.ok().put("shoppingCart", shoppingCart);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ShoppingCartEntity shoppingCart){
		shoppingCartService.save(shoppingCart);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ShoppingCartEntity shoppingCart){
		shoppingCartService.updateById(shoppingCart);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		shoppingCartService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
