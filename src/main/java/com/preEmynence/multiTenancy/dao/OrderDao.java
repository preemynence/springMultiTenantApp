package com.preEmynence.multiTenancy.dao;

import com.preEmynence.multiTenancy.domain.Order;

import java.util.List;

public interface OrderDao{
	List<Order> findAll();

	Order save(Order order);
}
