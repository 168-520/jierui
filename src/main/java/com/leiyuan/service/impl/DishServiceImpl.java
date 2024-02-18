package com.leiyuan.service.impl;

import com.github.pagehelper.PageHelper;
import com.leiyuan.common.R;
import com.leiyuan.entity.Category;
import com.leiyuan.entity.Dish;
import com.leiyuan.entity.DishFlavor;
import com.leiyuan.entity.Employee;
import com.leiyuan.mapper.CategoryMapper;
import com.leiyuan.mapper.DishFlavorsMapper;
import com.leiyuan.mapper.DishMapper;
import com.leiyuan.mapper.EmployeeMapper;
import com.leiyuan.service.DishService;
import com.leiyuan.utils.PageUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private DishFlavorsMapper dishFlavorsMapper;

    @Override
    public R getDish(int page, int pageSize, String name) {
        int index = (page - 1) * pageSize;
        PageHelper.offsetPage(index,pageSize);
        List<Dish> dishes = dishMapper.selectDishByPageAndName(name);
        int total = dishMapper.selectDishTotal();
        PageUtil<Dish> pageData = new PageUtil<>(total,dishes);
        return new R("1","请求成功",pageData);
    }

    @Override
    public R getDishById(long id) {
        Dish dish = dishMapper.selectDishById(id);
        return new R("1","查询成功",dish);
    }

    @Override
    public R removeDishById(List<Long> id) {
        int i = dishMapper.deleteDishById(id);
        if (i > 0){
            return new R("1","删除成功",null);
        }
        return new R("401","网络异常",null);
    }

    @Override
    public R updateDishStatusById(int status,List<Long> id) {
        int i = dishMapper.updateDishStatusById(status,id);
        if (i > 0){
            return new R("1","更新成功",null);
        }
        return new R("401","网络异常",null);
    }

    @Override
    public R saveDish(Dish dish,String username) {
         String code = UUID.randomUUID().toString();
         dish.setCode(code);
        List<DishFlavor> flavors = dish.getFlavors();

        Employee employee = employeeMapper.selectEmployeeByUsernameAndPassword(username);
        Long id = employee.getId();

        dish.setCreateTime(LocalDateTime.now());
        dish.setUpdateTime(LocalDateTime.now());

        dish.setCreateUser(id);
        dish.setUpdateUser(id);

        int i = dishMapper.addDish(dish);

        Dish dish1 = dishMapper.selectDishByCode(code);
        Long id1 = dish1.getId();
        for (DishFlavor dishFlavor : flavors){
            dishFlavor.setDishId(id1);
            dishFlavor.setCreateTime(LocalDateTime.now());
            dishFlavor.setUpdateTime(LocalDateTime.now());
            dishFlavor.setCreateUser(id);
            dishFlavor.setUpdateUser(id);
        }
        dishFlavorsMapper.updateDishFlavors(flavors);
        if (i > 0){
            return new R("1","添加成功",null);
        }
        return new R("401","网络异常",null);
    }

    @Override
    public R updateDishAndDishFlavors(Dish dish, String username) {
        dish.setUpdateTime(LocalDateTime.now());

        Employee employee = employeeMapper.selectEmployeeByUsernameAndPassword(username);
        Long id = employee.getId();
        dish.setUpdateUser(id);

        List<DishFlavor> dishFlavors = dish.getFlavors();
        for (DishFlavor flavors : dishFlavors) {
            flavors.setDishId(dish.getId());
            flavors.setUpdateTime(LocalDateTime.now());
            flavors.setUpdateUser(id);
            if (flavors.getCreateTime() == null){
                flavors.setCreateTime(LocalDateTime.now());
            }
            if (flavors.getCreateUser() == null){
                flavors.setCreateUser(id);
            }
            if(flavors.getId() != null){
                dishFlavorsMapper.deleteDishFlavors(flavors.getId());
            }
        }
        dishFlavorsMapper.updateDishFlavors(dishFlavors);

        int i = dishMapper.updateDishAndDishFlavors(dish);
        if (i > 0){
            return new R("1","更新成功",null);
        }
        return new R("401","网络异常",null);
    }

    @Override
    public R getDishByCategoryId(long categoryId) {
        List<Dish> dishes = dishMapper.selectAllDishByCategoryId(categoryId);
        return new R("1","查询成功",dishes);
    }

}
