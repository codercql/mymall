package com.chen.mymall.seckill.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.chen.mymall.common.entity.SeckillProductEntity;
import com.chen.mymall.common.entity.SeckillTimeEntity;
import com.chen.mymall.common.service.OrderService;
import com.chen.mymall.common.service.SeckillProductService;
import com.chen.mymall.common.utils.PageUtils;
import com.chen.mymall.common.utils.R;
import com.chen.mymall.common.utils.ResultMessage;
import com.chen.mymall.common.vo.SeckillProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;



/**
 *
 *
 * @author chenqiulu
 * @email 1281371382@qq.com
 * @date 2021-04-29 17:47:43
 */
@RestController
@RequestMapping("seckill/product")
public class SeckillProductController {
    @Autowired
    private SeckillProductService seckillProductService;

    @Autowired
    private OrderService orderService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据时间id获取对应时间的秒杀商品列表
     * @param timeId
     * @return
     */
    @GetMapping("/time/{timeId}")
    public ResultMessage getProduct(@PathVariable String timeId) {
        List<SeckillProductVo> seckillProductVos = seckillProductService.getProduct(timeId);
        return ResultMessage.success("001", seckillProductVos);

    }

    /**
     * 获取秒杀商品
     * @param seckillId
     * @return
     */
    @GetMapping("/{seckillId}")
    public ResultMessage getSeckill(@PathVariable String seckillId) {
        SeckillProductVo seckillProductVo = seckillProductService.getSeckill(seckillId);
        return ResultMessage.success("001", seckillProductVo);

    }

    /**
     * 获取时间段
     * @return
     */
    @GetMapping("/time")
    public ResultMessage getTime() {
        List<SeckillTimeEntity> seckillTimes = seckillProductService.getTime();
        return ResultMessage.success("001", seckillTimes);

    }

    /**
     * 添加秒杀商品
     * @param seckillProduct
     * @return
     */
    @PostMapping("")
    public ResultMessage addSeckillProduct(@RequestBody SeckillProductEntity seckillProduct) {
        seckillProductService.addSeckillProduct(seckillProduct);
        return ResultMessage.success("001", "添加成功");

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
        return ResultMessage.success("001", "排队中");

    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = seckillProductService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{seckillId}")
    public R info(@PathVariable("seckillId") Integer seckillId){
		SeckillProductEntity seckillProduct = seckillProductService.getById(seckillId);

        return R.ok().put("seckillProduct", seckillProduct);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody SeckillProductEntity seckillProduct){
		seckillProductService.save(seckillProduct);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody SeckillProductEntity seckillProduct){
		seckillProductService.updateById(seckillProduct);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] seckillIds){
		seckillProductService.removeByIds(Arrays.asList(seckillIds));

        return R.ok();
    }

}
