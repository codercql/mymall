package com.chen.mymall.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 *
 *
 * @author chenqiulu
 * @email 1281371382@qq.com
 * @date 2021-04-29 17:49:55
 */
@Data
@TableName("order")
public class OrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 订单id
	 */
	@TableId
	private Integer id;
	/**
	 * 下单id
	 */
	private String orderId;
	/**
	 * 用户id
	 */
	private Integer userId;
	/**
	 * 商品id
	 */
	private Integer productId;
	/**
	 * 商品名
	 */
	private Integer productNum;
	/**
	 * 商品价格
	 */
	private Double productPrice;
	/**
	 * 下单时间
	 */
	private Long orderTime;

}
