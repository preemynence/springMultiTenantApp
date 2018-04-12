package com.preEmynence.multiTenancy.dao.impl;

import com.preEmynence.multiTenancy.dao.OrderDao;
import com.preEmynence.multiTenancy.domain.Order;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component("OrderDAO")
@Transactional
public class OrderDaoImpl implements OrderDao {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public List<Order> findAll() {
		return (List<Order>) sessionFactory.getCurrentSession().createSQLQuery("select * from orders").list();
	}

	@Override
	public Order save(Order order) {
		return (Order) sessionFactory.getCurrentSession().save(order);
	}
}
