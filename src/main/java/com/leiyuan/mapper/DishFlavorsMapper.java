package com.leiyuan.mapper;

import com.leiyuan.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishFlavorsMapper {

    public int updateDishFlavors(List<DishFlavor> dishFlavor);

    public int deleteDishFlavors(long id);
}
