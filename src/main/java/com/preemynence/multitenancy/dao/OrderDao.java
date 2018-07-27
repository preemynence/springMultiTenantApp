package com.preemynence.multitenancy.dao;

import com.preemynence.multitenancy.domain.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao{
	List<Order> findAll();

	Order save(Order order);
}
