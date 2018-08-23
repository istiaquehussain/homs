package com.coe.homs.service;

import org.springframework.stereotype.Service;

import com.coe.homs.entity.ACL;
import com.coe.homs.serviceapi.ConfigurationService;
@Service("InMemoryConfigurationService")
public class InMemoryConfigurationService implements ConfigurationService {

	@Override
	public ACL getAcl() {
		return ACL.getInstance()
		.addAccess("/admin/**","ADMIN")
		.addAccess("/private/**","USER");
	}

}
