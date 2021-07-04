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
@TableName("product_picture")
public class ProductPictureEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 图片id
	 */
	@TableId
	private Integer id;
	/**
	 * 商品id
	 */
	private Integer productId;
	/**
	 * 商品图片
	 */
	private String productPicture;
	/**
	 * 图片详情
	 */
	private String intro;

}
