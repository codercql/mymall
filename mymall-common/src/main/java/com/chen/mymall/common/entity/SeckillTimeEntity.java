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
 * @date 2021-04-29 17:47:42
 */
@Data
@TableName("seckill_time")
public class SeckillTimeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 秒杀时间id
	 */
	@TableId
	private Integer timeId;
	/**
	 * 秒杀开始时间
	 */
	private Long startTime;
	/**
	 * 秒杀结束时间
	 */
	private Long endTime;

}
