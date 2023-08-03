package com.targetindia.stationarymanagementsystem.services;

import com.targetindia.stationarymanagementsystem.entities.StationaryItem;
import com.targetindia.stationarymanagementsystem.exception.DaoException;
import com.targetindia.stationarymanagementsystem.exception.ItemNotFoundException;
import com.targetindia.stationarymanagementsystem.repository.StationaryItemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StationaryItemServiceTest {

    @Mock
    private StationaryItemRepository stationaryItemRepository;
    private StationaryItemService stationaryItemService;
    private StationaryItem stationaryItem;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        stationaryItemService = new StationaryItemService(stationaryItemRepository);
        stationaryItem = new StationaryItem();
        stationaryItem.setItemId(1);
        stationaryItem.setItemName("Pen");
        stationaryItem.setQuantity(40);
        stationaryItem.setReturnable(true);
        stationaryItem.setMaxDays(5);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void testAddItem() throws DaoException {
        mock(StationaryItem.class);
        mock(StationaryItemRepository.class);

        when(stationaryItemRepository.save(stationaryItem)).thenReturn(stationaryItem);
        assertThat(stationaryItemService.addItem(stationaryItem).getItemName()).isEqualTo(stationaryItem.getItemName());
    }

    @Test
    void testAddItemException() {
        mock(StationaryItem.class);
        mock(StationaryItemRepository.class);

        doThrow(new RuntimeException("Something went wrong!"))
                .when(stationaryItemRepository)
                .save(any());

        assertThatThrownBy(()->stationaryItemService.addItem(stationaryItem))
                .isInstanceOf(DaoException.class)
                .hasMessage("Something went wrong!");
    }

    @Test
    void testGetOneItem() throws DaoException, ItemNotFoundException {
        mock(StationaryItem.class);
        mock(StationaryItemRepository.class);
        when(stationaryItemRepository.findById(1)).thenReturn(Optional.ofNullable(stationaryItem));
        assertThat(stationaryItemService.getOneItem(1).getItemName()).isEqualTo(stationaryItem.getItemName());
    }

    @Test
    void testGetOneItemFailure() throws DaoException, ItemNotFoundException {
        mock(StationaryItem.class);
        mock(StationaryItemRepository.class);
        when(stationaryItemRepository.findById(1)).thenReturn(Optional.ofNullable(null));
        assertThat(stationaryItemService.getOneItem(1)).isNull();
    }

    @Test
    void testGetOneItemException(){
        mock(StationaryItem.class);
        mock(StationaryItemRepository.class);

        doThrow(new RuntimeException("Something went wrong!"))
                .when(stationaryItemRepository)
                .findById(any());

        assertThatThrownBy(()->stationaryItemService.getOneItem(1))
                .isInstanceOf(DaoException.class)
                .hasMessage("Something went wrong!");
    }

    @Test
    void testGetAllItem() throws DaoException, ItemNotFoundException {
        mock(StationaryItemRepository.class);
        mock(StationaryItem.class);

        when(stationaryItemRepository.findAll()).thenReturn(
                new ArrayList<StationaryItem>(Collections.singleton(stationaryItem))
        );
        assertThat(stationaryItemService.getAllItem().get(0).getItemId()).isEqualTo(stationaryItem.getItemId());
    }

    @Test
    void testGetAllItemNotFoundException() throws DaoException, ItemNotFoundException {
        mock(StationaryItem.class);
        mock(StationaryItemRepository.class);

        when(stationaryItemRepository.findAll()).thenReturn(new ArrayList<>());
        assertThatThrownBy(()->stationaryItemService.getAllItem()).isInstanceOf(ItemNotFoundException.class)
                .hasMessage("Items not found.");
    }


    @Test
    void testGetAllItemDaoException() throws DaoException{
        mock(StationaryItem.class);
        mock(StationaryItemRepository.class);

        doThrow(new RuntimeException("Something went wrong"))
                .when(stationaryItemRepository)
                .findAll();

        assertThatThrownBy(()->stationaryItemService.getAllItem())
                .isInstanceOf(DaoException.class)
                .hasMessage("Something went wrong");
    }

    @Test
    void testUpdateItem() throws DaoException, ItemNotFoundException {
        mock(StationaryItem.class);
        mock(StationaryItemRepository.class);

        when(stationaryItemRepository.findById(1)).thenReturn(Optional.ofNullable(stationaryItem));
        when(stationaryItemRepository.save(stationaryItem)).thenReturn(stationaryItem);
        assertThat(stationaryItemService.updateItem(stationaryItem).getItemName()).isEqualTo(stationaryItem.getItemName());
    }

    @Test
    void testUpdateItemNotFoundException(){
        mock(StationaryItem.class);
        mock(StationaryItemRepository.class);

        when(stationaryItemRepository.findById(1)).thenReturn(Optional.ofNullable(null));
        assertThatThrownBy(()->stationaryItemService.updateItem(stationaryItem))
                .isInstanceOf(ItemNotFoundException.class)
                .hasMessage("Item Not Found with id 1");
    }

    @Test
    void testUpdateItemDaoException(){
        mock(StationaryItem.class);
        mock(StationaryItemRepository.class);

        when(stationaryItemRepository.findById(1)).thenReturn(Optional.ofNullable(stationaryItem));
        doThrow(new RuntimeException("Something went wrong"))
                .when(stationaryItemRepository)
                .save(stationaryItem);

        assertThatThrownBy(()->stationaryItemService.updateItem(stationaryItem))
                .isInstanceOf(DaoException.class)
                .hasMessage("Something went wrong");
    }

    @Test
    void testDeleteOneItem() throws DaoException, ItemNotFoundException {
        mock(StationaryItem.class);
        mock(StationaryItemRepository.class, Mockito.CALLS_REAL_METHODS);

        when(stationaryItemRepository.findById(1)).thenReturn(Optional.ofNullable(stationaryItem));
        doAnswer(Answers.CALLS_REAL_METHODS).when(stationaryItemRepository).deleteById(1);

        assertThat(stationaryItemService.deleteOneItem(1).getItemName()).isEqualTo(stationaryItem.getItemName());
    }
    @Test
    void testDeleteOneItemNotFoundException()
    {
        mock(StationaryItem.class);
        mock(StationaryItemRepository.class, Mockito.CALLS_REAL_METHODS);

        when(stationaryItemRepository.findById(1)).thenReturn(Optional.ofNullable(null));
        assertThatThrownBy(()->stationaryItemService.deleteOneItem(1))
                .isInstanceOf(ItemNotFoundException.class)
                .hasMessage("Item Not Found with id 1");
    }

    @Test
    void testDeleteOneDaoException()
    {
        mock(StationaryItem.class);
        mock(StationaryItemRepository.class, Mockito.CALLS_REAL_METHODS);

        when(stationaryItemRepository.findById(1)).thenReturn(Optional.ofNullable(stationaryItem));
        doThrow(new RuntimeException("Something went wrong"))
                .when(stationaryItemRepository)
                .deleteById(any());

        assertThatThrownBy(()->stationaryItemService.deleteOneItem(1))
                .isInstanceOf(DaoException.class)
                .hasMessage("Something went wrong");
    }
}