package com.mh.inventory.controllers;

import com.mh.inventory.common.commonresponse.Response;
import com.mh.inventory.dtos.ItemDto;
import com.mh.inventory.repository.itemRepository.ItemRepoCriteria;
import com.mh.inventory.service.ItemService;
import com.mh.inventory.service.ItemServiceQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item")
public class ItemController {

    @Autowired
    ItemService itemService;

    @Autowired
    ItemServiceQ itemServiceQ;

    @Autowired
    ItemRepoCriteria itemRepoCriteria;

    @Value("${build.version}")
    private String buildVersion;

    @Value("${inventory.message}")
    private String message;





    @PostMapping("/save")
    public Response saveItem(@RequestBody ItemDto itemDto) {
//        return itemService.saveItem(itemDto);
//        return itemService.saveItemWithProcedure(itemDto);
        return itemService.saveItemAndStockWithProcedure(itemDto);
    }

    @GetMapping("/list")
    public Response listItems() {
        return itemService.getAllItems();
    }

    @GetMapping("/search-item-proc")
    public Response searchItemWithProc(@RequestParam String searchText) {
//        return itemService.searchItemWithProc(searchText);
        return itemService.searchItemWithFunc(searchText);
    }


    @GetMapping("/find-item")
    public Response findItemById(@RequestParam Long id) {
        return itemServiceQ.getItemById(id);

    }

    @GetMapping("/find-item-with-stock")
    public Response itemWithStockList(@RequestParam Long id) {
//        return itemServiceQ.itemListWithStock(id);
        return itemRepoCriteria.getItemListWithStock(id);
    }


    @GetMapping("/get-filtered-item")
    public Response getFilteredItem(@RequestParam String keyword, @RequestParam List<Long> categoryIds,
                                    @RequestParam Integer status) {
        return itemService.getFilteredItem(keyword, categoryIds, status);

    }

    //paginated data
    @GetMapping("/data-table")
    public Response getItems(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) List<Long> categoryIds,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer perPage,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {

        return itemServiceQ.itemListPaginated(
                keyword,
                categoryIds,
                page,
                perPage,
                sortBy,
                direction,
                startDate,
                endDate
        );

    }


    @GetMapping("/build-info-message")
    public ResponseEntity<String> getBuildInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(message.concat(buildVersion));
    }

}
