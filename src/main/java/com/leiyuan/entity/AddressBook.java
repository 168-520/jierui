package com.leiyuan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author leiyuan
 * @version 1.0
 * @description 地址实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressBook {
    private Long id;
    private Long userId;
    private String consignee;
    private Integer sex;
    private String phone;
    private String email;
    private String provinceCode;
    private String provinceName;
    private String cityCode;
    private String cityName;
    private String districtCode;
    private String districtName;
    private String detail;
    private String label;
    private Integer isDefault;
    private Date createTime;
    private Date updateTime;
    private Long createUser;
    private Long updateUser;
    private Integer isDeleted;

}
