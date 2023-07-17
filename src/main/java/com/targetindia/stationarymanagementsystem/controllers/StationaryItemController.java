package com.targetindia.stationarymanagementsystem.controllers;

import com.targetindia.stationarymanagementsystem.entities.StationaryItem;
import com.targetindia.stationarymanagementsystem.model.Message;
import com.targetindia.stationarymanagementsystem.services.StationaryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "/api/stationaryItem")
public class StationaryItemController {

    @Autowired
    private StationaryItemService service;

    @PostMapping(path = "/save",consumes = "application/json",produces = "application/json")
    public ResponseEntity handleAddItem(@RequestBody StationaryItem item){
        try{
            StationaryItem stationaryItem = service.addItem(item);
            if(stationaryItem == null){
                return ResponseEntity.status(500).body(new Message("Stationary Item should not be NULL or Empty!"));
            }
            return ResponseEntity.ok(stationaryItem);
        }catch (Exception e){
            return ResponseEntity.status(500).body(new Message("Item Not Saved"));
        }
    }


    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity handleGetOneItem(@PathVariable Integer id){
        try {
            StationaryItem item = service.getOneItem(id);
            if(item == null) ResponseEntity.status(404).body(new Message("Item Not Found!"));
            return ResponseEntity.ok(item);
        }catch (Exception e){
            return ResponseEntity.status(404).body(new Message("Item Not Found!"));
        }
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity handleGetAllItems(){
        try{
            List<StationaryItem> fetchedItems = service.getAllItem();
            return ResponseEntity.ok(fetchedItems);
        }catch (Exception e){
            return ResponseEntity.status(404).body(new Message("Items Not Found!"));
        }
    }

    @PatchMapping(path = "/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity handleUpdateItem(@PathVariable Integer id, @RequestBody StationaryItem stationaryItem){
        try{
            stationaryItem.setItemId(id);
            StationaryItem item = service.updateItem(stationaryItem);
            return ResponseEntity.ok(item);
        }catch (Exception e){
            return ResponseEntity.status(404).body(new Message("Item Not Found with id "+id));
        }
    }

    @DeleteMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity handleDeleteOneItem(@PathVariable Integer id){
        try {
            StationaryItem fetchedItem = service.deleteOneItem(id);
            return ResponseEntity.ok(fetchedItem);
        }catch (Exception e){
            return ResponseEntity.status(404).body(new Message("Item Not Found with id "+id));
        }
    }
}
