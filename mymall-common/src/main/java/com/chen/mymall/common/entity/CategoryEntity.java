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
@TableName("category")
public class CategoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 商品分类id
	 */
	@TableId
	private Integer categoryId;
	/**
	 * 商品分类名
	 */
	private String categoryName;

}
