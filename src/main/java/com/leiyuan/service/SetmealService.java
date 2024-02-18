package com.leiyuan.service;

import com.leiyuan.common.R;
import com.leiyuan.entity.Setmeal;

import java.util.List;

public interface SetmealService {

    public R getSetmealList(int page,int pageSize,String name);

    public R setSetmealStatus(int status, List<Long> ids);

    public R addSetmeal(Setmeal setmeal,String username);

    public R deleteSetmeal(List<Long> ids);

    public R getSetmealWithDish(long id);

    public R setSetmeal(Setmeal setmeal,String username);

    public R getSetmealByCategoryId(Setmeal setmeal);
}
