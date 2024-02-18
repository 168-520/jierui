package com.leiyuan.service;

import com.leiyuan.common.R;
import com.leiyuan.entity.Dish;

import java.util.List;

public interface DishService {

    public R getDish(int page,int pageSize,String name);

    public R getDishById(long id);

    public R removeDishById(List<Long> id);

    public R updateDishStatusById(int status,List<Long> id);

    public R saveDish(Dish dish,String username);

    public R updateDishAndDishFlavors(Dish dish,String username);

    public R getDishByCategoryId(long categoryId);
}
