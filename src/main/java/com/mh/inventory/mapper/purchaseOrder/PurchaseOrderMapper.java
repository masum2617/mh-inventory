package com.mh.inventory.mapper.purchaseOrder;

import com.mh.inventory.dtos.PurOrderRequestDto;
import com.mh.inventory.entity.PurchaseOrder;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PurchaseOrderMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "purchaseOrderItems", ignore = true)
    @Mapping(target = "poCode", ignore = true)
    void updateEntityFromDto(
            PurOrderRequestDto dto,
            @MappingTarget PurchaseOrder entity
    );
}
