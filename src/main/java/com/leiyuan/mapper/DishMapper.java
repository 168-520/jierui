package com.leiyuan.mapper;

import com.leiyuan.entity.Category;
import com.leiyuan.entity.Dish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DishMapper {

    public List<Dish> selectDishByPageAndName(String name);
    public int selectDishTotal();

    public Dish selectDishById(long id);

    public int deleteDishById(List<Long> id);

    public int updateDishStatusById(@Param("status") int status,@Param("ids") List<Long> ids);

    public int addDish(Dish dish);
    public Dish selectDishByCode(String code);

    public int updateDishAndDishFlavors(Dish dish);

    public List<Dish> selectAllDishByCategoryId(long categoryId);
}
