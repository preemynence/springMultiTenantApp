package com.preemynence.multitenancy.controllers;

import com.preemynence.multitenancy.domain.Order;
import com.preemynence.multitenancy.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;

@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;

	@RequestMapping(path = "/orders", method = RequestMethod.POST)
	public ResponseEntity<?> createSampleOrder() {
		return ResponseEntity.ok(orderService.saveOrder(new Order(new Date(System.currentTimeMillis()))));
	}

	@RequestMapping(path = "/orders", method = RequestMethod.GET,produces = "application/json")
	public List<Order> getSampleOrder() {
		return orderService.findAll();
	}
}
