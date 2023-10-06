package com.hmtrade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmall.common.exception.BadRequestException;
import com.hmall.common.utils.UserContext;

import com.hmapi.client.CartClient;
import com.hmapi.client.ItemClient;
import com.hmapi.dto.ItemDTO;
import com.hmtrade.domain.dto.OrderDetailDTO;
import com.hmtrade.domain.dto.OrderFormDTO;
import com.hmtrade.domain.po.Order;
import com.hmtrade.domain.po.OrderDetail;
import com.hmtrade.mapper.OrderMapper;
import com.hmtrade.service.IOrderDetailService;
import com.hmtrade.service.IOrderService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2023-05-05
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

//    private final IItemService itemService;
    private final IOrderDetailService detailService;
//    private final ICartService cartService;

    private final CartClient cartClient;
    private final ItemClient itemClient;

    @Override
    @Transactional
    public Long createOrder(OrderFormDTO orderFormDTO) {
        // 1.订单数据
        Order order = new Order();
        // 1.1.查询商品
        List<OrderDetailDTO> detailDTOS = orderFormDTO.getDetails();

        List<com.hmapi.dto.OrderDetailDTO> orderDetailDTOS = detailDTOS.stream().map(dto -> {
            com.hmapi.dto.OrderDetailDTO dtoOrderDetail = new com.hmapi.dto.OrderDetailDTO();
            dtoOrderDetail.setItemId(dto.getItemId());
            dtoOrderDetail.setNum(dto.getNum());
            return dtoOrderDetail;
        }).collect(Collectors.toList());

//        List<com.hmapi.dto.OrderDetailDTO> items
        // 1.2.获取商品id和数量的Map
        Map<Long, Integer> itemNumMap = detailDTOS.stream()
                .collect(Collectors.toMap(OrderDetailDTO::getItemId, OrderDetailDTO::getNum));
        Set<Long> itemIds = itemNumMap.keySet();
        List<Long> itemIdslongList = itemIds.stream().collect(Collectors.toList());


        // 1.3.查询商品
        List<ItemDTO> items = itemClient.queryItemByIds(itemIdslongList);
        if (items == null || items.size() < itemIds.size()) {
            throw new BadRequestException("商品不存在");
        }
        // 1.4.基于商品价格、购买数量计算商品总价：totalFee
        int total = 0;
        for (ItemDTO item : items) {
            total += item.getPrice() * itemNumMap.get(item.getId());
        }
        order.setTotalFee(total);
        // 1.5.其它属性
        order.setPaymentType(orderFormDTO.getPaymentType());
        order.setUserId(UserContext.getUser());
        order.setStatus(1);
        // 1.6.将Order写入数据库order表中
        save(order);

        // 2.保存订单详情
        List<OrderDetail> details = buildDetails(order.getId(), items, itemNumMap);
        detailService.saveBatch(details);

        // 3.清理购物车商品

        cartClient.deleteCarByIds(itemIdslongList);

        // 4.扣减库存
        try {
            itemClient.deductStock(orderDetailDTOS);
        } catch (Exception e) {
            throw new RuntimeException("库存不足！");
        }
        return order.getId();
    }

    @Override
    public void markOrderPaySuccess(Long orderId) {
        Order order = new Order();
        order.setId(orderId);
        order.setStatus(2);
        order.setPayTime(LocalDateTime.now());
        updateById(order);
    }

    private List<OrderDetail> buildDetails(Long orderId, List<ItemDTO> items, Map<Long, Integer> numMap) {
        List<OrderDetail> details = new ArrayList<>(items.size());
        for (ItemDTO item : items) {
            OrderDetail detail = new OrderDetail();
            detail.setName(item.getName());
            detail.setSpec(item.getSpec());
            detail.setPrice(item.getPrice());
            detail.setNum(numMap.get(item.getId()));
            detail.setItemId(item.getId());
            detail.setImage(item.getImage());
            detail.setOrderId(orderId);
            details.add(detail);
        }
        return details;
    }
}
