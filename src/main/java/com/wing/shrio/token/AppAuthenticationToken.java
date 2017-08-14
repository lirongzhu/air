package com.wing.shrio.token;

import org.apache.shiro.authc.AuthenticationToken;

public class AppAuthenticationToken implements AuthenticationToken{

	private static final long serialVersionUID = 1L;
	
	private String username;
    private String password;
    
	public AppAuthenticationToken(String username, String password){
		this.username = username;
        this.password = password;
	}
	
    @Override
    public String getPrincipal() {
        return this.username;
    }

    @Override
    public Object getCredentials() {
        return this.password;
    }
}
