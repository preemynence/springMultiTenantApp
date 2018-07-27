package com.preemynence.multitenancy.service;

import com.preemynence.multitenancy.domain.Order;

import java.util.List;

public interface OrderService {
	Order saveOrder(Order order);

	List<Order> findAll();
}
