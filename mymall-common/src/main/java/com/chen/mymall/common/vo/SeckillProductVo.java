package com.chen.mymall.common.vo;

import com.chen.mymall.common.entity.SeckillProductEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: wdd
 * @Date: 2020-03-28 20:17
 * @Description:
 */
@Data
public class SeckillProductVo extends SeckillProductEntity implements Serializable {

    private String productName;

    private Double productPrice;

    private String productPicture;

    private Long startTime;

    private Long endTime;


}
