package com.targetindia.stationarymanagementsystem.web.controllers;

import com.targetindia.stationarymanagementsystem.dto.StationaryItemDTO;
import com.targetindia.stationarymanagementsystem.entities.StationaryItem;
import com.targetindia.stationarymanagementsystem.exception.DaoException;
import com.targetindia.stationarymanagementsystem.exception.ItemNotFoundException;
import com.targetindia.stationarymanagementsystem.model.Message;
import com.targetindia.stationarymanagementsystem.services.StationaryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.targetindia.stationarymanagementsystem.web.validators.ItemValidator.isItemValid;

@RestController
@CrossOrigin
@RequestMapping(path = "/inventory/v1/stationary_item")
public class StationaryItemController {

    @Autowired
    private StationaryItemService service;

    @PostMapping(path = "/add_item", consumes = "application/json",produces = "application/json")
    public ResponseEntity handleAddItem(@RequestBody StationaryItemDTO itemDTO){
        if(!isItemValid(itemDTO)){
            return ResponseEntity.status(400).body(new Message("Invalid Input"));
        }
        try{
            StationaryItem stationaryItem = new StationaryItem();
            stationaryItem.setItemName(itemDTO.getItemName());
            stationaryItem.setQuantity(itemDTO.getQuantity());
            stationaryItem.setReturnable(itemDTO.getReturnable());
            stationaryItem.setMaxDays(itemDTO.getMaxDays());

            service.addItem(stationaryItem);
            return ResponseEntity.status(201).body(new Message("Item saved successfully."));
        }catch (Exception e){
            return ResponseEntity.status(500).body(new Message("Item not saved."));
        }
    }


    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity handleGetOneItem(@PathVariable Integer id){
        try {
            StationaryItem item = service.getOneItem(id);
            if(item == null) throw new ItemNotFoundException("Item not found with id "+id);
            return ResponseEntity.ok(item);
        }catch (ItemNotFoundException e){
            return ResponseEntity.status(404).body(new Message(e.getMessage()));
        }
        catch (DaoException e){
            return ResponseEntity.status(500).body(new Message(e.getMessage()));
        }
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity handleGetAllItems(){
        try{
            List<StationaryItem> fetchedItems = service.getAllItem();
            return ResponseEntity.ok(fetchedItems);
        }catch (ItemNotFoundException e){
            return ResponseEntity.status(204).body(new Message("NO content"));
        }
        catch (Exception e){
            return ResponseEntity.status(500).body(new Message(e.getMessage()));
        }
    }

    @PatchMapping(path = "/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity handleUpdateItem(@PathVariable Integer id, @RequestBody StationaryItem stationaryItem){
        try{
            stationaryItem.setItemId(id);
            StationaryItem item = service.updateItem(stationaryItem);
            return ResponseEntity.ok(item);
        }catch (ItemNotFoundException e){
            return ResponseEntity.status(404).body(new Message("Item Not Found with id "+id));
        }catch (DaoException e){
            return ResponseEntity.status(500).body(new Message(e.getMessage()));
        }
    }

    @DeleteMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity handleDeleteOneItem(@PathVariable Integer id){
        try {
            StationaryItem fetchedItem = service.deleteOneItem(id);
            return ResponseEntity.ok(fetchedItem);
        }catch (ItemNotFoundException e){
            return ResponseEntity.status(404).body(new Message("Item Not Found with id "+id));
        }
        catch (Exception e){
            return ResponseEntity.status(500).body(new Message(e.getMessage()));
        }
    }
}
