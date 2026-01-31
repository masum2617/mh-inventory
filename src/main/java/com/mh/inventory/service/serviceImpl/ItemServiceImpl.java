package com.mh.inventory.service.serviceImpl;

import com.mh.inventory.common.commonresponse.Response;
import com.mh.inventory.common.commonresponse.ResponseUtils;
import com.mh.inventory.dtos.ItemDto;
import com.mh.inventory.entity.Category;
import com.mh.inventory.entity.Item;
import com.mh.inventory.exception.ResourceNotFoundException;
import com.mh.inventory.mapper.item.ItemMapper;
import com.mh.inventory.repository.CategoryRepo;
import com.mh.inventory.repository.ItemRepo;
import com.mh.inventory.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemMapper itemMapper;
    private final CategoryRepo categoryRepo;
    private final ItemRepo itemRepo;

    @Transactional
    @Override
    public Response saveItem(ItemDto item) {

        Item itemEntity = itemMapper.toEntity(item);

        Category category = categoryRepo.findById(item.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        itemEntity.setCategory(category);

        Item savedItem = itemRepo.save(itemEntity);

        if(item != null) {
            ItemDto savedItemDto = itemMapper.toItemDto(savedItem);
            return ResponseUtils.createSuccessResponse("Saved Successfully", savedItemDto );
        }
        return ResponseUtils.createFailedResponse("Saved Failed");

    }

    @Override
    public Response getAllItems() {
        List<Item> items = itemRepo.findAll();
        List<ItemDto> itemList = itemMapper.toItemDtos(items);

        if(itemList.isEmpty()) {
            return ResponseUtils.createFailedResponse("Items Not Found");
        }
        return ResponseUtils.createSuccessResponse("Items Found", itemList);

    }
}
