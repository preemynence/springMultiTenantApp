package com.preemynence.multitenancy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Tenants {

	@Autowired
	private DataSourceProperties properties;

	private Map<String, DataSource> dataSourceMap = new ConcurrentHashMap<>();

	public void addDataSource(String session, DataSource dataSource) {
		this.dataSourceMap.put(session, dataSource);
	}

	public Map<String, DataSource> getDataSourceMap() {
		return dataSourceMap;
	}

	public DataSource defaultDataSource() {
		DataSourceBuilder dataSourceBuilder = new DataSourceBuilder(this.getClass().getClassLoader())
				.driverClassName(properties.getDriverClassName()).url(properties.getUrl().replace("#tenant#","dummy"))
				.username(properties.getUsername()).password(properties.getPassword());

		if (properties.getType() != null) {
			dataSourceBuilder.type(properties.getType());
		}

		return dataSourceBuilder.build();
	}
}