package com.mh.inventory.mapper.stock;

import com.mh.inventory.dtos.StockResponseDto;
import com.mh.inventory.entity.Stock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StockMapper {

//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    @Mapping(target = "id", ignore = true)
//    StockResponseDto toStockResponseDto(Stock stock);


    @Mapping(source = "id", target = "stockId")
    @Mapping(source = "item.id", target = "itemId")
    @Mapping(source = "item.itemName", target = "itemName")
    @Mapping(source = "item.reOrderLevel", target = "reOrderLevel")
    StockResponseDto toResponseDto(Stock stock);
}
