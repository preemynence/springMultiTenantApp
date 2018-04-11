package com.premynence.multiTenancy.config;

import com.premynence.multiTenancy.domain.Tenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class TenantsInitialize {
	
	@Autowired
	private DataSourceProperties properties;

	@Autowired
	private Tenants tenants;

	@Qualifier("myMultiTenantDataSource")
	@Autowired
	private DataSource dataSource;

	
	@PostConstruct
	public void loadTenants() {

		List<Tenant> cons = initTenants();

		DataSourceBuilder dataSourceBuilder = new DataSourceBuilder(this.getClass().getClassLoader());

		for (Tenant con : cons) {
			dataSourceBuilder.driverClassName(con.getDriver())
					.url(con.getUrl())
					.username(con.getUserName())
					.password(con.getPassword());
			if (properties.getType() != null) {
				dataSourceBuilder.type(properties.getType());
			}
			System.out.println(con.getTenantName());
			tenants.addDataSource(con.getTenantName(), dataSourceBuilder.build());
			((AbstractRoutingDataSource) dataSource).setTargetDataSources(tenants.getDataSourceMap());
			((AbstractRoutingDataSource) dataSource).afterPropertiesSet();
		}
	}

	//This can have any third party implementation for getting all the tenants info from remote.
	private List<Tenant> initTenants() {
		List<Tenant> tenants = new ArrayList<>();
		for(int i = 1;i<3;i++){
			Tenant tenant = new Tenant();
			tenant.setDriver("com.mysql.jdbc.Driver");
			tenant.setTenantName(""+i);
			tenant.setUserName("demo");
			tenant.setPassword("demo");
			tenant.setUrl("jdbc:mysql://localhost:3306/tenant" + i);
			tenants.add(tenant);
		}
		return tenants;
	}
}
