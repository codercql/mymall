package com.chen.mymall.seckill.controller;

import java.util.Arrays;
import java.util.Map;

import com.chen.mymall.common.entity.SeckillTimeEntity;
import com.chen.mymall.common.service.SeckillTimeService;
import com.chen.mymall.common.utils.PageUtils;
import com.chen.mymall.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



/**
 *
 *
 * @author chenqiulu
 * @email 1281371382@qq.com
 * @date 2021-04-29 17:47:42
 */
@RestController
@RequestMapping("seckill/seckilltime")
public class SeckillTimeController {
    @Autowired
    private SeckillTimeService seckillTimeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = seckillTimeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{timeId}")
    public R info(@PathVariable("timeId") Integer timeId){
		SeckillTimeEntity seckillTime = seckillTimeService.getById(timeId);

        return R.ok().put("seckillTime", seckillTime);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody SeckillTimeEntity seckillTime){
		seckillTimeService.save(seckillTime);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody SeckillTimeEntity seckillTime){
		seckillTimeService.updateById(seckillTime);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] timeIds){
		seckillTimeService.removeByIds(Arrays.asList(timeIds));

        return R.ok();
    }

}
