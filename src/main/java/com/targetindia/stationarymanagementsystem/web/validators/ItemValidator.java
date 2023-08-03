package com.targetindia.stationarymanagementsystem.web.validators;

import com.targetindia.stationarymanagementsystem.dto.StationaryItemDTO;
import org.springframework.stereotype.Component;

@Component
public class ItemValidator {
    public Boolean isItemValid(StationaryItemDTO stationaryItemDTO){
        if(stationaryItemDTO.getItemName() == null || stationaryItemDTO.getItemName().isBlank()) return false;
        if(stationaryItemDTO.getQuantity() == null) return false;
        if(stationaryItemDTO.getReturnable() == null) return false;
        if(stationaryItemDTO.getReturnable() == true && stationaryItemDTO.getMaxDays() == null) return false;
        return true;
    }
}
