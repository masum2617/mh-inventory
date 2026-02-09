package com.mh.inventory.repository;

import com.mh.inventory.common.commonresponse.Response;
import com.mh.inventory.common.commonresponse.ResponseUtils;
import com.mh.inventory.config.jpaConfig.BaseRepo;
import com.mh.inventory.dtos.ItemDto;
import com.mh.inventory.dtos.QItemDto;
import com.mh.inventory.entity.Item;
import com.mh.inventory.entity.QCategory;
import com.mh.inventory.entity.QItem;
import com.mh.inventory.entity.QStock;
import com.mh.inventory.service.ItemServiceQ;
import com.mh.inventory.utils.DateUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;

import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;


import java.time.LocalDate;
import java.util.List;

import static com.mh.inventory.entity.QItem.item;
/*query dsl */

@Repository
public class ItemRepoQImpl extends BaseRepo implements ItemServiceQ {

    private final QItem qItem = item;
    private final QCategory qCategory = QCategory.category;
    private final QStock qStock = QStock.stock;

    protected ItemRepoQImpl(JPAQueryFactory query) {
        super(query);
    }

    @Override
    public Response getItemById(Long id) {
        /*
        * Constructor projection (recommended)
        ✅ Compile-time safety
        ✅ Order matters → less silent bugs
        ✅ Immutable DTOs possible
        ✅ Refactor-safe
        ✅ Cleaner mapping
        * */
        ItemDto itemDto = query
                .select(Projections.constructor(ItemDto.class,
                        qItem.id, qItem.itemCode, qItem.itemName, qItem.category.id,
                        qItem.qty, qItem.reOrderLevel, qItem.category.categoryName))
                .from(qItem)
//                .join(qCategory)
//                .on(qCategory.id.eq(qItem.category.id))
                .where(qItem.id.eq(id))
                .fetchOne();

        if (itemDto == null) {
            return ResponseUtils.createFailedResponse("Item not found with id " + id);
        }
        return ResponseUtils.createSuccessResponse("Item Found", itemDto);
    }

    @Override
    public Response itemListWithStock(Long itemId) {

        // New QItemDto -> this is happened because in itemDto i have QueryProjection annotation
        List<ItemDto> result = query.select(new QItemDto(
                        qItem.id,
                        qItem.itemCode,
                        qItem.itemName,
                        qItem.qty,
                        qItem.reOrderLevel,
                        qItem.category.id,
                        qItem.category.categoryName,
                        qStock.qtyAvailable
                ))
                .from(qItem)
                .leftJoin(qItem.stockList, qStock)
                .fetch();

        return ResponseUtils.createSuccessResponse("Item Found", result);

    }

    //paginated data table data
    public Page<ItemDto> getPaginatedList(
            String keyword,
            List<Long> categoryIds,
            Pageable pageable
    ) {

        BooleanBuilder where = new BooleanBuilder();

        if (keyword != null && !keyword.isBlank()) {
            where.and(item.itemName.containsIgnoreCase(keyword)
                    .or(item.itemCode.containsIgnoreCase(keyword)));
        }

        if (categoryIds != null && !categoryIds.isEmpty()) {
            where.and(item.category.id.in(categoryIds));
        }

        // MAIN QUERY
        List<ItemDto> content = query
                .select(new QItemDto(
                        qItem.id,
                        qItem.itemCode,
                        qItem.itemName,
                        qItem.qty,
                        qItem.reOrderLevel,
                        qItem.category.id,
                        qItem.category.categoryName,
                        qStock.qtyAvailable
                ))
                .from(item)
                .leftJoin(qItem.stockList, qStock)
                .where(where)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // COUNT QUERY (important for pagination)
        Long total = query
                .select(item.id.countDistinct())
                .from(item)
                .where(where)
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }


    public Response itemListPaginated(
            String keyword,
            List<Long> categoryIds,
            Integer page,
            Integer perPage,
            String sortBy,
            String orderBy,
            String startDate,
            String endDate
    ) {

        int pageIndex = (page != null && page > 0) ? page - 1 : 0;
        int pageSize = (perPage != null && perPage > 0) ? perPage : 20;

        // 🔒 Prevent invalid sort field
        String safeSortBy = switch (sortBy) {
            case "itemName", "itemCode", "qty", "reOrderLevel" -> sortBy;
            default -> "id";
        };

        Sort sort = Sort.by(
                "desc".equalsIgnoreCase(orderBy)
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC,
                safeSortBy
        );

        Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);

        BooleanBuilder where = buildItemFilter(keyword, categoryIds, startDate, endDate);

        // =========================
        // MAIN QUERY (DTO + pagination)
        // =========================
        List<ItemDto> content = query
//                .select(new QItemDto(
//                        qItem.id,
//                        qItem.itemCode,
//                        qItem.itemName,
//                        qItem.qty,
//                        qItem.reOrderLevel,
//                        qItem.category.id,
//                        qItem.category.categoryName,
//                        qStock.qtyAvailable.sum()
//                ))
                //using projection bean don't need to follow the order
                .select(Projections.bean(
                        ItemDto.class,
                        qItem.id,
                        qItem.qty,
                        qItem.itemName,
                        qItem.category.id,
                        qItem.reOrderLevel,
                        qItem.category.categoryName,
                        qStock.qtyAvailable.sum().as("stockQty")

                ))
                .from(qItem)
                .leftJoin(qItem.category, qCategory)
                .leftJoin(qItem.stockList, qStock)
//                .where(likeKeyword(keyword), categoryIn(categoryIds))
                .where(where)
                .groupBy(
                        qItem.id,
                        qItem.itemCode,
                        qItem.itemName,
                        qItem.qty,
                        qItem.reOrderLevel,
                        qItem.category.id,
                        qItem.category.categoryName
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getOrderSpecifier(safeSortBy, orderBy))
                .fetch();

        // =========================
        // COUNT QUERY (NO JOIN, NO DUPLICATE)
        // =========================
        Long total = query
                .select(qItem.id.count())
                .from(qItem)
                .where(where)
                .fetchOne();

        Page<ItemDto> pageResult =
                new PageImpl<>(content, pageable, total != null ? total : 0);

        return ResponseUtils.createSuccessResponse("success", pageResult);
    }


    private BooleanBuilder buildItemFilter(
            String keyword,
            List<Long> categoryIds,
            String startDate,
            String endDate
    ) {
        BooleanBuilder where = new BooleanBuilder();

        if (keyword != null && !keyword.isBlank()) {
//            where.and(
//                    qItem.itemName.containsIgnoreCase(keyword)
//                            .or(qItem.itemCode.containsIgnoreCase(keyword))
//            );

            where.and(qItem.itemName.likeIgnoreCase("%" + keyword + "%"));

        }

        if (categoryIds != null && !categoryIds.isEmpty()) {
            where.and(qItem.category.id.in(categoryIds));
        }

        if(startDate != null && endDate != null) {
            where.and(createdAtBetween(startDate, endDate));
        }


        return where;
    }

    private OrderSpecifier<?> getOrderSpecifier(
            String sortBy,
            String orderBy
    ) {
        PathBuilder<Item> entityPath = new PathBuilder<>(Item.class, "item");

        ComparableExpressionBase<?> sortExpression =
                entityPath.getComparable(sortBy, Comparable.class);

        Order direction = "desc".equalsIgnoreCase(orderBy)
                ? Order.DESC
                : Order.ASC;

        return new OrderSpecifier<>(direction, sortExpression);
    }


    private BooleanExpression likeKeyword(String keyword) {
        return !StringUtils.hasLength(keyword) ? null :
                qItem.itemName.likeIgnoreCase("%" + keyword + "%");
    }

    private BooleanExpression categoryIn(List<Long> categoryIds) {

        if (categoryIds != null && !categoryIds.isEmpty()) {
            return qItem.category.id.in(categoryIds);
        } else{
            return null;
        }

    }


    private BooleanExpression createdAtBetween(
            String startDateStr,
            String endDateStr
    ) {
        LocalDate startDate = DateUtils.parseDate(startDateStr);
        LocalDate endDate   = DateUtils.parseDate(endDateStr);

        if (startDate == null && endDate == null) {
            return null;
        }

        if (startDate != null && endDate != null) {
            return qItem.createdAt.between(
                    DateUtils.startOfDay(startDate),
                    DateUtils.endOfDay(endDate)
            );
        }

        if (startDate != null) {
            return qItem.createdAt.goe(DateUtils.startOfDay(startDate));
        }

        return qItem.createdAt.loe(DateUtils.endOfDay(endDate));
    }


}
