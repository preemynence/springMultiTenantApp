package com.premynence.multiTenancy.service;

import com.premynence.multiTenancy.domain.Order;

import java.util.List;

public interface OrderService {
	Order saveOrder(Order order);

	List<Order> findAll();
}
