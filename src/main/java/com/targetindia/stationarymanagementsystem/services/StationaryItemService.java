package com.targetindia.stationarymanagementsystem.services;

import com.targetindia.stationarymanagementsystem.entities.StationaryItem;
import com.targetindia.stationarymanagementsystem.exception.DaoException;
import com.targetindia.stationarymanagementsystem.exception.ItemNotFoundException;
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
    public StationaryItem addItem  (StationaryItem item) throws DaoException {
        try{
            repository.save(item);
            return item;
        }catch (Exception e){
            throw new DaoException(e.getMessage());
        }
    }
    
    //READ ITEM/ITEMS
    public StationaryItem getOneItem(Integer itemId) throws DaoException, ItemNotFoundException {
        try{
            Optional<StationaryItem> fetchedItem = repository.findById(itemId);
            if(fetchedItem.isPresent()) return  fetchedItem.get();
            else return null;
        }catch (Exception e){
            throw new DaoException(e.getMessage());
        }
    }
    
    public List<StationaryItem> getAllItem() throws DaoException, ItemNotFoundException {
        try{
            List<StationaryItem> itemList = repository.findAll();
            if(itemList.isEmpty()) throw new ItemNotFoundException("Items not found.");
            return itemList;
        }catch (ItemNotFoundException e){
            throw e;
        }
        catch (Exception e){
            throw new DaoException(e.getMessage());
        }
    }
    
    //UPDATE ITEM
    public StationaryItem updateItem(StationaryItem item) throws DaoException, ItemNotFoundException {
        try{
            Optional<StationaryItem> result = repository.findById(item.getItemId());
            if(result.isPresent()){
                StationaryItem fetchedItem = result.get();

                if(item.getItemName() != null) fetchedItem.setItemName(item.getItemName());

                if(item.getReturnable() != null){
                    if(item.getReturnable() == true) fetchedItem.setReturnable(true);
                    else{
                        fetchedItem.setReturnable(false);
                        fetchedItem.setMaxDays(null);
                    }
                }
                else fetchedItem.setReturnable(fetchedItem.getReturnable());

                if(item.getQuantity() != null) fetchedItem.setQuantity(item.getQuantity());
                if(item.getMaxDays() != null) fetchedItem.setMaxDays(item.getMaxDays());

                repository.save(fetchedItem);
                return fetchedItem;
            }else {
                throw new ItemNotFoundException("Item Not Found with id "+item.getItemId());
            }
        }catch (ItemNotFoundException e){
            throw e;
        }catch (Exception e){
            throw new DaoException(e.getMessage());
        }
    }

    //DELETE ITEM
    public StationaryItem deleteOneItem(Integer itemId) throws DaoException,ItemNotFoundException {
        try{
            Optional<StationaryItem> result = repository.findById(itemId);
            if(result.isPresent()){
                StationaryItem fetchedItem = result.get();
                repository.deleteById(itemId);
                return fetchedItem;
            }else
                throw new ItemNotFoundException("Item Not Found with id "+itemId);
        }catch (ItemNotFoundException e){
            throw e;
        }
        catch (Exception e){
            throw new DaoException(e.getMessage());
        }

    }

}
