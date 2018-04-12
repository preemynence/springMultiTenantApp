package com.preEmynence.multiTenancy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DataSourceRouter extends AbstractDataSource {

	@Autowired
	private DataSourceProperties properties;

	@Qualifier("myMultiTenantDataSource")
	@Autowired
	private DataSource dataSource;

	@Autowired
	Tenants tenants;

	private Map<String, DataSource> targetDataSources;

	private Object defaultTargetDataSource;

	private boolean lenientFallback = true;

	private boolean tryFindingNewDataSource = true;

	private DataSourceLookup dataSourceLookup = new JndiDataSourceLookup();

	private Map<String, DataSource> resolvedDataSources;

	private DataSource resolvedDefaultDataSource;


	protected String determineCurrentLookupKey() {
		return TenantContext.getCurrentTenant();
	}

	private DataSource initializeNewDataSourceIfNot(String lookupKey) {
		DataSourceBuilder dataSourceBuilder = new DataSourceBuilder(this.getClass().getClassLoader());

		//TODO: here find the new datasource from remote by lookup key.
		dataSourceBuilder.driverClassName(properties.getDriverClassName())
				.url(properties.getUrl() + lookupKey)
				.username(properties.getUsername())
				.password(properties.getPassword());
		if (properties.getType() != null) {
			dataSourceBuilder.type(properties.getType());
		}
		System.out.println("Setting up Datasource." + lookupKey);
		tenants.addDataSource(lookupKey, dataSourceBuilder.build());
		((DataSourceRouter) dataSource).setTargetDataSources(tenants.getDataSourceMap());
		((DataSourceRouter) dataSource).afterPropertiesSet();
		return tenants.getDataSourceMap().get(lookupKey);
	}

	public void setTargetDataSources(Map<String ,DataSource> targetDataSources) {
		this.targetDataSources = targetDataSources;
	}

	public void setDefaultTargetDataSource(Object defaultTargetDataSource) {
		this.defaultTargetDataSource = defaultTargetDataSource;
	}

	public void afterPropertiesSet() {
		if (this.targetDataSources == null) {
			throw new IllegalArgumentException("Property 'targetDataSources' is required");
		}
		this.resolvedDataSources = new HashMap<>(this.targetDataSources.size());
		for (Map.Entry<String, DataSource> entry : this.targetDataSources.entrySet()) {
			String lookupKey = resolveSpecifiedLookupKey(entry.getKey());
			DataSource dataSource = resolveSpecifiedDataSource(entry.getValue());
			this.resolvedDataSources.put(lookupKey, dataSource);
		}
		if (this.defaultTargetDataSource != null) {
			this.resolvedDefaultDataSource = resolveSpecifiedDataSource(this.defaultTargetDataSource);
		}
	}

	protected String resolveSpecifiedLookupKey(String lookupKey) {
		return lookupKey;
	}

	protected DataSource resolveSpecifiedDataSource(Object dataSource) throws IllegalArgumentException {
		if (dataSource instanceof DataSource) {
			return (DataSource) dataSource;
		}
		else if (dataSource instanceof String) {
			return this.dataSourceLookup.getDataSource((String) dataSource);
		}
		else {
			throw new IllegalArgumentException(
					"Illegal data source value - only [javax.sql.DataSource] and String supported: " + dataSource);
		}
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
		Assert.notNull(this.resolvedDataSources, "DataSource router not initialized");
		Object lookupKey = determineCurrentLookupKey();
		if (lookupKey == null) {
			return this.resolvedDefaultDataSource;
		}
		DataSource dataSource = this.resolvedDataSources.get(lookupKey);
		if(dataSource == null && this.tryFindingNewDataSource){
			dataSource = initializeNewDataSourceIfNot(lookupKey.toString());
			if (dataSource == null) {
				throw new IllegalStateException("Cannot determine target DataSource for lookup key [" + lookupKey + "]");
			}
		} else if (dataSource == null && (this.lenientFallback || lookupKey == null)) {
			dataSource = this.resolvedDefaultDataSource;
		} else if (dataSource == null) {
			throw new IllegalStateException("Cannot determine target DataSource for lookup key [" + lookupKey + "]");
		}
		return dataSource;
	}
}


