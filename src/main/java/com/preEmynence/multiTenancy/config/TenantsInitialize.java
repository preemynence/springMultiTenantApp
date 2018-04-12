package com.preEmynence.multiTenancy.config;

import org.springframework.stereotype.Component;

@Component
public class TenantsInitialize {
	
//	@Autowired
//	private DataSourceProperties properties;
//
//	@Autowired
//	private Tenants tenants;
//
//	@Qualifier("myMultiTenantDataSource")
//	@Autowired
//	private DataSource dataSource;
//
//
////	@PostConstruct
////	public void loadTenants() {
////
//////		List<Tenant> cons = initTenants();
////
////		DataSourceBuilder dataSourceBuilder = new DataSourceBuilder(this.getClass().getClassLoader());
////
////		dataSourceBuilder.driverClassName(properties.getDriverClassName())
////				.url(properties.getUrl())
////				.username(properties.getUsername())
////				.password(properties.getPassword());
////		if (properties.getType() != null) {
////			dataSourceBuilder.type(properties.getType());
////		}
////		System.out.println("Setting up Default Datasource.");
////		tenants.addDataSource("default", dataSourceBuilder.build());
////		((AbstractRoutingDataSource) dataSource).setTargetDataSources(tenants.getDataSourceMap());
////		((AbstractRoutingDataSource) dataSource).afterPropertiesSet();
////
////	}
//
//	//This can have any third party implementation for getting all the tenants info from remote.
//	private List<Tenant> initTenants() {
//		List<Tenant> tenants = new ArrayList<>();
//		for(int i = 1;i<3;i++){
//			Tenant tenant = new Tenant();
//			tenant.setDriver("com.mysql.jdbc.Driver");
//			tenant.setTenantName(""+i);
//			tenant.setUserName("demo");
//			tenant.setPassword("demo");
//			tenant.setUrl("jdbc:mysql://localhost:3306/tenant" + i);
//			tenants.add(tenant);
//		}
//		return tenants;
//	}
}
