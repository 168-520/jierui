package com.leiyuan.service;

import com.leiyuan.common.R;
import com.leiyuan.entity.ShoppingCart;

public interface ShoppingCartService {

    public R getShoppingCartList(String mail);

    public R addDish(ShoppingCart shoppingCart,String mail);

    public R addSetmeal(ShoppingCart shoppingCart,String mail);

    public R subDish(ShoppingCart shoppingCart,String mail);

    public R subSetmeal(ShoppingCart shoppingCart,String mail);

    public R removeShoppingCart(String mail);

}
