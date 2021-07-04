package com.chen.mymall.product.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chen.mymall.common.entity.ProductEntity;
import com.chen.mymall.common.service.ProductService;
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
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/category/limit/{categoryId}")
    public ResultMessage getProductByCategoryId(@PathVariable Integer categoryId){

        return ResultMessage.success("001",productService.getProductByCategoryId(categoryId));
    }

    @GetMapping("/category/hot")
    public ResultMessage getHotProduct() {
        List<ProductEntity> list = productService.getHotProduct();
        return ResultMessage.success("001",list);
    }

    @GetMapping("/{productId}")
    public ResultMessage getProduct(@PathVariable String productId) {
        ProductEntity product = productService.getProductById(productId);
        return ResultMessage.success("001",product);
    }

    //分类分页查询暂时未完成
    @GetMapping("/page/{currentPage}/{pageSize}/{categoryId}")
    public Map<String, Object> getProductByPage(@PathVariable String currentPage, @PathVariable String pageSize, @PathVariable String categoryId) {
//        PageInfo<ProductEntity> pageInfo = productService.getProductByPage(currentPage, pageSize, categoryId);
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", "001");
//        map.put("data", pageInfo.getList());
//        map.put("total", pageInfo.getTotal());
        return map;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = productService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{productId}")
    public R info(@PathVariable("productId") Integer productId){
		ProductEntity product = productService.getById(productId);

        return R.ok().put("product", product);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ProductEntity product){
		productService.save(product);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ProductEntity product){
		productService.updateById(product);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] productIds){
		productService.removeByIds(Arrays.asList(productIds));

        return R.ok();
    }

}
