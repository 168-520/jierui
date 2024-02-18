package com.leiyuan.service.impl;

import com.github.pagehelper.PageHelper;
import com.leiyuan.common.R;
import com.leiyuan.entity.Employee;
import com.leiyuan.mapper.EmployeeMapper;
import com.leiyuan.service.EmployeeService;
import com.leiyuan.utils.PageUtil;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public R getEmployeeByUsernameAndPassword(String username, String password) {
        //根据用户名查询用户
        Employee employee = employeeMapper.selectEmployeeByUsernameAndPassword(username);
        //对密码加密
        Md5Hash md5 = new Md5Hash(password,"jierui",8);
        if(employee != null){
            String password1 = employee.getPassword();
            if (password1.equals(md5.toString())){
                JwtBuilder builder = Jwts.builder();
                HashMap<String,Object> map = new HashMap<>();
                map.put("username",employee.getUsername());
                String token = builder.setSubject(username)                 //主题，就是token中携带的数据
                        .setIssuedAt(new Date())                            //设置token的⽣成时间
                        .setId(employee.getId() + "")               //设置⽤户id为token  id
                        .setClaims(map)                                     //map中可以存放⽤户的⻆⾊权限信息
                        .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)) //设置过期时间
                        .signWith(SignatureAlgorithm.HS256, "leiyuan666")     //设置加密⽅式和加密密码
                        .compact();
                return new R("1","登录成功",employee,token);
            }else return new R("401","账号或密码错误",null);
        }
        return new R("401","账号不存在",null);
    }

    @Override
    public R getEmployeeByPage(Integer page, Integer pageSize,String name) {
        int index = (page-1)*pageSize;
        if(name != null && name != ""){
            PageHelper.offsetPage(index,pageSize);
            List<Employee> employees = employeeMapper.selectEmployeeByPageAndName(name);
            int total = employeeMapper.selectEmployeeTotalByName(name);
            PageUtil<Employee> pageUtil = new PageUtil<>(total,employees);
            return new R("1","请求成功",pageUtil);
        }else {
            PageHelper.offsetPage(index,pageSize);
            List<Employee> employees = employeeMapper.selectEmployeeByPage();
            int total = employeeMapper.selectEmployeeTotal();
            PageUtil<Employee> pageUtil = new PageUtil<>(total,employees);
            return new R("1","请求成功",pageUtil);
        }
    }

    @Override
    public R getEmployeeById(long id) {
        List<Employee> employees = employeeMapper.selectEmployeeById(id);
        if (employees.size() == 1){
            Employee employee = employees.get(0);
            return new R("1","请求成功",employee);
        }
        return new R("402","账号异常",null);
    }

    @Override
    public R setEmployeeById(Employee employee,String username) {
        Employee employee1 = employeeMapper.selectEmployeeByUsernameAndPassword(username);
        if (employee.getUsername()==null){
            employee.setUpdateTime(LocalDateTime.now());
            employee.setUpdateUser(employee1.getId());
            int i = employeeMapper.updateEmployeeStatusById(employee);
            if (i>0){
                return new R("1","操作成功",null);
            }
        }else {
            employee.setUpdateTime(LocalDateTime.now());
            employee.setUpdateUser(employee1.getId());
            int i = employeeMapper.updateEmployeeById(employee);
            if (i>0){
                return new R("1","修改成功",null);
            }
        }
        return new R("401","操作失败",null);
    }

    @Override
    public R addEmployee(Employee employee, String username) {
        Employee employee1 = employeeMapper.selectEmployeeByUsernameAndPassword(username);
        Md5Hash md5Pwd = new Md5Hash("123456","jierui",8);
        Long id = employee1.getId();
        employee.setPassword(md5Pwd.toString());
        employee.setStatus(1);
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        employee.setCreateUser(id);
        employee.setUpdateUser(id);
        int i = employeeMapper.addEmployee(employee);
        if (i > 0){
            return new R("1","新增成功",employee);
        }
        return new R("401","网络异常",null);
    }

}
