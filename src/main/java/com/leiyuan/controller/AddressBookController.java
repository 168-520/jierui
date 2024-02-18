package com.leiyuan.controller;

import com.leiyuan.common.R;
import com.leiyuan.entity.AddressBook;
import com.leiyuan.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author leiyuan
 * @version 1.0
 * @description 地址接口
 */
@RestController
@RequestMapping("/addressBook")
@CrossOrigin
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    @GetMapping("/list")
    public R list(HttpServletRequest request){
        String token = request.getHeader("token");
        return addressBookService.list(token);
    }

    @PutMapping("/default")
    public R setDefault(HttpServletRequest request,@RequestBody AddressBook addressBook){
        String token = request.getHeader("token");
        return addressBookService.defaultAddress(addressBook,token);
    }
}
