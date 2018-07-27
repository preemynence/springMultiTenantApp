package com.preemynence.multitenancy.service.impl;

import com.preemynence.multitenancy.dao.OrderDao;
import com.preemynence.multitenancy.domain.Order;
import com.preemynence.multitenancy.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;

	@Override
	public Order saveOrder(Order order) {
		return orderDao.save(order);
	}

	@Override
	public List<Order> findAll() {
		return orderDao.findAll();
	}
}
