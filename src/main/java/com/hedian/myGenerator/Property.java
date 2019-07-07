package com.hedian.myGenerator;

import lombok.Data;

/**
 * 实体对应的属性类
 * @author  lvzb.software@qq.com
 *
 */
@Data
public class Property {

    // 属性数据类型
    private String javaType;
    // 属性名称
    private String propertyName;

    private PropertyType propertyType;

}