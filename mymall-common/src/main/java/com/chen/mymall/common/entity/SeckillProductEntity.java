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
 * @date 2021-04-29 17:47:43
 */
@Data
@TableName("seckill_product")
public class SeckillProductEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 秒杀id
	 */
	@TableId
	private Integer seckillId;
	/**
	 * 商品id
	 */
	private Integer productId;
	/**
	 * 秒杀价格
	 */
	private Double seckillPrice;
	/**
	 * 秒杀库存
	 */
	private Integer seckillStock;
	/**
	 * 秒杀时间id
	 */
	private Integer timeId;

}
