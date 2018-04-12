package com.preEmynence.multiTenancy.config;

import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Tenants {

	private Map<String, DataSource> dataSourceMap = new ConcurrentHashMap<>();

	public void addDataSource(String session, DataSource dataSource) {
		this.dataSourceMap.put(session, dataSource);
	}

	public Map<String, DataSource> getDataSourceMap() {
		return dataSourceMap;
	}
}