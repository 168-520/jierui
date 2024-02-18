package com.leiyuan.service.impl;

import com.github.pagehelper.PageHelper;
import com.leiyuan.common.R;
import com.leiyuan.entity.DishFlavor;
import com.leiyuan.entity.Employee;
import com.leiyuan.entity.Setmeal;
import com.leiyuan.entity.SetmealDish;
import com.leiyuan.mapper.EmployeeMapper;
import com.leiyuan.mapper.SetmealDishMapper;
import com.leiyuan.mapper.SetmealMapper;
import com.leiyuan.service.SetmealService;
import com.leiyuan.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Override
    public R getSetmealList(int page, int pageSize, String name) {
        int index = (page - 1) * pageSize;
        int total = setmealMapper.SelectSetmealTotal(name);
        PageHelper.offsetPage(index,pageSize);
        List<Setmeal> setmeals = setmealMapper.selectAllSetmealByPageAndName(name);
        PageUtil<Setmeal> pageData = new PageUtil<>(total,setmeals);
        return new R("1","请求成功",pageData);
    }

    @Override
    public R setSetmealStatus(int status, List<Long> ids) {
        int i = setmealMapper.updateSetmealStatusById(status,ids);
        if (i > 0){
            return new R("1","修改成功",null);
        }
        return new R("401","网络异常",null);
    }

    @Override
    public R addSetmeal(Setmeal setmeal,String username) {
        Long id = employeeMapper.selectEmployeeByUsernameAndPassword(username).getId();

        String code = UUID.randomUUID().toString();

        //获取套餐中的菜品信息
        List<SetmealDish> setmealDishes = setmeal.getSetmealDishes();

        setmeal.setCode(code);
        setmeal.setCreateTime(LocalDateTime.now());
        setmeal.setUpdateTime(LocalDateTime.now());
        setmeal.setCreateUser(id);
        setmeal.setUpdateUser(id);
        setmealMapper.addSetmeal(setmeal);

        Long id1 = setmealMapper.selectSetmealByCode(code).getId();
        for (SetmealDish setmealDish : setmealDishes){
            setmealDish.setSetmealId(id1);
            setmealDish.setCreateTime(LocalDateTime.now());
            setmealDish.setUpdateTime(LocalDateTime.now());
            setmealDish.setCreateUser(id);
            setmealDish.setUpdateUser(id);
        }
        int i = setmealDishMapper.addSetmealDish(setmealDishes);

        if (i > 0){
            return new R("1","新增成功",null);
        }
        return new R("401","网络异常",null);
    }

    @Override
    public R deleteSetmeal(List<Long> ids) {
        int i = setmealMapper.deleteSetmealByIds(ids);
        if (i > 0){
            return new R("1","删除成功",null);
        }
        return new R("401","网络异常",null);
    }

    @Override
    public R getSetmealWithDish(long id) {
        Setmeal setmeal = setmealMapper.selectSetmealWithDishById(id);
        return new R("1","请求成功",setmeal);
    }

    @Override
    public R setSetmeal(Setmeal setmeal,String username) {
        //获取当前登录用户id
        Long id = employeeMapper.selectEmployeeByUsernameAndPassword(username).getId();
        //更新套餐信息
        setmeal.setUpdateUser(id);
        setmeal.setUpdateTime(LocalDateTime.now());
        //更新套餐关联的菜品信息
        List<SetmealDish> setmealDishes = setmeal.getSetmealDishes();
        for (SetmealDish setmealDish : setmealDishes){
            setmealDish.setSetmealId(setmeal.getId());
            setmealDish.setUpdateTime(LocalDateTime.now());
            setmealDish.setUpdateUser(id);
            if (setmealDish.getId() == null){
                setmealDish.setCreateTime(LocalDateTime.now());
                setmealDish.setCreateUser(id);
            }else {
                setmealDishMapper.deleteSetmeal_dish(setmealDish.getId());
            }
        }
        setmealDishMapper.addSetmealDish(setmealDishes);

        int i = setmealMapper.updateSetmeal(setmeal);

        if (i > 0){
            return new R("1","修改成功",null);
        }
        return new R("401","网络异常",null);
    }

    @Override
    public R getSetmealByCategoryId(Setmeal setmeal) {
        Long categoryId = setmeal.getCategoryId();
        Integer status = setmeal.getStatus();
        List<Setmeal> setmeals = setmealMapper.selectSetmealByCategoryId(categoryId, status);
        return new R("1","请求成功",setmeals);
    }
}
