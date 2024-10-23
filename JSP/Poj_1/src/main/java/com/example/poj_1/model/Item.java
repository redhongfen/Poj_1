//package com.example.poj_1.model;
//
//import jakarta.persistence.Entity;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import javax.persistence.Entity;
//import javax.persistence.Table;
//import javax.persistence.Id;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Column;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//@Entity
//@Table(name = "items")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class Item {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自动生成 ID
//    private Long id;
//
//    @Column(nullable = false) // 名称不能为空
//    private String name;
//
//    @Column(nullable = false) // 价格不能为空
//    private Double price;
//
//    @Column(nullable = false) // 数量不能为空
//    private Integer quantity;
//
//    @Column(length = 500) // 备注，设置最大长度为500字符
//    private String remarks;
//
//    @Column // 图片的 URL
//    private String image;
//
//    // 你可以继续添加其他字段
//    // 例如：商品类别、上架时间等
//}