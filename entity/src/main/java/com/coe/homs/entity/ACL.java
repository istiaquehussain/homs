package com.coe.homs.entity;

import java.util.HashMap;
import java.util.Map;

public class ACL {
	Map<String,String> data=new HashMap<String,String>();

	private static ACL instance = null;
	
	public static ACL getInstance()
    {
        if (instance == null)
            instance = new ACL();
 
        return instance;
    }
	
	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}
	public ACL addAccess(String url,String role)
	{
		data.put(url, role);
		return instance;
	}
}
