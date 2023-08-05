package com.targetindia.stationarymanagementsystem.web.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.targetindia.stationarymanagementsystem.dto.StationaryItemDTO;
import com.targetindia.stationarymanagementsystem.entities.StationaryItem;
import com.targetindia.stationarymanagementsystem.entities.Transaction;
import com.targetindia.stationarymanagementsystem.exception.DaoException;
import com.targetindia.stationarymanagementsystem.exception.ItemNotFoundException;
import com.targetindia.stationarymanagementsystem.services.StationaryItemService;
import com.targetindia.stationarymanagementsystem.web.validators.ItemValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class StationaryItemControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StationaryItemService stationaryItemService;

    @Mock
    private ItemValidator itemValidator;

    @InjectMocks
    private StationaryItemController stationaryItemController;

    private ObjectMapper objectMapper;

    @BeforeEach
    private void setUp(){
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(stationaryItemController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void addItemValidTest() throws Exception{
        StationaryItem stationaryItem = new StationaryItem(1,"pen",3,true,5,List.of(new Transaction()));
        when(stationaryItemService.addItem(stationaryItem)).thenReturn(stationaryItem);
        String requestJson = objectMapper.writeValueAsString(stationaryItem);
        when(itemValidator.isItemValid(any(StationaryItemDTO.class))).thenReturn(true);
        mockMvc.perform(post("/inventory/v1/stationary_item/add_item")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)).
                andExpect(status().isCreated());
    }
    @Test
    public void addItemInvalidTest() throws Exception {
        StationaryItem stationaryItem = new StationaryItem();

        String requestJson = objectMapper.writeValueAsString(stationaryItem);
        when(itemValidator.isItemValid(any(StationaryItemDTO.class))).thenReturn(false);

        mockMvc.perform(post("/inventory/v1/stationary_item/add_item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void addItemErrorTest() throws Exception {
        StationaryItem stationaryItem = new StationaryItem(2,"Eraiser", 10, true, 30, List.of(new Transaction()));
        when(stationaryItemService.addItem(any(StationaryItem.class))).thenThrow(new RuntimeException("item is not saved"));
        String requestJson = objectMapper.writeValueAsString(stationaryItem);
        when(itemValidator.isItemValid(any(StationaryItemDTO.class))).thenReturn(true);

        mockMvc.perform(post("/inventory/v1/stationary_item/add_item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isInternalServerError());
    }
    @Test
    public void getOneItemTest() throws Exception{
        Integer id = 1;
        StationaryItem item = new StationaryItem();
        when(stationaryItemService.getOneItem(id)).thenReturn(item);
        String requestjson = objectMapper.writeValueAsString(stationaryItemController);

        mockMvc.perform(get("/inventory/v1/stationary_item/{id}",id)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void getOneItemNotFoundTest() throws Exception {
        Integer id = 100;
        when(stationaryItemService.getOneItem(id)).thenThrow(new ItemNotFoundException("Item not found with id " + id));

        mockMvc.perform(get("/inventory/v1/stationary_item/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    public void getOneInternalErrorTest() throws Exception {
        Integer id = 100;
        when(stationaryItemService.getOneItem(id)).thenThrow(new DaoException("Item not found with id " + id));

        mockMvc.perform(get("/inventory/v1/stationary_item/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void getAllItemsTest() throws Exception {
        when(stationaryItemService.getAllItem()).thenReturn(List.of(new StationaryItem()));

        mockMvc.perform(get("/inventory/v1/stationary_item")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllItemsNoContentTest() throws Exception {
        when(stationaryItemService.getAllItem()).thenThrow(new ItemNotFoundException("No Content"));

        mockMvc.perform(get("/inventory/v1/stationary_item")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
    @Test
    public void getAllItemsErrorTest() throws Exception {
        when(stationaryItemService.getAllItem()).thenThrow(new RuntimeException("Internal server error"));

        mockMvc.perform(get("/inventory/v1/stationary_item")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
    @Test
    public void updateItemTest() throws Exception {
        Integer id = 1;
        StationaryItem updatedItem = new StationaryItem();
        updatedItem.setItemId(id);

        when(stationaryItemService.updateItem(any(StationaryItem.class))).thenReturn(updatedItem);

        mockMvc.perform(patch("/inventory/v1/stationary_item/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedItem)))
                .andExpect(status().isOk());
    }
    @Test
    public void updateItemNotFoundTest() throws Exception {
        Integer id = 100;
        StationaryItem updatedItem = new StationaryItem();
        updatedItem.setItemId(id);

        when(stationaryItemService.updateItem(any(StationaryItem.class)))
                .thenThrow(new ItemNotFoundException("Item not found with id " + id));

        mockMvc.perform(patch("/inventory/v1/stationary_item/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedItem)))
                .andExpect(status().isNotFound());
    }
    @Test
    public void updateItemInternalServerErrorTest() throws Exception {
        Integer id = 100;
        StationaryItem updatedItem = new StationaryItem();
        updatedItem.setItemId(id);

        when(stationaryItemService.updateItem(any(StationaryItem.class)))
                .thenThrow(new DaoException("Internal Error"));

        mockMvc.perform(patch("/inventory/v1/stationary_item/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedItem)))
                .andExpect(status().isInternalServerError());
    }
    @Test
    public void deleteOneItemTest() throws Exception {
        Integer id = 1;
        when(stationaryItemService.deleteOneItem(id)).thenReturn(new StationaryItem());

        mockMvc.perform(delete("/inventory/v1/stationary_item/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void deleteOneItemNotFoundTest() throws Exception {
        Integer id = 1;
        when(stationaryItemService.deleteOneItem(id)).thenThrow(new ItemNotFoundException("Item not found with id " + id));

        mockMvc.perform(delete("/inventory/v1/stationary_item/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    public void deleteOneItemInternalServerErrorTest() throws Exception {
        Integer id = 1;
        when(stationaryItemService.deleteOneItem(id)).thenThrow(new DaoException("Internal Server"));


        mockMvc.perform(delete("/inventory/v1/stationary_item/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }


    }
