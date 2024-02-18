package com.leiyuan.service.impl;

import com.leiyuan.common.R;
import com.leiyuan.entity.AddressBook;
import com.leiyuan.entity.User;
import com.leiyuan.mapper.AddressBookMapper;
import com.leiyuan.mapper.UserMapper;
import com.leiyuan.service.AddressBookService;
import com.leiyuan.utils.DecodeTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author leiyuan
 * @version 1.0
 * @description 地址相关业务实现类
 * @date 2023/8/9 21:44
 */
@Service
@Slf4j
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AddressBookMapper addressBookMapper;
    @Override
    public R list(String token) {
        log.info("token:{}",token);
        String userMail = DecodeTokenUtil.getUserMail(token);
        User user = userMapper.selectUserByMail(userMail);
        List<AddressBook> addressBooks = addressBookMapper.selectUserAllAddressByUserId(user.getId());
        return new R("1","获取地址成功",addressBooks);
    }

    @Transactional
    @Override
    public R defaultAddress(AddressBook addressBook,String token) {
        String userMail = DecodeTokenUtil.getUserMail(token);
        User user = userMapper.selectUserByMail(userMail);
        List<AddressBook> addressBooks = addressBookMapper.selectUserAddressByUserIdAndStatus(user.getId(), 1);
        for (AddressBook address:addressBooks) {
            address.setIsDefault(0);
            addressBookMapper.updateUserAddressStatusByAddressId(address);
        }
        addressBook.setIsDefault(1);
        addressBookMapper.updateUserAddressStatusByAddressId(addressBook);
        return new R("1","设置默认地址成功",null);
    }
}
