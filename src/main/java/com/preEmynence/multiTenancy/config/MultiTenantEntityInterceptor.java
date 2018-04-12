package com.preEmynence.multiTenancy.config;

import com.preEmynence.multiTenancy.util.ArrayUtil;
import com.preEmynence.multiTenancy.util.StringUtil;
import org.hibernate.EmptyInterceptor;

import java.io.Serializable;

public class MultiTenantEntityInterceptor extends EmptyInterceptor {

	private String tenantId;

	private static final long serialVersionUID = -2372143420877673397L;

	public MultiTenantEntityInterceptor(String tenantId) {
		this.tenantId = tenantId;
	}

	@Override
	public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState,
	                            String[] propertyNames, org.hibernate.type.Type[] types) {
		return this.handleTenant(entity, id, currentState, propertyNames, types);
	}

	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames,
			org.hibernate.type.Type[] types) {
		return this.handleTenant(entity, id, state, propertyNames, types);
	}

	private boolean handleTenant(Object entity, Serializable id, Object[] currentState, String[] propertyNames,
			org.hibernate.type.Type[] types) {

		int index = ArrayUtil.indexOf(propertyNames, "tenantId");
		if (index < 0) {
			return false;
		}

		String activeTenantId = TenantContext.getCurrentTenant();
		Object tenantId = currentState[index];

		// on a new entity, set tenant id to current tenant
		if (tenantId == null || StringUtil.isEmpty(tenantId.toString())) {
			currentState[index] = activeTenantId;
			return true;
		}

		// on update, block cross tenant attempt
		else if (!tenantId.equals(activeTenantId)) {
			try {
				throw new Exception(
						"cross tenant update, tenantId=" + tenantId + ", activeTenantId=" + activeTenantId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return true;
	}
}