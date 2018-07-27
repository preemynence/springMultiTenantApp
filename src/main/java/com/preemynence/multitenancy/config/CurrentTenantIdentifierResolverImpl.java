package com.preemynence.multitenancy.config;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

	public CurrentTenantIdentifierResolverImpl(String tenantId) {
		TenantContext.setCurrentTenant(tenantId);
	}

	public String resolveCurrentTenantIdentifier() {
		return TenantContext.getCurrentTenant();
	}

	public boolean validateExistingCurrentSessions() {
		return true;
	}
}