package com.leiyuan.controller;

import com.leiyuan.common.R;
import com.leiyuan.entity.Category;
import com.leiyuan.entity.Setmeal;
import com.leiyuan.service.SetmealService;
import com.leiyuan.utils.DecodeTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/setmeal")
@CrossOrigin
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @GetMapping("/page")
    public R setmealList(int page,int pageSize,String name){
        return setmealService.getSetmealList(page,pageSize,name);
    }

    @PostMapping("/status/{status}")
    public R updateStatus(@PathVariable int status,String ids){
        String[] sid = ids.split(",");
        List<Long> idList = new ArrayList<>();

        for (String id:sid) {
            long lid = Long.parseLong(id);  //字符串转长整型
            idList.add(lid);
        }

        return setmealService.setSetmealStatus(status,idList);
    }

    @PostMapping
    public R addSetmeal(HttpServletRequest request, @RequestBody Setmeal setmeal){
        String token = request.getHeader("token");
        String username = DecodeTokenUtil.getUsername(token);
        return setmealService.addSetmeal(setmeal,username);
    }

    @DeleteMapping
    public R deleteSetmealById(String ids){
        String[] sid = ids.split(",");
        List<Long> idList = new ArrayList<>();

        for (String id:sid) {
            long lid = Long.parseLong(id);  //字符串转长整型
            idList.add(lid);
        }
        return setmealService.deleteSetmeal(idList);
    }

    @GetMapping("/{id}")
    public R getSetmealById(@PathVariable long id){
        return setmealService.getSetmealWithDish(id);
    }

    @PutMapping
    public R setSetmealAndDish(HttpServletRequest request,@RequestBody Setmeal setmeal){
        String token = request.getHeader("token");
        String username = DecodeTokenUtil.getUsername(token);
        return setmealService.setSetmeal(setmeal,username);
    }

    @GetMapping("/list")
    public R getSetmealList(Setmeal setmeal){
        return setmealService.getSetmealByCategoryId(setmeal);
    }
}
