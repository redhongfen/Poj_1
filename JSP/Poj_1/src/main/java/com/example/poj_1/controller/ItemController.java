//package com.example.poj_1.controller;
//
//import com.example.poj_1.model.Item;
//import com.example.poj_1.service.ItemService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/items")
//@CrossOrigin(origins = "http://localhost:8080") // 允许从前端 Vue 发起跨域请求
//public class ItemController {
//
//    @Autowired
//    private ItemService itemService;
//
//    // 获取所有物品
//    @GetMapping
//    public List<Item> getAllItems() {
//        return itemService.getAllItems();
//    }
//
//    // 根据ID获取物品
//    @GetMapping("/{id}")
//    public Optional<Item> getItemById(@PathVariable Long id) {
//        return itemService.getItemById(id);
//    }
//
//    // 添加新物品
//    @PostMapping
//    public Item createItem(@RequestBody Item item) {
//        return itemService.saveItem(item);
//    }
//
//    // 更新物品信息
//    @PutMapping("/{id}")
//    public Item updateItem(@PathVariable Long id, @RequestBody Item item) {
//        item.setId(id);  // 设置物品的ID
//        return itemService.saveItem(item);
//    }
//
//    // 删除物品
//    @DeleteMapping("/{id}")
//    public void deleteItem(@PathVariable Long id) {
//        itemService.deleteItem(id);
//    }
//}