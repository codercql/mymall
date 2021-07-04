package com.chen.mymall.order.service.impl;

import com.chen.mymall.common.entity.OrderEntity;
import com.chen.mymall.common.entity.ProductEntity;
import com.chen.mymall.common.entity.SeckillProductEntity;
import com.chen.mymall.common.entity.ShoppingCartEntity;
import com.chen.mymall.common.exception.ExceptionEnum;
import com.chen.mymall.common.exception.XmException;
import com.chen.mymall.common.service.OrderService;
import com.chen.mymall.common.service.ProductService;
import com.chen.mymall.common.service.SeckillProductService;
import com.chen.mymall.common.service.ShoppingCartService;
import com.chen.mymall.common.utils.IdWorker;
import com.chen.mymall.common.utils.PageUtils;
import com.chen.mymall.common.utils.Query;
import com.chen.mymall.common.vo.CartVo;
import com.chen.mymall.common.vo.OrderVo;
import org.apache.commons.lang.ArrayUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.mymall.order.dao.OrderDao;
import org.springframework.transaction.annotation.Transactional;


//@Service("orderService")
//@Service
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductService productService;
    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private SeckillProductService seckillProductService;

    private IdWorker idWorker;


    private final static String SECKILL_PRODUCT_USER_LIST = "seckill:product:user:list";


    @Transactional
    public void addOrder(List<CartVo> cartVoList, Integer userId) {
        // 先添加订单
        String orderId = idWorker.nextId() + ""; // 订单id
        long time = new Date().getTime(); // 订单生成时间
        for (CartVo cartVo : cartVoList) {
            OrderEntity order = new OrderEntity();
            order.setOrderId(orderId);
            order.setOrderTime(time);
            order.setProductNum(cartVo.getNum());
            order.setProductId(cartVo.getProductId());
            order.setProductPrice(cartVo.getPrice());
            order.setUserId(userId);
            try {
                orderDao.insert(order);
            } catch (Exception e) {
                e.printStackTrace();
                throw new XmException(ExceptionEnum.ADD_ORDER_ERROR);
            }
            // 减去商品库存,记录卖出商品数量
            // TODO : 此处会产生多线程问题，即不同用户同时对这个商品操作，此时会导致数量不一致问题
//            ProductEntity product = productMapper.selectByPrimaryKey(cartVo.getProductId());
            ProductEntity product = productService.getProductById(String.valueOf(cartVo.getProductId()));
            product.setProductNum(product.getProductNum() - cartVo.getNum());
            product.setProductSales(product.getProductSales() + cartVo.getNum());
            productService.updateByPrimaryKey(product);
        }
        // 删除购物车
        ShoppingCartEntity cart = new ShoppingCartEntity();
        cart.setUserId(userId);
        try {
            int count = shoppingCartService.delete(cart);
            if (count == 0) {
                throw new XmException(ExceptionEnum.ADD_ORDER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new XmException(ExceptionEnum.ADD_ORDER_ERROR);
        }

    }

    public List<List<OrderVo>> getOrder(Integer userId) {
        List<OrderVo> list = null;
        ArrayList<List<OrderVo>> ret = new ArrayList<>();
        try {
            list = orderDao.getOrderVoByUserId(userId);
            if (ArrayUtils.isEmpty(list.toArray())) {
                throw new XmException(ExceptionEnum.GET_ORDER_NOT_FOUND);
            }
            // 将同一个订单放在一组
            Map<String, List<OrderVo>> collect = list.stream().collect(Collectors.groupingBy(OrderEntity::getOrderId));
            Collection<List<OrderVo>> values = collect.values();
            ret.addAll(values);
        } catch (XmException e) {
            e.printStackTrace();
            throw new XmException(ExceptionEnum.GET_ORDER_ERROR);
        }
        return ret;
    }

    @Transactional
    public void addSeckillOrder(String seckillId, String userId) {
        // 订单id
        String orderId = idWorker.nextId() + "";
        // 商品id
        SeckillProductEntity seckillProduct = new SeckillProductEntity();
        seckillProduct.setSeckillId(Integer.parseInt(seckillId));
        SeckillProductEntity one = seckillProductService.selectOne(seckillProduct);
        Integer productId = one.getProductId();
        // 秒杀价格
        Double price = one.getSeckillPrice();

        // 订单封装
        OrderEntity order = new OrderEntity();
        order.setOrderId(orderId);
        order.setProductId(productId);
        order.setProductNum(1);
        order.setUserId(Integer.parseInt(userId));
        order.setOrderTime(new Date().getTime());
        order.setProductPrice(price);

        try {
            orderDao.insert(order);
            // 减库存
            seckillProductService.decrStock(one.getSeckillId());
        } catch (Exception e) {
            e.printStackTrace();
            throw new XmException(ExceptionEnum.ADD_ORDER_ERROR);
        }

        // 订单创建成功, 将用户写入redis, 防止多次抢购
        redisTemplate.opsForList().leftPush(SECKILL_PRODUCT_USER_LIST + seckillId, userId);

    }
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                new QueryWrapper<OrderEntity>()
        );

        return new PageUtils(page);
    }
}
