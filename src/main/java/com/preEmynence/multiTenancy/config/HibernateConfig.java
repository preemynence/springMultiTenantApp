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

	@Autowired
	DataSourceRouter dataSourceRouter;

	@Value("${spring.jpa.database-platform}")
	private String hibernateDialect;

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
		factoryBean.setDataSource(dataSourceRouter);

		factoryBean.getHibernateProperties().setProperty(AvailableSettings.DIALECT,
				hibernateDialect);


		factoryBean.getHibernateProperties().setProperty(AvailableSettings.MULTI_TENANT,
				MultiTenancyStrategy.DATABASE.toString());
		factoryBean.setMultiTenantConnectionProvider(
				new MultiTenantConnectionProviderImpl(dataSourceRouter));
		factoryBean.setCurrentTenantIdentifierResolver(
				new CurrentTenantIdentifierResolverImpl(TenantContext.getCurrentTenant()));

		// update guard
		factoryBean.setEntityInterceptor(new MultiTenantEntityInterceptor(TenantContext.getCurrentTenant()));

		return factoryBean;

	}
}