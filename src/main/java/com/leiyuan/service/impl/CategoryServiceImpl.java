package com.leiyuan.service.impl;

import com.github.pagehelper.PageHelper;
import com.leiyuan.common.R;
import com.leiyuan.entity.Category;
import com.leiyuan.entity.Dish;
import com.leiyuan.entity.Employee;
import com.leiyuan.mapper.CategoryMapper;
import com.leiyuan.mapper.DishMapper;
import com.leiyuan.mapper.EmployeeMapper;
import com.leiyuan.service.CategoryService;
import com.leiyuan.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private DishMapper dishMapper;

    @Override
    public R getCategoryList(int page,int pageSize) {
        int index = (page - 1) * pageSize;
        PageHelper.offsetPage(index,pageSize);
        List<Category> categories = categoryMapper.selectCategoryList();
        int total = categoryMapper.selectCategoryTotal();
        PageUtil<Category> pageDate = new PageUtil<>(total,categories);
        return new R("1","请求成功",pageDate);
    }

    @Override
    public R setCategoryById(Category category,String username) {
        Employee employee = employeeMapper.selectEmployeeByUsernameAndPassword(username);
        Long id = employee.getId();
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(id);
        int i = categoryMapper.updateCategoryById(category);
        if (i>0){
            return new R("1","修改成功",null);
        }
        return new R("401","网络异常",null);
    }

    @Override
    public R removeCategoryById(long id) {
        List<Dish> dishes = dishMapper.selectAllDishByCategoryId(id);
        if (dishes.size() == 0){
            int i = categoryMapper.deleteCategoryById(id);
            if (i > 0){
                return new R("1","操作成功",null);
            }
        }
        return new R("401","网络异常",null);
    }

    @Override
    public R saveCategory(Category category, String username) {
        Employee employee = employeeMapper.selectEmployeeByUsernameAndPassword(username);
        Long id = employee.getId();
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        category.setCreateUser(id);
        category.setUpdateUser(id);
        int i = categoryMapper.addCategory(category);
        if (i > 0){
            return new R("1","添加成功",null);
        }
        return new R("401","网络异常",null);
    }

    @Override
    public R getCategoryTypeName(Integer type){
        List<Category> categories = categoryMapper.selectCategoryListByType(type);
        return new R("1","请求成功",categories);
    }
}
