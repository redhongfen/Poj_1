package com.example.poj_1.common;

import lombok.Data;

import java.util.List;

@Data
public class Page<T> {


    private Integer toto;
    private List<T> list;

}
