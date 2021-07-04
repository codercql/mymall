package com.chen.mymall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.chen.mymall.common.entity.CarouselEntity;
import com.chen.mymall.common.service.CarouselService;
import com.chen.mymall.common.utils.PageUtils;
import com.chen.mymall.common.utils.R;
import com.chen.mymall.common.utils.ResultMessage;
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
//@RequestMapping("/product/carousel")
public class CarouselController {
    @Autowired
    private CarouselService carouselService;

//    获取轮播图片
//    @GetMapping("/resources/carousel")
//    public R carousels(){
//        List<CarouselEntity> carousels = carouselService.getCarouselList();
//        return R.ok().put("001",carousels);
//    }

//    获取轮播图片
    @GetMapping("/resources/carousel")
    public ResultMessage carousels(){
        List<CarouselEntity> carousels = carouselService.getCarouselList();
        System.out.println(ResultMessage.success("001",carousels));
        return ResultMessage.success("001",carousels);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = carouselService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{carouselId}")
    public R info(@PathVariable("carouselId") Integer carouselId){
		CarouselEntity carousel = carouselService.getById(carouselId);

        return R.ok().put("carousel", carousel);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CarouselEntity carousel){
		carouselService.save(carousel);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CarouselEntity carousel){
		carouselService.updateById(carousel);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] carouselIds){
		carouselService.removeByIds(Arrays.asList(carouselIds));

        return R.ok();
    }

}
