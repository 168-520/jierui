package com.leiyuan.service.impl;

import com.leiyuan.common.R;
import com.leiyuan.entity.Employee;
import com.leiyuan.entity.ShoppingCart;
import com.leiyuan.entity.User;
import com.leiyuan.mapper.EmployeeMapper;
import com.leiyuan.mapper.ShoppingCartMapper;
import com.leiyuan.mapper.UserMapper;
import com.leiyuan.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Override
    public R getShoppingCartList(String mail) {
        User user = userMapper.selectUserByMail(mail);
        List<ShoppingCart> shoppingCarts = shoppingCartMapper.selectAllShoppingCartByUserId(user.getId());
        return new R("1","查询购物车成功",shoppingCarts);
    }

    //添加菜品到购物车
    @Override
    public R addDish(ShoppingCart shoppingCart,String mail) {
        User user = userMapper.selectUserByMail(mail);
        Long userId = user.getId();

        ShoppingCart shoppingCart1 = shoppingCartMapper.selectDishByDishId(shoppingCart.getDishId(),userId);
        if (shoppingCart1 != null){
            //购物车已有商品
            Integer number = shoppingCart1.getNumber();
            shoppingCart1.setNumber(number + 1);
            int i = shoppingCartMapper.updateShoppingCartNumber(shoppingCart1);
            if (i > 0){
                return new R("1","添加菜品成功",shoppingCart1);
            }
        }else {
            shoppingCart.setUserId(userId);
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            int i = shoppingCartMapper.addShoppingCart(shoppingCart);
            if (i > 0){
                return new R("1","添加菜品成功",shoppingCart);
            }
        }
        return new R("401","添加购物车失败",null);
    }

    //添加套餐到购物车
    @Override
    public R addSetmeal(ShoppingCart shoppingCart,String mail) {
        User user = userMapper.selectUserByMail(mail);
        Long userId = user.getId();
        ShoppingCart shoppingCart1 = shoppingCartMapper.selectSetmealBySetmealId(shoppingCart.getSetmealId(),userId);
        if (shoppingCart1 != null){
            //购物车已有套餐
            shoppingCart1.setNumber(shoppingCart1.getNumber() + 1);
            int i = shoppingCartMapper.updateShoppingCartNumber(shoppingCart1);
            if (i > 0){
                return new R("1","添加套餐成功",shoppingCart1);
            }
        }else {
            shoppingCart.setUserId(userId);
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            int i = shoppingCartMapper.addShoppingCart(shoppingCart);
            if (i > 0){
                return new R("1","添加套餐成功",shoppingCart);
            }
        }
        return new R("401","添加购物车失败",null);
    }

    //删除购物车菜品
    @Override
    public R subDish(ShoppingCart shoppingCart, String mail) {
        User user = userMapper.selectUserByMail(mail);
        Long userId = user.getId();

        ShoppingCart shoppingCart1 = shoppingCartMapper.selectDishByDishId(shoppingCart.getDishId(),userId);
        Integer number = shoppingCart1.getNumber();
        if (number > 1){
            //菜品数量减一
            shoppingCart1.setNumber(number - 1);
            int i = shoppingCartMapper.updateShoppingCartNumber(shoppingCart1);
            if (i > 0){
                return new R("1","删除菜品成功",shoppingCart1);
            }
        }else {
            //当前菜品已为一，删除菜品
            shoppingCartMapper.deleteShoppingCartByDishId(shoppingCart.getDishId(),userId);
            shoppingCart1.setNumber(0);
            return new R("1","删除菜品成功",shoppingCart1);
        }
        return new R("401","删除菜品失败",null);
    }

    //删除购物车套餐
    @Override
    public R subSetmeal(ShoppingCart shoppingCart, String mail) {
        User user = userMapper.selectUserByMail(mail);
        Long userId = user.getId();
        ShoppingCart shoppingCart1 = shoppingCartMapper.selectSetmealBySetmealId(shoppingCart.getSetmealId(),userId);
        Integer number = shoppingCart1.getNumber();
        if (number > 1){
            //套餐数量减一
            shoppingCart1.setNumber(number - 1);
            int i = shoppingCartMapper.updateShoppingCartNumber(shoppingCart1);
            if (i > 0){
                return new R("1","删除套餐成功",shoppingCart1);
            }
        }else {
            //当前套餐已为一，删除套餐
            shoppingCartMapper.deleteShoppingCartBySetmealId(shoppingCart.getSetmealId(),userId);
            shoppingCart1.setNumber(0);
            return new R("1","删除套餐成功",shoppingCart1);
        }
        return new R("401","删除套餐失败",null);
    }

    //清空购物车
    @Override
    public R removeShoppingCart(String mail) {
        User user = userMapper.selectUserByMail(mail);
        Long userId = user.getId();
        int i = shoppingCartMapper.deleteShoppingCart(userId);
        if (i > 0){
            return new R("1","清空购物车成功",null);
        }
        return new R("401","网络异常",null);
    }


}
