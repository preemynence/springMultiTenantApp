package com.preEmynence.multiTenancy.config;

import org.hibernate.engine.jdbc.connections.internal.DatasourceConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;

import java.util.HashMap;

public class MultiTenantConnectionProviderImpl extends AbstractMultiTenantConnectionProvider {

	private DatasourceConnectionProviderImpl dataSourceConnectionProvider;

	public MultiTenantConnectionProviderImpl(DataSourceRouter dataSource) {
		this.dataSourceConnectionProvider = new DatasourceConnectionProviderImpl();
		this.dataSourceConnectionProvider.setDataSource(dataSource);
		// this triggers the 'available' flag
		this.dataSourceConnectionProvider.configure(new HashMap<Object, Object>());
	}

	@Override
	protected ConnectionProvider getAnyConnectionProvider() {
		return this.getDatasourceConnectionProvider();
	}

	@Override
	protected ConnectionProvider selectConnectionProvider(String tenantIdentifier) {
		return this.getDatasourceConnectionProvider();
	}
	
	protected DatasourceConnectionProviderImpl getDatasourceConnectionProvider() {
		return this.dataSourceConnectionProvider;
	}
}