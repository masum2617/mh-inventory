package com.mh.inventory.mapper.stock;

import com.mh.inventory.dtos.StockRequestDto;
import com.mh.inventory.dtos.StockResponseDto;
import com.mh.inventory.entity.InventoryTransaction;
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



//    @Mapping(target = "activeStatus", defaultValue = "1")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "item", ignore = true)
    @Mapping(source = "warehouseId", target = "warehouse.id", ignore = true)
    @Mapping(source = "supplierId", target = "supplier.id", ignore = true)
    @Mapping(target = "purchasePrice", expression = "java(dto.getPurchasePrice() != null ? java.math.BigDecimal.valueOf(dto.getPurchasePrice()) : null)")
    @Mapping(target = "salesPrice", expression = "java(dto.getSalesPrice() != null ? java.math.BigDecimal.valueOf(dto.getSalesPrice()) : null)")
    InventoryTransaction toEntity(StockRequestDto dto);
}
