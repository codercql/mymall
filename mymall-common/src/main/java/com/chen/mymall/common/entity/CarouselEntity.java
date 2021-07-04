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
 * @date 2021-04-29 17:49:00
 */
@Data
@TableName("carousel")
public class CarouselEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 轮播图id
	 */
	@TableId
	private Integer carouselId;
	/**
	 * 轮播图片
	 */
	private String imgPath;
	/**
	 * 轮播图描述
	 */
	private String describes;

}
