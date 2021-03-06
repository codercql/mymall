package com.chen.mymall.seckill.service.impl;

import com.chen.mymall.common.entity.SeckillProductEntity;
import com.chen.mymall.common.entity.SeckillTimeEntity;
import com.chen.mymall.common.exception.ExceptionEnum;
import com.chen.mymall.common.exception.XmException;
import com.chen.mymall.common.service.OrderService;
import com.chen.mymall.common.service.SeckillProductService;
import com.chen.mymall.common.utils.BeanUtil;
import com.chen.mymall.common.utils.PageUtils;
import com.chen.mymall.common.utils.Query;

import java.util.*;
import java.util.concurrent.TimeUnit;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.chen.mymall.common.utils.RedisKey;
import com.chen.mymall.common.vo.SeckillProductVo;
import com.chen.mymall.seckill.dao.SeckillProductDao;
import com.chen.mymall.seckill.dao.SeckillTimeDao;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.transaction.annotation.Transactional;


//@Service("seckillProductService")
public class SeckillProductServiceImpl extends ServiceImpl<SeckillProductDao, SeckillProductEntity> implements SeckillProductService {

    @Autowired
    private SeckillProductDao seckillProductDao;

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private SeckillTimeDao seckillTimeDao;
    @Autowired
    private OrderService orderService;

    private HashMap<String, Boolean> localOverMap = new HashMap<>();

    @Override
    @Transactional
    public List<SeckillProductVo> getProduct(String timeId) {

        // ?????????????????????????????????
        List<SeckillProductVo> seckillProductVos = redisTemplate.opsForList().range(RedisKey.SECKILL_PRODUCT_LIST + timeId, 0, -1);
        if (ArrayUtils.isNotEmpty(seckillProductVos.toArray())) {
            return seckillProductVos;
        }
        // ?????????????????????????????????????????????????????????
        seckillProductVos = seckillProductDao.getSeckillProductVos(timeId, new Date().getTime());
        if (ArrayUtils.isNotEmpty(seckillProductVos.toArray())) {
            redisTemplate.opsForList().leftPushAll(RedisKey.SECKILL_PRODUCT_LIST + timeId, seckillProductVos);
            // ??????????????????
            long l = seckillProductVos.get(0).getEndTime() - new Date().getTime();
            redisTemplate.expire(RedisKey.SECKILL_PRODUCT_LIST + timeId, l, TimeUnit.MILLISECONDS);
        }else {
            // ??????????????????????????????
            throw new XmException(ExceptionEnum.GET_SECKILL_NOT_FOUND);
        }
        return seckillProductVos;
    }

    @Override
    public void addSeckillProduct(SeckillProductEntity seckillProduct) {
        // TODO: ?????????????????????
        Date time = getDate();
        long startTime = time.getTime()/1000*1000 + 1000 * 60 * 60;
        long endTime = startTime + 1000 * 60 * 60;
        SeckillTimeEntity seckillTime = new SeckillTimeEntity();
        seckillTime.setStartTime(startTime);
        seckillTime.setEndTime(endTime);
        // ??????????????????????????????
        QueryWrapper<SeckillTimeEntity> wrapper = new QueryWrapper<>(seckillTime);
        SeckillTimeEntity one = seckillTimeDao.selectOne(wrapper);
        if (one == null) {
            seckillTimeDao.insert(seckillTime);
            seckillProduct.setTimeId(seckillTime.getTimeId());
        }else {
            seckillProduct.setTimeId(one.getTimeId());
        }
        seckillProductDao.insert(seckillProduct);
    }

    /**
     * ???????????????????????????
     * @return
     */
    private Date getDate() {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        return ca.getTime();
    }

    @Override
    public List<SeckillTimeEntity> getTime() {
        // ???????????????????????????7????????????, ??????8???
        Date time = getDate();
        List<SeckillTimeEntity> seckillTimes = seckillTimeDao.getTime(time.getTime()/1000*1000);
        return seckillTimes;
    }

    @Override
    public SeckillProductVo getSeckill(String seckillId) {
        // ??????????????????
        Map map = redisTemplate.opsForHash().entries(RedisKey.SECKILL_PRODUCT + seckillId);
        if (!map.isEmpty()) {
            map.size();
            SeckillProductVo seckillProductVo = null;
            try {
                seckillProductVo = BeanUtil.map2bean(map, SeckillProductVo.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return seckillProductVo;
        }
        // ?????????????????????
        SeckillProductVo seckillProductVo = seckillProductDao.getSeckill(seckillId);
        if (seckillProductVo != null) {
            try {
                redisTemplate.opsForHash().putAll(RedisKey.SECKILL_PRODUCT + seckillId, BeanUtil.bean2map(seckillProductVo));
                redisTemplate.expire(RedisKey.SECKILL_PRODUCT + seckillId, seckillProductVo.getEndTime() - new Date().getTime(), TimeUnit.MILLISECONDS);
                // ???????????????????????????key???
                if (stringRedisTemplate.opsForValue().get(RedisKey.SECKILL_PRODUCT_STOCK + seckillId) == null) {
                    stringRedisTemplate.opsForValue().set(RedisKey.SECKILL_PRODUCT_STOCK + seckillId, seckillProductVo.getSeckillStock() + "",seckillProductVo.getEndTime() - new Date().getTime(), TimeUnit.MILLISECONDS);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return seckillProductVo;
        }
        return null;
    }

    /**
     * ??????
     * @param seckillId
     */
    @Override
    @Transactional
    public void seckillProduct(String seckillId, Integer userId) {
        if (localOverMap.get(seckillId) != null && localOverMap.get(seckillId)) {
            // ??????
            throw new XmException(ExceptionEnum.GET_SECKILL_IS_OVER);
        }
        // ????????????????????????, ????????????????????????
        Map m = redisTemplate.opsForHash().entries(RedisKey.SECKILL_PRODUCT + seckillId);
        if (!m.isEmpty()) {
            SeckillProductVo seckillProductVo = null;
            try {
                seckillProductVo = BeanUtil.map2bean(m, SeckillProductVo.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // ??????????????????
            Long startTime = seckillProductVo.getStartTime();

            if (startTime > new Date().getTime()) {
                throw new XmException(ExceptionEnum.GET_SECKILL_IS_NOT_START);
            }
        }

        // ?????????????????????????????????????????????????????????????????????
        List<String> list = redisTemplate.opsForList().range(RedisKey.SECKILL_PRODUCT_USER_LIST + seckillId, 0, -1);
        if (list.contains(String.valueOf(userId))) {
            throw new XmException(ExceptionEnum.GET_SECKILL_IS_REUSE);
        }

        // ???????????????????????????????????????
        // ??????redis????????????????????????????????????????????????1????????????
        if (stringRedisTemplate.opsForValue().decrement(RedisKey.SECKILL_PRODUCT_STOCK + seckillId) < 0) {
            // ??????????????????
            localOverMap.put(seckillId, true);
            // ???????????????????????????
            throw new XmException(ExceptionEnum.GET_SECKILL_IS_OVER);
        }

        // ??????RabbitMQ????????????
        mqSend(seckillId, userId);


    }


    private void mqSend(String seckillId, Integer userId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("seckillId", seckillId);
        map.put("userId", userId.toString());
        // ??????ID??????????????????????????????
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(seckillId + ":" + userId);
        try {
            System.out.println("seckill_order"+"????????????");
            //???????????????????????????id?????????id ??????????????? 1??????????????? 2????????????
            rabbitTemplate.convertAndSend("seckill_order", map, correlationData);
            System.out.println("??????????????????");
        } catch (AmqpException e) {
            // ??????????????????
            e.printStackTrace();
            stringRedisTemplate.opsForValue().increment(RedisKey.SECKILL_PRODUCT_STOCK + seckillId);
        }
    }

    //??????????????????public??????
    @RabbitListener(queues = "seckill_order")
    public void mqReceiver(@Payload Map map){
        System.out.println("???????????????????????????????????????????????????userId");
        System.out.println(map.get("seckillId"));
        System.out.println(map.get("userId"));
        orderService.addSeckillOrder((String)map.get("seckillId"),(String)map.get("userId"));
        return;
    }
    /*    public String createOrderByMq(Integer seckillId,Integer userId) throws Exception{
            // ???????????????????????????????????????????????????????????????10???
            Thread.sleep(10000);

            //1???????????????

            //2?????????redis?????????

            //3???????????????

            return "?????????????????????";
        }*/
    @Override
    public Long getEndTime(String seckillId) {
        SeckillProductVo seckill = seckillProductDao.getSeckill(seckillId);
        return seckillTimeDao.getEndTime(seckill.getTimeId());
    }




    @Override
    public SeckillProductEntity selectOne(SeckillProductEntity seckillProductEntity){
        QueryWrapper<SeckillProductEntity> wrapper = new QueryWrapper<>(seckillProductEntity);
        return seckillProductDao.selectOne(wrapper);
    }

    @Override
    public void decrStock(Integer seckillId){
        seckillProductDao.decrStock(seckillId);

    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SeckillProductEntity> page = this.page(
                new Query<SeckillProductEntity>().getPage(params),
                new QueryWrapper<SeckillProductEntity>()
        );

        return new PageUtils(page);
    }

}
