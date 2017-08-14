package com.wing.shrio.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;

import com.wing.shrio.token.AppAuthenticationToken;

public class AppTokenAuthenticatingFilter extends AuthenticatingFilter {

	protected static final String AUTHORIZATION_HEADER = "customer_infomation";
	
    @Override
    protected AuthenticationToken createToken(ServletRequest request,
                                              ServletResponse response) throws Exception {

        String authorizationHeader = getAuthHeader(request);
        
        if (authorizationHeader == null || authorizationHeader.length() == 0) {
            return new AppAuthenticationToken("", "");
        }

        try {
            String[] prinCred = getPrincipalsAndCredentials(authorizationHeader);
            
            if (prinCred == null || prinCred.length < 2) {
                return new AppAuthenticationToken("", "");
            }
            
            String username = prinCred[0];
            String password = prinCred[1];
            
            return new AppAuthenticationToken(username, password);
        } catch (AuthenticationException e) {
            super.onLoginFailure(new AppAuthenticationToken("", ""), e, request, response);
        }

        return new AppAuthenticationToken("", "");
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        
    	AuthenticationToken token = createToken(request, response);
        if (token == null) {
            String msg = "createToken method implementation returned null. A valid non-null AuthenticationToken "
                    + "must be created in order to execute a login attempt.";
            throw new IllegalStateException(msg);
        }
        
        try {
        	
            Subject subject = getSubject(request, response);
            subject.login(token);
            return onLoginSuccess(token, subject, request, response);
        } catch (AuthenticationException e) {
            return onLoginFailure(token, e, request, response);
        }
    }
    
    protected String getAuthHeader(ServletRequest request) {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        return httpRequest.getHeader(AUTHORIZATION_HEADER);
    }

    protected String[] getPrincipalsAndCredentials(String authorizationHeader) {
        if (authorizationHeader == null) {
            return null;
        }

        String[] authTokens = authorizationHeader.split("_");

        if (authTokens == null || authTokens.length < 2) {
            return null;
        }

        return authTokens;
    }
}
