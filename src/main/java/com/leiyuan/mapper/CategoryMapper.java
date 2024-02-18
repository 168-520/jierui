package com.leiyuan.mapper;

import com.leiyuan.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    public List<Category> selectCategoryList();
    public int selectCategoryTotal();

    public int updateCategoryById(Category category);

    public int deleteCategoryById(long id);

    public int addCategory(Category category);

    public List<Category> selectCategoryListByType(Integer type);

}
