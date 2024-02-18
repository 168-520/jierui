package com.leiyuan.mapper;

import com.leiyuan.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    public Employee selectEmployeeByUsernameAndPassword(String username);

    public List<Employee> selectEmployeeByPage();
    public List<Employee> selectEmployeeByPageAndName(String name);

    public Integer selectEmployeeTotal();
    public Integer selectEmployeeTotalByName(String name);

    public int updateEmployeeStatusById(Employee employee);

    public List<Employee> selectEmployeeById(long id);

    public int updateEmployeeById(Employee employee);

    public int addEmployee(Employee employee);

}
