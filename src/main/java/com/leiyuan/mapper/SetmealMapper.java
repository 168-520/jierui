package com.leiyuan.mapper;

import com.leiyuan.entity.Setmeal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SetmealMapper {

    public List<Setmeal> selectAllSetmealByPageAndName(String name);
    public int SelectSetmealTotal(String name);

    public int updateSetmealStatusById(@Param("status") int status,@Param("ids") List<Long> ids);

    public int addSetmeal(Setmeal setmeal);
    public Setmeal selectSetmealByCode(String code);

    public int deleteSetmealByIds(List<Long> ids);

    public Setmeal selectSetmealWithDishById(long id);

    public int updateSetmeal(Setmeal setmeal);

    public List<Setmeal> selectSetmealByCategoryId(@Param("categoryId")long category,@Param("status") int status);
}
