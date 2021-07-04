package com.chen.mymall.product.service.impl;

import com.chen.mymall.common.entity.ProductEntity;
import com.chen.mymall.common.entity.ShoppingCartEntity;
import com.chen.mymall.common.service.ShoppingCartService;
import com.chen.mymall.common.utils.PageUtils;
import com.chen.mymall.common.utils.Query;
import com.chen.mymall.common.vo.CartVo;
import com.chen.mymall.product.dao.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.chen.mymall.product.dao.ShoppingCartDao;
import org.springframework.transaction.annotation.Transactional;


//@Service("shoppingCartService")
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartDao, ShoppingCartEntity> implements ShoppingCartService {
    @Autowired
    private ShoppingCartDao shoppingCartDao;
    @Autowired
    private ProductDao productDao;

    @Override
    public int delete(ShoppingCartEntity shoppingCartEntity){
        QueryWrapper<ShoppingCartEntity> wrapper = new QueryWrapper<>(shoppingCartEntity);
        return shoppingCartDao.delete(wrapper);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ShoppingCartEntity> page = this.page(
                new Query<ShoppingCartEntity>().getPage(params),
                new QueryWrapper<ShoppingCartEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CartVo> getCartByUserId(String userId) {
        ShoppingCartEntity cartEntity = new ShoppingCartEntity();
        cartEntity.setUserId(Integer.parseInt(userId));
        QueryWrapper<ShoppingCartEntity> wrapper = new QueryWrapper<>(cartEntity);
        List<ShoppingCartEntity> list = null;
        List<CartVo> cartVoList = new ArrayList<>();
        try{
            list = shoppingCartDao.selectList(wrapper);
            for(ShoppingCartEntity c:list){
                cartVoList.add(getCartVo(c));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return cartVoList;
    }

    @Override
    @Transactional
    public CartVo addCart(String productId, String userId) {
        ShoppingCartEntity cartEntity = new ShoppingCartEntity();
        cartEntity.setUserId(Integer.parseInt(userId));
        cartEntity.setProductId(Integer.parseInt(productId));
        QueryWrapper<ShoppingCartEntity> wrapper = new QueryWrapper<>(cartEntity);
        // 查看数据库是否已存在,存在数量直接加1
        ShoppingCartEntity one = shoppingCartDao.selectOne(wrapper);
        if(one!=null){
            // 还要判断是否达到该商品规定上限
            if (one.getNum() >= 5) { // TODO 这里默认设为5 后期再动态修改
                System.out.println("商品达到上限");
                return null;
//                throw new XmException(ExceptionEnum.ADD_CART_NUM_UPPER);
            }
            one.setNum(one.getNum()+1);
            shoppingCartDao.update(one,wrapper);//one相当于set  wrapper相当于where
            return null;
        }else{
            //不存在
            cartEntity.setNum(1);
            shoppingCartDao.update(cartEntity,wrapper);//这个更新语句也没验证
            return getCartVo(cartEntity);
        }
    }

    @Override
    public void updateCartNum(String cartId, String userId, String num) {
        ShoppingCartEntity cartEntity = new ShoppingCartEntity();
        cartEntity.setId(Integer.parseInt(cartId));
        cartEntity.setUserId(Integer.parseInt(userId));
        cartEntity.setNum(Integer.parseInt(num));
        QueryWrapper<ShoppingCartEntity> wrapper = new QueryWrapper<>(new ShoppingCartEntity());
        try{
            int count = shoppingCartDao.update(cartEntity,wrapper);//不确定
            if(count!=1){
                throw new Exception();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCart(String cartId, String userId) {
        ShoppingCartEntity cartEntity = new ShoppingCartEntity();
        cartEntity.setId(Integer.parseInt(cartId));
        cartEntity.setUserId(Integer.parseInt(userId));
        QueryWrapper<ShoppingCartEntity> wrapper = new QueryWrapper<>(cartEntity);
        try{
            shoppingCartDao.delete(wrapper);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 封装类
     * @param
     * @return
     */
    private CartVo getCartVo(ShoppingCartEntity cartEntity) {
        // 获取商品，用于封装下面的类
        ProductEntity product = productDao.selectById(cartEntity.getProductId());
        // 返回购物车详情
        CartVo cartVo = new CartVo();
        cartVo.setId(cartEntity.getId());
        cartVo.setProductId(cartEntity.getProductId());
        cartVo.setProductName(product.getProductName());
        cartVo.setProductImg(product.getProductPicture());
        cartVo.setPrice(product.getProductSellingPrice());
        cartVo.setNum(cartEntity.getNum());
        cartVo.setMaxNum(5); // TODO 这里默认设为5 后期再动态修改
        cartVo.setCheck(false);
        return cartVo;
    }



}
