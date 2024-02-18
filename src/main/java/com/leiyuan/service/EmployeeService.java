package com.leiyuan.service;

import com.leiyuan.common.R;
import com.leiyuan.entity.Employee;

public interface EmployeeService {
    public R getEmployeeByUsernameAndPassword(String username, String password);

    public R getEmployeeByPage(Integer page,Integer pageSize,String name);

    public R getEmployeeById(long id);

    public R setEmployeeById(Employee employee,String username);

    public R addEmployee(Employee employee,String username);
}
