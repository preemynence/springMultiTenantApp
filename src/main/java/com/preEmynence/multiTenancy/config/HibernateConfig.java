package com.preEmynence.multiTenancy.config;

import org.hibernate.MultiTenancyStrategy;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

@Configuration
public class HibernateConfig {

	@Value("${spring.jpa.database-platform}")
	private String hibernateDialect;

	@Autowired
	private MultiTenantDataSources multiTenantDataSources;
	/**
	 * Convenience for {@link #sessionFactoryBean().getObject()}.
	 */
	@Bean
	public SessionFactory sessionFactory() {
		return this.sessionFactoryBean().getObject();
	}

	@Bean
	public LocalSessionFactoryBean sessionFactoryBean() {
		return this.createSessionFactoryBean();
	}

	protected LocalSessionFactoryBean createSessionFactoryBean() {

		LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
		factoryBean.setDataSource(multiTenantDataSources.myMultiTenantDataSource());

		factoryBean.getHibernateProperties().setProperty(AvailableSettings.DIALECT,
				hibernateDialect);

		// configure for multitenancy
		if (!(multiTenantDataSources.myMultiTenantDataSource() instanceof DataSourceRouter)) {
			throw new IllegalStateException("a DataSourceRouter is required");
		}

		factoryBean.getHibernateProperties().setProperty(AvailableSettings.MULTI_TENANT,
				MultiTenancyStrategy.DATABASE.toString());
		factoryBean.setMultiTenantConnectionProvider(
				new MultiTenantConnectionProviderImpl((DataSourceRouter) multiTenantDataSources.myMultiTenantDataSource()));
		factoryBean.setCurrentTenantIdentifierResolver(
				new CurrentTenantIdentifierResolverImpl(TenantContext.getCurrentTenant()));

		// update guard
		factoryBean.setEntityInterceptor(new MultiTenantEntityInterceptor(TenantContext.getCurrentTenant()));

		return factoryBean;

	}
}