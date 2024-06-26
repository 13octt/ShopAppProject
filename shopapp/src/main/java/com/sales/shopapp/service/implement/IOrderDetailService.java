package com.sales.shopapp.service.implement;

import com.sales.shopapp.dto.request.OrderDetailDto;
import com.sales.shopapp.entity.OrderDetail;
import com.sales.shopapp.exception.DataNotFoundException;

public interface IOrderDetailService {
    OrderDetail createOrderDetail(OrderDetailDto newOrderDetail) throws Exception;

    OrderDetail getOrderDetail(Long id) throws DataNotFoundException;

    OrderDetail updateOrderDetail(Long id, OrderDetailDto newOrderDetailData)
            throws DataNotFoundException;

    void deleteById(Long id);

//    List<OrderDetail> findByOrderId(Long orderId);

}
