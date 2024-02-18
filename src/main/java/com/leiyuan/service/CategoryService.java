package com.leiyuan.service;

import com.leiyuan.common.R;
import com.leiyuan.entity.Category;

public interface CategoryService {

    public R getCategoryList(int page,int pageSize);

    public R setCategoryById(Category category,String username);

    public R removeCategoryById(long id);

    public R saveCategory(Category category,String username);

    public R getCategoryTypeName(Integer type);

}
