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
        // ??????????????????????????????,?????????????????????1
        ShoppingCartEntity one = shoppingCartDao.selectOne(wrapper);
        if(one!=null){
            // ?????????????????????????????????????????????
            if (one.getNum() >= 5) { // TODO ??????????????????5 ?????????????????????
                System.out.println("??????????????????");
                return null;
//                throw new XmException(ExceptionEnum.ADD_CART_NUM_UPPER);
            }
            one.setNum(one.getNum()+1);
            shoppingCartDao.update(one,wrapper);//one?????????set  wrapper?????????where
            return null;
        }else{
            //?????????
            cartEntity.setNum(1);
            shoppingCartDao.update(cartEntity,wrapper);//??????????????????????????????
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
            int count = shoppingCartDao.update(cartEntity,wrapper);//?????????
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
     * ?????????
     * @param
     * @return
     */
    private CartVo getCartVo(ShoppingCartEntity cartEntity) {
        // ???????????????????????????????????????
        ProductEntity product = productDao.selectById(cartEntity.getProductId());
        // ?????????????????????
        CartVo cartVo = new CartVo();
        cartVo.setId(cartEntity.getId());
        cartVo.setProductId(cartEntity.getProductId());
        cartVo.setProductName(product.getProductName());
        cartVo.setProductImg(product.getProductPicture());
        cartVo.setPrice(product.getProductSellingPrice());
        cartVo.setNum(cartEntity.getNum());
        cartVo.setMaxNum(5); // TODO ??????????????????5 ?????????????????????
        cartVo.setCheck(false);
        return cartVo;
    }



}
