package lusa.api

import groovy.json.JsonSlurper
import grails.plugins.rest.client.RestBuilder
import grails.plugin.springsecurity.rest.RestAuthenticationFilter
import grails.plugin.springsecurity.rest.token.AccessToken
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.grails.web.util.WebUtils
import org.springframework.security.core.context.SecurityContextHolder
import grails.web.mapping.LinkGenerator

class LusaAuthFilter extends RestAuthenticationFilter {

	LinkGenerator grailsLinkGenerator
	UserService userService
	String casUri
	String casLoginEndpoint
	String casValidationEndpoint
	String casTicketParam
	String casServiceParam
	String casClientRedirectUriParam

	String getCompleteUri(String endpoint) {
		"${grailsLinkGenerator.serverBaseURL}${endpoint}"
	}

	String getService(callbackUri, clientRedirectUri) {
		"${callbackUri}?${casClientRedirectUriParam}=${clientRedirectUri}"
	}

	String getValidationUrl(ticket, service) {
		"${casUri}${casValidationEndpoint}?${casTicketParam}=${ticket}&${casServiceParam}=${service}"
	}

	@Override
    void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	HttpServletRequest httpRequest = request as HttpServletRequest
    	HttpServletResponse httpResponse = response as HttpServletResponse

    	def actualUri = httpRequest.requestURI - httpRequest.contextPath
    	if (actualUri == endpointUrl) {

    		// It's expected that the user is redirected to this URI (GET request) after CAS authentication
	    	if (httpRequest.method != "GET") {
	            httpResponse.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED)
	        }

	    	def params = WebUtils.fromQueryString(request.queryString)

	    	def currentUri = getCompleteUri(request.forwardURI)
	    	def service = getService(currentUri, params.redirectUri)
	    	def validationUrl = getValidationUrl(params.ticket, service)

	    	def restBuilder = new RestBuilder()
			def resp = restBuilder.get(validationUrl)
			def authenticationSuccess = resp.xml.getProperty("authenticationSuccess")
			
			if (!authenticationSuccess.size()) {
		    	httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED)
			}
			else {
				def jsonAttributes = authenticationSuccess.getProperty("attributes").text()

				def slurper = new JsonSlurper()
				def attributes = slurper.parseText(jsonAttributes)

	    		def user = userService.findOrSave(attributes)

	    		AccessToken accessToken = tokenGenerator.generateAccessToken(user)
                tokenStorageService.storeToken(accessToken.accessToken, user)

				authenticationEventPublisher.publishTokenCreation(accessToken)

				SecurityContextHolder.context.setAuthentication(accessToken)

				def accessTokenUrl = "access_token=${accessToken.accessToken}"
				def refreshTokenUrl = "refresh_token=${accessToken.refreshToken}"
				def tokenTypeUrl = "token_type=Bearer"
				def expirationUrl = "expires_in=${accessToken.expiration}"
				def finalRedirectUri = "${params.redirectUri}?${accessTokenUrl}&${refreshTokenUrl}&${expirationUrl}&${tokenTypeUrl}"
				
				httpResponse.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY)
				httpResponse.setHeader('Location', finalRedirectUri)
    		}	
    	}
    	else {
	    	chain.doFilter(request, response);
    	}
    }

}