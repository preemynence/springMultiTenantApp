package com.preemynence.multitenancy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class DataSourceRouter extends AbstractDataSource {

	@Autowired
	private DataSourceProperties properties;

	@Autowired
	private Tenants tenants;

	// Should you find new dataSources based on the new tenantId received as part of request?
	private boolean tryFindingNewDataSource = true;

	protected String determineCurrentLookupKey() {
		return TenantContext.getCurrentTenant();
	}

	private DataSource initializeNewDataSourceIfNot(String lookupKey) {
		DataSourceBuilder dataSourceBuilder = new DataSourceBuilder(this.getClass().getClassLoader());

		//TODO: here find the new datasource from remote by lookup key.
		dataSourceBuilder.driverClassName(properties.getDriverClassName())
				.url(properties.getUrl().replace("#tenant#",lookupKey))
				.username(lookupKey + "_username")
				.password(lookupKey + "_password");
		if (properties.getType() != null) {
			dataSourceBuilder.type(properties.getType());
		}
		System.out.println("Setting up Datasource." + lookupKey);
		tenants.addDataSource(lookupKey, dataSourceBuilder.build());
		return tenants.getDataSourceMap().get(lookupKey);
	}

	@Override
	public Connection getConnection() throws SQLException {
		return determineTargetDataSource().getConnection();
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		return determineTargetDataSource().getConnection(username, password);
	}

	protected DataSource determineTargetDataSource() {
		Assert.notNull(tenants.getDataSourceMap(), "DataSource router not initialized");
		Object lookupKey = determineCurrentLookupKey();
		if (lookupKey == null) {
			return tenants.defaultDataSource();
		}
		DataSource dataSource = tenants.getDataSourceMap().get(lookupKey);
		if(dataSource == null && this.tryFindingNewDataSource){
			dataSource = initializeNewDataSourceIfNot(lookupKey.toString());
			if (dataSource == null) {
				throw new IllegalStateException("Cannot determine target DataSource for lookup key [" + lookupKey + "]");
			}
		} else if (dataSource == null) {
			throw new IllegalStateException("Cannot determine target DataSource for lookup key [" + lookupKey + "]");
		}
		return dataSource;
	}
}


