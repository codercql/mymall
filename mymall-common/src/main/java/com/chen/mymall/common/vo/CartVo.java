package com.chen.mymall.common.vo;

import lombok.Data;

/**
 * @Auther:
 * @Date: 2020-04-30 12:37
 * @Description:购物车
 */
@Data
public class CartVo {

    private Integer id;

    private Integer productId;

    private String productName;

    private String productImg;

    private Double price;

    private Integer num;

    private Integer maxNum;

    private boolean check;
}
