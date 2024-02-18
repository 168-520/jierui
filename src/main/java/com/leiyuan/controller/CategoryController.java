package com.leiyuan.controller;

import com.leiyuan.common.R;
import com.leiyuan.entity.Category;
import com.leiyuan.service.CategoryService;
import com.leiyuan.utils.DecodeTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/category")
@CrossOrigin
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/page")
    public R list(int page,int pageSize){
        return categoryService.getCategoryList(page,pageSize);
    }

    @PutMapping
    public R updateCategory(HttpServletRequest request, @RequestBody Category category){
        String token = request.getHeader("token");
        String username = DecodeTokenUtil.getUsername(token);
        return categoryService.setCategoryById(category,username);
    }

    @DeleteMapping
    public R deleteCategory(long id){
        return categoryService.removeCategoryById(id);
    }

    @PostMapping
    public R addCategory(HttpServletRequest request, @RequestBody Category category){
        String token = request.getHeader("token");
        String username = DecodeTokenUtil.getUsername(token);
        return categoryService.saveCategory(category,username);
    }

    @GetMapping("/list")
    public R list(Integer type){
        return categoryService.getCategoryTypeName(type);
    }
}
