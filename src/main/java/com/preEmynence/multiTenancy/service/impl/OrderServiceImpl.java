package com.preEmynence.multiTenancy.service.impl;

import com.preEmynence.multiTenancy.dao.OrderDao;
import com.preEmynence.multiTenancy.domain.Order;
import com.preEmynence.multiTenancy.service.OrderService;
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
