package com.chen.mymall.user.service.impl;

import com.chen.mymall.common.entity.UserEntity;
import com.chen.mymall.common.exception.ExceptionEnum;
import com.chen.mymall.common.exception.XmException;
import com.chen.mymall.common.service.UserService;
import com.chen.mymall.common.utils.MD5Util;
import com.chen.mymall.common.utils.PageUtils;
import com.chen.mymall.common.utils.Query;

import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.chen.mymall.user.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;


//@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

    @Autowired
    private UserDao userDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserEntity> page = this.page(
                new Query<UserEntity>().getPage(params),
                new QueryWrapper<UserEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public UserEntity login(UserEntity user) {
        user.setPassword(MD5Util.MD5Encode(user.getPassword() + "", "UTF-8"));
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>(user);
        UserEntity one = userDao.selectOne(wrapper);

        if (one == null) {
            throw new XmException(ExceptionEnum.GET_USER_NOT_FOUND);
        }
        return one;
    }

    @Override
    public void register(UserEntity user) {
        UserEntity one = new UserEntity();
        one.setUsername(user.getUsername());
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>(one);
        // 先去看看用户名是否重复
        if (userDao.selectCount(wrapper) == 1) {
            // 用户名已存在
            throw new XmException(ExceptionEnum.SAVE_USER_REUSE);
        }
        // 使用md5对密码进行加密
        user.setPassword(MD5Util.MD5Encode(user.getPassword() + "", "UTF-8"));
        // 存入数据库
        try {
            userDao.insert(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new XmException(ExceptionEnum.SAVE_USER_ERROR);
        }
    }

    @Override
    public void isUserName(String username) {
        UserEntity one = new UserEntity();
        one.setUsername(username);
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>(one);
        // 先去看看用户名是否重复
        if (userDao.selectCount(wrapper) == 1) {
            // 用户名已存在
            throw new XmException(ExceptionEnum.SAVE_USER_REUSE);
        }
    }

}
