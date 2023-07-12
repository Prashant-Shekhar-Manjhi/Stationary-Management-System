package com.targetindia.stationarymanagementsystem.services;

import com.targetindia.stationarymanagementsystem.entities.StationaryItem;
import com.targetindia.stationarymanagementsystem.repository.StationaryItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class StationaryItemService {

    @Autowired
    private StationaryItemRepository repository;
    
    //CRUD 
    //CREATE ITEM
    public StationaryItem addItem(StationaryItem item){
        if(item == null) return null;

        try{
            item.setItemName(item.getItemName().toLowerCase());
            repository.save(item);
            return item;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    
    //READ ITEM/ITEMS
    public StationaryItem getOneItem(Integer itemId){
        try{
            Optional<StationaryItem> fetchedItem = repository.findById(itemId);
            return fetchedItem.get();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    
    public List<StationaryItem> getAllItem(){
        try{
            List<StationaryItem> itemList = repository.findAll();
            return itemList;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    
    //UPDATE ITEM
    public StationaryItem updateItem(StationaryItem item){
        Optional<StationaryItem> result = repository.findById(item.getItemId());
        if(result.isPresent()){
            StationaryItem fetchedItem = result.get();

            if(item.getItemName() != null) fetchedItem.setItemName(item.getItemName());

            if(item.getReturnable() != null) fetchedItem.setReturnable(item.getReturnable());
            else fetchedItem.setReturnable(false);

            if(item.getQuantity() != null) fetchedItem.setQuantity(item.getQuantity());

            repository.save(fetchedItem);
            return fetchedItem;
        }else {
            throw new RuntimeException("Item Not Found with id "+item.getItemId());
        }
    }

    //DELETE ITEM
    public StationaryItem deleteOneItem(Integer itemId){
        Optional<StationaryItem> result = repository.findById(itemId);
        if(result.isPresent()){
            StationaryItem fetchedItem = result.get();
            repository.deleteById(itemId);
            return fetchedItem;
        }else
            throw new RuntimeException("Item Not Found with id "+itemId);
    }
    
    public void deleteAllItems(){
        // TODO: 7/12/2023
    }
}
