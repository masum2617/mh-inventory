package com.mh.inventory.mapper.purchaseOrder;

import com.mh.inventory.dtos.PurchaseOrderRequestDto;
import com.mh.inventory.entity.PurchaseOrder;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PurchaseOrderMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "poCode", ignore = true)
    void updateEntityFromDto(
            PurchaseOrderRequestDto dto,
            @MappingTarget PurchaseOrder entity
    );
}
