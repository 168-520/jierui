package com.leiyuan.controller;

import com.leiyuan.common.R;
import com.leiyuan.entity.Employee;
import com.leiyuan.service.EmployeeService;
import com.leiyuan.utils.DecodeTokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/employee")
@CrossOrigin
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登录
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R EmployeeLogin(@RequestBody Employee employee){
        return employeeService.getEmployeeByUsernameAndPassword(employee.getUsername(),employee.getPassword());
    }

    @GetMapping("/page")
    public R page(Integer page,Integer pageSize,String name){
        return employeeService.getEmployeeByPage(page,pageSize,name);
    }

    /**
     * 状态修改/员工修改
     * @param employee
     * @return
     */
    @PutMapping
    public R updateStatus(HttpServletRequest request,@RequestBody Employee employee){
        String token = request.getHeader("token");
        String username = DecodeTokenUtil.getUsername(token);
        return employeeService.setEmployeeById(employee,username);
    }

    @GetMapping("/{id}")
    public R selectEmployeeById(@PathVariable long id){
        return employeeService.getEmployeeById(id);
    }

    @PostMapping
    public R addEmployee(HttpServletRequest request,@RequestBody Employee employee){
        String token = request.getHeader("token");
        String username = DecodeTokenUtil.getUsername(token);
        return employeeService.addEmployee(employee,username);
    }
}
