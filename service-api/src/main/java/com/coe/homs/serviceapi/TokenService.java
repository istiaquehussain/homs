package com.coe.homs.serviceapi;

import java.util.Map;

public interface TokenService {
	public String genrateParmanentToken(final Map<String, String> attributes);
	public String genrateExpiryToken(final Map<String, String> attributes);
	public Map<String, String> varifyToken(final String token);
	public void invalidateToken(final String token);

}
