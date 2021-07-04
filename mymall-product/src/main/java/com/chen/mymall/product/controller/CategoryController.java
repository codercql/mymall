package com.chen.mymall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.chen.mymall.common.entity.CategoryEntity;
import com.chen.mymall.common.service.CategoryService;
import com.chen.mymall.common.utils.PageUtils;
import com.chen.mymall.common.utils.R;
import com.chen.mymall.common.utils.ResultMessage;
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
 * @date 2021-04-29 17:49:00
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("")
    public ResultMessage category(){
        List<CategoryEntity> category = categoryService.getAll();
        return ResultMessage.success("001",category);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = categoryService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{categoryId}")
    public R info(@PathVariable("categoryId") Integer categoryId){
		CategoryEntity category = categoryService.getById(categoryId);

        return R.ok().put("category", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CategoryEntity category){
		categoryService.save(category);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CategoryEntity category){
		categoryService.updateById(category);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] categoryIds){
		categoryService.removeByIds(Arrays.asList(categoryIds));

        return R.ok();
    }

}
