package com.premynence.multiTenancy.config;

import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Tenants {

	private Map<Object, Object> dataSourceMap = new ConcurrentHashMap<>();

	public void addDataSource(String session, DataSource dataSource) {
		this.dataSourceMap.put(session, dataSource);
	}

	public Map<Object, Object> getDataSourceMap() {
		return dataSourceMap;
	}
}