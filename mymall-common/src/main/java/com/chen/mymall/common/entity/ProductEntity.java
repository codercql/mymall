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
@TableName("product")
public class ProductEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 商品id
	 */
	@TableId
	private Integer productId;
	/**
	 * 商品名
	 */
	private String productName;
	/**
	 * 分类id
	 */
	private Integer categoryId;
	/**
	 * 商品title
	 */
	private String productTitle;
	/**
	 * 商品详情
	 */
	private String productIntro;
	/**
	 * 商品图片
	 */
	private String productPicture;
	/**
	 * 商品价格
	 */
	private Double productPrice;
	/**
	 * 商品售卖价格
	 */
	private Double productSellingPrice;
	/**
	 * 商品数量
	 */
	private Integer productNum;
	/**
	 * 商品售出量
	 */
	private Integer productSales;

}
