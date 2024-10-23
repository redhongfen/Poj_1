//package com.example.poj_1.service;
//
//import com.example.poj_1.model.Item;
//import com.example.poj_1.repository.ItemRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class ItemService {
//
//    @Autowired
//    private ItemRepository itemRepository;
//
//    // 获取所有物品
//    public List<Item> getAllItems() {
//        return itemRepository.findAll();
//    }
//
//    // 通过ID获取物品
//    public Optional<Item> getItemById(Long id) {
//        return itemRepository.findById(id);
//    }
//
//    // 添加或更新物品
//    public Item saveItem(Item item) {
//        return itemRepository.save(item);
//    }
//
//    // 删除物品
//    public void deleteItem(Long id) {
//        itemRepository.deleteById(id);
//    }
//}