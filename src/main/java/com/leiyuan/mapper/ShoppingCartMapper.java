package com.leiyuan.mapper;

import com.leiyuan.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    public ShoppingCart selectDishByDishId(@Param("dishId") long dishId,@Param("userId")long userId);

    public ShoppingCart selectSetmealBySetmealId(@Param("setmealId") long setmealId,@Param("userId")long userId);

    public int updateShoppingCartNumber(ShoppingCart shoppingCart);

    public int addShoppingCart(ShoppingCart shoppingCart);

    public List<ShoppingCart> selectAllShoppingCartByUserId(long userId);

    public int deleteShoppingCartByDishId(@Param("dishId") long dishId,@Param("userId")long userId);

    public int deleteShoppingCartBySetmealId(@Param("setmealId") long setmealId,@Param("userId")long userId);

    public int deleteShoppingCart(long userId);
}
