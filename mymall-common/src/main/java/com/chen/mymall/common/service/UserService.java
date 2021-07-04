package com.chen.mymall.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.mymall.common.entity.UserEntity;
import com.chen.mymall.common.utils.PageUtils;

import java.util.Map;

/**
 *
 *
 * @author chenqiulu
 * @email 1281371382@qq.com
 * @date 2021-04-28 19:46:09
 */
public interface UserService extends IService<UserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    UserEntity login(UserEntity user);

    void register(UserEntity user);

    void isUserName(String username);

}

