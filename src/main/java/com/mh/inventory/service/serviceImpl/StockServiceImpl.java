package com.mh.inventory.service.serviceImpl;

import com.mh.inventory.common.commonresponse.Response;
import com.mh.inventory.common.commonresponse.ResponseStatus;
import com.mh.inventory.common.commonresponse.ResponseUtils;
import com.mh.inventory.dtos.StockLowEvent;
import com.mh.inventory.dtos.StockRequestDto;
import com.mh.inventory.dtos.StockResponseDto;
import com.mh.inventory.entity.Stock;
import com.mh.inventory.mapper.stock.StockMapper;
import com.mh.inventory.repository.StockRepo;
import com.mh.inventory.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockRepo stockRepo;
    private final ApplicationEventPublisher eventPublisher;
    private final StockMapper stockMapper;

    @Override
    public StockResponseDto saveStock(StockRequestDto stockRequestDto) {
        return null;
    }

    @Transactional
    @Override
    public Response reduceStock(StockRequestDto stockRequestDto) {
        Long itemNo = stockRequestDto.getItemId();
        Integer sellQty = stockRequestDto.getSellQty() != null ? stockRequestDto.getSellQty() : 1;

//        Stock stock = stockRepo.findByItemId(itemNo);
        StockResponseDto stock = stockRepo.findStockWithItem(itemNo);
        Stock currStockObj = stockRepo.findById(stock.getStockId()).orElse(null);


        Response res = Response.builder()
                .status(ResponseStatus.success("Stock Updated Successfully"))
                .data(stock)
                .build();


        Integer currQty = stock.getQtyAvailable();

        int newQty = currQty - sellQty;
        currStockObj.setQtyAvailable(newQty);

        Stock savedStock = stockRepo.save(currStockObj);

        // threshold crossing check
        if (newQty <= stock.getReOrderLevel()) {
            eventPublisher.publishEvent(
                    new StockLowEvent(itemNo, newQty, null, stock.getItemName())
            );
        }


//
        if (savedStock != null) {
            return ResponseUtils.createSuccessResponse("Stock Updated Successfully", stockMapper.toResponseDto(savedStock));
        }


        return ResponseUtils.createFailedResponse("Stock Updated Failed");
    }
}
