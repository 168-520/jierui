package com.leiyuan.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageUtil<T> {
    private Integer total;
    private List<T> data;
}
