package com.premynence.multiTenancy.service.impl;

import com.premynence.multiTenancy.domain.Order;
import com.premynence.multiTenancy.repo.OrderRepository;
import com.premynence.multiTenancy.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Override
	public Order saveOrder(Order order) {
		return orderRepository.save(order);
	}

	@Override
	public List<Order> findAll() {
		return (List<Order>) orderRepository.findAll();
	}
}
