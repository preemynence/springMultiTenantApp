package com.preEmynence.multiTenancy.service;

import com.preEmynence.multiTenancy.domain.Order;

import java.util.List;

public interface OrderService {
	Order saveOrder(Order order);

	List<Order> findAll();
}
