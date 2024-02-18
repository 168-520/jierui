package com.leiyuan.service;

import com.leiyuan.common.R;
import com.leiyuan.entity.AddressBook;

/**
 * @author leiyuan
 * @version 1.0
 * @description 地址相关业务
 * @date 2023/8/9 21:31
 */
public interface AddressBookService {

    /**
     * @description 获取用户所有地址
     *
     * @return com.leiyuan.common.R
     * @author leiyuan
    */
    R list(String token);

    /**
     * @description 修改默认地址
     * @param addressBook
     * @return com.leiyuan.common.R
     * @author leiyuan
    */
    R defaultAddress(AddressBook addressBook,String token);
}
