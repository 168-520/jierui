package com.leiyuan.controller;

import com.leiyuan.common.R;
import com.leiyuan.entity.Dish;
import com.leiyuan.service.DishService;
import com.leiyuan.utils.DecodeTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/dish")
@CrossOrigin
public class DishController {

    @Autowired
    private DishService dishService;

    @GetMapping("/page")
    public R list(int page,int pageSize,String name){
        return dishService.getDish(page,pageSize,name);
    }

    @GetMapping("/{id}")
    public R queryDish(@PathVariable long id){
        return dishService.getDishById(id);
    }

//    /**
//     * 删除单个
//     * @param id
//     * @return
//     */
//
//    @DeleteMapping
//    public R deleteDish(long id){
//        return dishService.removeDishById(id);
//    }

    /**
     * 批量删除/删除单个
     * @param id
     * @return
     */
    @DeleteMapping
    public R deleteDishList(String id){
        String[] ids = id.split(",");
        List<Long> idList = new ArrayList<>();

        for (String sid:ids) {
            long lid = Long.parseLong(sid);  //字符串转长整型
            idList.add(lid);
        }
        return dishService.removeDishById(idList);
    }

    @PostMapping("/status/{status}")
    public R updateDishStatus(@PathVariable int status,String ids){
        String[] sid = ids.split(",");
        List<Long> idList = new ArrayList<>();

        for (String id:sid) {
            long lid = Long.parseLong(id);  //字符串转长整型
            idList.add(lid);
        }
        return dishService.updateDishStatusById(status,idList);
    }

    @PostMapping
    public R addDish(HttpServletRequest request, @RequestBody Dish dish){
        String token = request.getHeader("token");
        String username = DecodeTokenUtil.getUsername(token);
        return dishService.saveDish(dish,username);
    }

    @PutMapping
    public R updateDish(HttpServletRequest request, @RequestBody Dish dish){
        String token = request.getHeader("token");
        String username = DecodeTokenUtil.getUsername(token);
        System.out.println(dish);
        return dishService.updateDishAndDishFlavors(dish,username);
    }

    /**
     * 获取某一分类的所有菜品
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    public R getList(long categoryId){
        return dishService.getDishByCategoryId(categoryId);
    }
}
