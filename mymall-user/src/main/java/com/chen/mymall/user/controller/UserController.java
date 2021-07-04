package com.chen.mymall.user.controller;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.chen.mymall.common.entity.UserEntity;
import com.chen.mymall.common.service.UserService;
import com.chen.mymall.common.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 *
 * @author chenqiulu
 * @email 1281371382@qq.com
 * @date 2021-04-28 19:46:09
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 用户登录
     * @param user
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/login")
    public ResultMessage login(@RequestBody UserEntity user, HttpServletRequest request, HttpServletResponse response) {
        user = userService.login(user);
        // 添加cookie，设置唯一认证
        String encode = MD5Util.MD5Encode(user.getUsername() + user.getPassword(), "UTF-8");
        // 进行加盐
        encode += "|" + user.getUserId() + "|" + user.getUsername() + "|";
        CookieUtil.setCookie(request, response, "XM_TOKEN", encode, 1800);
        // 将encode放入redis中，用于认证
        try {
            redisTemplate.opsForHash().putAll(encode, BeanUtil.bean2map(user));
            redisTemplate.expire(encode, 30 * 60, TimeUnit.SECONDS); // 设置过期时间
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 将密码设为null,返回给前端
        user.setPassword(null);
        return ResultMessage.success("001", "登录成功", user);
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @PostMapping("/register")
    public ResultMessage register(@RequestBody UserEntity user) {
        userService.register(user);
        return ResultMessage.success("001", "注册成功");
    }

    /**
     * 判断用户名是否已存在
     * @param username
     * @return
     */
    @GetMapping("/username/{username}")
    public ResultMessage username(@PathVariable String username) {
        userService.isUserName(username);
        return ResultMessage.success("001", "可注册");
    }

    /**
     * 根据token获取用户信息
     * @param token
     * @return
     */
    @GetMapping("/token")
    public ResultMessage token(@CookieValue("XM_TOKEN") String token, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map map = redisTemplate.opsForHash().entries(token);
        // 可能map为空 ， 即redis中时间已过期，但是cookie还存在。
        // 这个时候应该删除cookie，让用户重新登录
        if (map.isEmpty()) {
            CookieUtil.delCookie(request, token);
            return ResultMessage.fail("002", "账号过期,请重新登录");

        }

        redisTemplate.expire(token, 30 * 60, TimeUnit.SECONDS); // 设置过期时间
        UserEntity user = BeanUtil.map2bean(map, UserEntity.class);
        user.setPassword(null);
        return ResultMessage.success("001", user);
    }





    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{userId}")
    public R info(@PathVariable("userId") Integer userId){
		UserEntity user = userService.getById(userId);

        return R.ok().put("user", user);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody UserEntity user){
		userService.save(user);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody UserEntity user){
		userService.updateById(user);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] userIds){
		userService.removeByIds(Arrays.asList(userIds));

        return R.ok();
    }

}
