package com.chen.mymall.common.vo;

import com.chen.mymall.common.entity.OrderEntity;
import lombok.Data;

/**
 * @Auther: wdd
 * @Date: 2020-03-27 16:29
 * @Description:
 */
@Data
public class OrderVo extends OrderEntity {

    private String productName;

    private String productPicture;

}
