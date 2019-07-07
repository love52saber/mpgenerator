package com.hedian.myGenerator;

import lombok.Data;

import java.util.List;

/**
 * 实体类
 * @author  lvzb.software@qq.com
 *
 */
@Data
public class Entity {

    // 实体所在的包名
    private String javaPackage;
    // 实体类名
    private String className;
    // 父类名
    private String superclass;
    // 属性集合
    List<Property> properties;
    // 是否有构造函数
    private boolean constructors;

}