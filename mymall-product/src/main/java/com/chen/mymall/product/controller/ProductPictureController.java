package com.chen.mymall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.chen.mymall.common.entity.ProductPictureEntity;
import com.chen.mymall.common.service.ProductPictureService;
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
@RequestMapping("/productPicture")
public class ProductPictureController {
    @Autowired
    private ProductPictureService productPictureService;

    @GetMapping("/product/{productId}")
    public ResultMessage productPicture(@PathVariable String productId) {
        List<ProductPictureEntity> products = productPictureService.getProductPictureByProductId(productId);
        return ResultMessage.success("001", products);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = productPictureService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		ProductPictureEntity productPicture = productPictureService.getById(id);

        return R.ok().put("productPicture", productPicture);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ProductPictureEntity productPicture){
		productPictureService.save(productPicture);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ProductPictureEntity productPicture){
		productPictureService.updateById(productPicture);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		productPictureService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
