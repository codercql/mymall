package com.chen.mymall.miaosha.client.controller;

import com.mall.xiaomi.pojo.SeckillProduct;
import com.mall.xiaomi.pojo.SeckillTime;
import com.mall.xiaomi.service.OrderService;
import com.mall.xiaomi.service.SeckillProductService;
import com.mall.xiaomi.util.ResultMessage;
import com.mall.xiaomi.vo.SeckillProductVo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: wdd
 * @Date: 2020-03-28 19:58
 * @Description:秒杀功能
 */
@RestController
@RequestMapping("/seckill/product")
public class SeckillProductController {

//    @Autowired
    private ResultMessage resultMessage;
//    @Autowired
    @Reference
    private SeckillProductService seckillProductService;
//    @Autowired
    private OrderService orderService;
//    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据时间id获取对应时间的秒杀商品列表
     * @param timeId
     * @return
     */
    @GetMapping("/time/{timeId}")
    public ResultMessage getProduct(@PathVariable String timeId) {
        List<SeckillProductVo> seckillProductVos = seckillProductService.getProduct(timeId);
        resultMessage.success("001", seckillProductVos);
        return resultMessage;
    }

    /**
     * 获取秒杀商品
     * @param seckillId
     * @return
     */
    @GetMapping("/{seckillId}")
    public ResultMessage getSeckill(@PathVariable String seckillId) {
        SeckillProductVo seckillProductVo = seckillProductService.getSeckill(seckillId);
        resultMessage.success("001", seckillProductVo);
        return resultMessage;
    }

    /**
     * 获取时间段
     * @return
     */
    @GetMapping("/time")
    public ResultMessage getTime() {
        List<SeckillTime> seckillTimes = seckillProductService.getTime();
        resultMessage.success("001", seckillTimes);
        return resultMessage;
    }

    /**
     * 添加秒杀商品
     * @param seckillProduct
     * @return
     */
    @PostMapping("")
    public ResultMessage addSeckillProduct(@RequestBody SeckillProduct seckillProduct) {
        seckillProductService.addSeckillProduct(seckillProduct);
        resultMessage.success("001", "添加成功");
        return resultMessage;
    }

    /**
     * 开始秒杀
     * @param seckillId
     * @return
     */
    @PostMapping("/seckill/{seckillId}")
    public ResultMessage seckillProduct(@PathVariable String seckillId, @CookieValue("XM_TOKEN") String cookie) {
        // 先判断cookie是否存在，和redis校验
        Integer userId = (Integer) redisTemplate.opsForHash().get(cookie, "userId");
        seckillProductService.seckillProduct(seckillId, userId); //抢购过程这个里面
        resultMessage.success("001", "排队中");
        return resultMessage;
    }



}
