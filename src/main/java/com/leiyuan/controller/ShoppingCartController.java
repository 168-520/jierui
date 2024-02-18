package com.leiyuan.controller;

import com.leiyuan.common.R;
import com.leiyuan.entity.ShoppingCart;
import com.leiyuan.service.ShoppingCartService;
import com.leiyuan.utils.DecodeTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/shoppingCart")
@CrossOrigin
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/list")
    public R list(HttpServletRequest request){
        String token = request.getHeader("token");
        String mail = DecodeTokenUtil.getUserMail(token);
        return shoppingCartService.getShoppingCartList(mail);
    }

    @PostMapping("/add")
    public R addShoppingCart(HttpServletRequest request, @RequestBody ShoppingCart shoppingCart){
        String token = request.getHeader("token");
        String mail = DecodeTokenUtil.getUserMail(token);

        Long dishId = shoppingCart.getDishId();
        if (dishId != null){
            //添加的是菜品
            return shoppingCartService.addDish(shoppingCart,mail);
        }else {
            //添加的是套餐
            return shoppingCartService.addSetmeal(shoppingCart,mail);
        }
    }

    @PostMapping("/sub")
    public R subShoppingCart(HttpServletRequest request, @RequestBody ShoppingCart shoppingCart){
        String token = request.getHeader("token");
        String mail = DecodeTokenUtil.getUserMail(token);
        Long dishId = shoppingCart.getDishId();
        if (dishId != null){
            //添加的是菜品
            return shoppingCartService.subDish(shoppingCart,mail);
        }else {
            //添加的是套餐
            return shoppingCartService.subSetmeal(shoppingCart,mail);
        }
    }

    @DeleteMapping("/clean")
    public R deleteShoppingCart(HttpServletRequest request){
        String token = request.getHeader("token");
        String mail = DecodeTokenUtil.getUserMail(token);
        return shoppingCartService.removeShoppingCart(mail);
    }

}
