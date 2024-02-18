package com.leiyuan.mapper;

import com.leiyuan.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author leiyuan
 * @version 1.0
 * @description TODO
 * @date 2023/8/9 23:51
 */
@Mapper
public interface AddressBookMapper {

    /**
     * @description 根据用户id查询用户地址
     * @param id
     * @return java.util.List<com.leiyuan.entity.AddressBook>
     * @author leiyuan
    */
    List<AddressBook> selectUserAllAddressByUserId(long id);

    /**
     * @description 根据用户id和默认地址状态查询用户地址
     * @param id 用户id
     * @param status 是否默认地址
     * @return java.util.List<com.leiyuan.entity.AddressBook>
     * @author leiyuan
    */
    List<AddressBook> selectUserAddressByUserIdAndStatus(long id,Integer status);

    /**
     * @description 更新默认地址
     * @param addressBook
     * @return int
     * @author leiyuan
     * @date 2023/8/10 0:38
    */
    int updateUserAddressStatusByAddressId(AddressBook addressBook);
}
