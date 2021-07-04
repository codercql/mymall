package com.chen.mymall.order.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.chen.mymall.common.entity.CollectEntity;
import com.chen.mymall.common.entity.ProductEntity;
import com.chen.mymall.common.service.CollectService;
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
 * @date 2021-04-29 17:49:55
 */
@RestController
@RequestMapping("/collect")
public class CollectController {

    @Autowired
    private CollectService collectService;

    /**
     * 将商品收藏
     * @param userId
     * @param productId
     * @return
     */
    @PostMapping("/user/{productId}/{userId}")
    public ResultMessage addCollect(@PathVariable String userId, @PathVariable String productId) {
        collectService.addCollect(userId, productId);
        return ResultMessage.success("001", "商品收藏成功");

    }

    /**
     * 获取用户收藏
     * @param userId
     * @return 返回商品集合
     */
    @GetMapping("/user/{userId}")
    public ResultMessage getCollect(@PathVariable String userId) {
        List<ProductEntity> collects = collectService.getCollect(userId);
        return ResultMessage.success("001", collects);

    }

    @DeleteMapping("/user/{productId}/{userId}")
    public ResultMessage deleteCollect(@PathVariable String productId, @PathVariable String userId) {
        collectService.deleteCollect(userId, productId);
        return ResultMessage.success("001", "删除收藏成功");

    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = collectService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		CollectEntity collect = collectService.getById(id);

        return R.ok().put("collect", collect);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CollectEntity collect){
		collectService.save(collect);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CollectEntity collect){
		collectService.updateById(collect);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		collectService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
