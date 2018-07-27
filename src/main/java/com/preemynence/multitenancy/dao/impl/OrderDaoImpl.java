package com.preemynence.multitenancy.dao.impl;

import com.preemynence.multitenancy.dao.OrderDao;
import com.preemynence.multitenancy.domain.Order;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component("OrderDAO")
@Transactional
public class OrderDaoImpl implements OrderDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Order> findAll() {
		return (List<Order>) sessionFactory.getCurrentSession().createSQLQuery("select * from orders").list();
	}

	@Override
	public Order save(Order order) {
		return (Order) sessionFactory.getCurrentSession().save(order);
	}
}
