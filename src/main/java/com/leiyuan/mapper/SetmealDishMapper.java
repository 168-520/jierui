package com.leiyuan.mapper;

import com.leiyuan.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    public int addSetmealDish(List<SetmealDish> setmealDishes);

    public int deleteSetmeal_dish(long id);
}
