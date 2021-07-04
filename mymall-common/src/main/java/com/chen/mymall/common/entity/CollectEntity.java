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
@TableName("collect")
public class CollectEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 购物车id
	 */
	@TableId
	private Integer id;
	/**
	 * 用户id
	 */
	private Integer userId;
	/**
	 * 商品id
	 */
	private Integer productId;
	/**
	 * 加入时间
	 */
	private Long collectTime;

}
