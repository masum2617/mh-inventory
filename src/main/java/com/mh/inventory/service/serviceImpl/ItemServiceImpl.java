package com.mh.inventory.service.serviceImpl;

import com.mh.inventory.common.commonresponse.Response;
import com.mh.inventory.common.commonresponse.ResponseUtils;
import com.mh.inventory.dtos.ItemDto;
import com.mh.inventory.entity.Category;
import com.mh.inventory.entity.Item;
import com.mh.inventory.exception.ResourceNotFoundException;
import com.mh.inventory.mapper.item.ItemMapper;
import com.mh.inventory.procedures.ItemProcedureImpl;
import com.mh.inventory.repository.CategoryRepo;
import com.mh.inventory.repository.ItemRepo;
import com.mh.inventory.repository.itemRepository.ItemSpecification;
import com.mh.inventory.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemMapper itemMapper;
    private final CategoryRepo categoryRepo;
    private final ItemRepo itemRepo;
    private final ItemProcedureImpl itemProcedureImpl;


    @Transactional
    @Override
    public Response saveItem(ItemDto item) {

        Item itemEntity = itemMapper.toEntity(item);

        Category category = categoryRepo.findById(item.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        itemEntity.setCategory(category);

        Item savedItem = itemRepo.save(itemEntity);

        if (item != null) {
            ItemDto savedItemDto = itemMapper.toItemDto(savedItem);
            return ResponseUtils.createSuccessResponse("Saved Successfully", savedItemDto);
        }
        return ResponseUtils.createFailedResponse("Saved Failed");

    }

    @Transactional
    @Override
    public Response saveItemWithProcedure(ItemDto dto) {
        return itemProcedureImpl.createItemWithProcedure(dto.getItemCode(),
                dto.getItemName(),
                dto.getCategoryId(),
                dto.getQty(),
                dto.getReOrderLevel());
    }

    @Transactional
    @Override
    public Response saveItemAndStockWithProcedure(ItemDto dto) {
        return itemProcedureImpl.createItemWithStockWithFunction(

                dto.getItemCode(),
                dto.getItemName(),
                dto.getCategoryId(),
                dto.getQty(),
                dto.getReOrderLevel());
    }

    @Override
    public Response getAllItems() {
        List<Item> items = itemRepo.findAll();
        List<ItemDto> itemList = itemMapper.toItemDtos(items);

        if (itemList.isEmpty()) {
            return ResponseUtils.createFailedResponse("Items Not Found");
        }
        return ResponseUtils.createSuccessResponse("Items Found", itemList);

    }

    @Transactional(readOnly = true)
    @Override
    public Response searchItemWithProc(String searchText) {
        return itemProcedureImpl.searchItemWithProcedure(searchText);
    }

    @Transactional(readOnly = true)
    @Override
    public Response searchItemWithFunc(String searchText) {
        return itemProcedureImpl.searchItemWithFunc(searchText);
    }

    @Transactional(readOnly = true)
    @Override
    public Response getFilteredItem(String keyword, List<Long> categoryIds, Integer status) {


        Specification<Item> filteredSpecification = ItemSpecification.filter(
                keyword,
                categoryIds,
                status
        );

        List<Item> itemList = itemRepo.findAll(filteredSpecification);
        List<ItemDto> list = itemRepo.findAll(filteredSpecification)
                .stream()
                .map(item -> new ItemDto(
                        item.getId(),
                        item.getItemCode(),
                        item.getItemName(),
//                        item.getCategory().getId(),
                        item.getQty()
                ))
                .toList();
        return ResponseUtils.createSuccessResponse("Items Found", list);
    }



}
