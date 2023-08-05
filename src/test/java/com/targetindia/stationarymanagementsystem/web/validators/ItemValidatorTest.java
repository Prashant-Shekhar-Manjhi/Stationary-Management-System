package com.targetindia.stationarymanagementsystem.web.validators;

import com.targetindia.stationarymanagementsystem.dto.StationaryItemDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ItemValidatorTest {

    private ItemValidator itemValidator = new ItemValidator();
    @Test
    public void isItemValidValidStationaryItemDTOTest() {
        StationaryItemDTO stationaryItemDTO = new StationaryItemDTO();
        stationaryItemDTO.setItemName("Pen");
        stationaryItemDTO.setQuantity(10);
        stationaryItemDTO.setReturnable(true);
        stationaryItemDTO.setMaxDays(7);

        boolean result = itemValidator.isItemValid(stationaryItemDTO);
        Assertions.assertTrue(result);
    }

    @Test
    public void isItemValidInvalidStationaryItemDTOTest() {
        StationaryItemDTO stationaryItemDTO = new StationaryItemDTO();
        stationaryItemDTO.setItemName(null);
        stationaryItemDTO.setQuantity(5);
        stationaryItemDTO.setReturnable(null);
        stationaryItemDTO.setMaxDays(null);

        boolean result = itemValidator.isItemValid(stationaryItemDTO);
        Assertions.assertFalse(result);
    }

    @Test
    public void isItemValidNonReturnableItemTest() {
        StationaryItemDTO stationaryItemDTO = new StationaryItemDTO();
        stationaryItemDTO.setItemName("Notebook");
        stationaryItemDTO.setQuantity(20);
        stationaryItemDTO.setReturnable(false);
        stationaryItemDTO.setMaxDays(null);

        boolean result = itemValidator.isItemValid(stationaryItemDTO);
        Assertions.assertTrue(result);
    }
}
