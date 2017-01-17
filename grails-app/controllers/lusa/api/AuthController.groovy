package lusa.api


import grails.rest.*
import grails.converters.*
import grails.web.mapping.LinkGenerator
import org.springframework.beans.factory.annotation.Value


class AuthController {
    static responseFormats = ["json", "xml"]

	LinkGenerator grailsLinkGenerator

    @Value('${lusa.cas.uri}')
    String casUri

    @Value('${lusa.cas.loginEndpoint}')
    String loginEndpoint

    @Value('${lusa.cas.logoutEndpoint}')
    String logoutEndpoint

    @Value('${lusa.cas.ticketParam}')
    String ticketParam

    @Value('${lusa.cas.serviceParam}')
    String serviceParam

    @Value('${lusa.cas.clientRedirectUriParam}')
    String clientRedirectUriParam

    @Value('${grails.plugin.springsecurity.rest.login.endpointUrl}')
    String restLoginEndpoint

    def login() {
    	def casLogin = "${casUri}${loginEndpoint}"
    	def serviceValue = "${grailsLinkGenerator.serverBaseURL}${restLoginEndpoint}?${clientRedirectUriParam}=${params.redirectUri}"
    	def serviceQueryParam = "${serviceParam}=${serviceValue}"
		def callbackUrl = "${casLogin}?${serviceQueryParam}"
		redirect(url:callbackUrl)
    }

    def logout() {
		def casLogout = "${casUri}${logoutEndpoint}?service="
		if (params.redirectUri?.startsWith("https://")) {
			casLogout += "${params.redirectUri}"
		}
		else {
			casLogout += "${grailsLinkGenerator.serverBaseURL}/auth/logoutCallback?${clientRedirectUriParam}=${params.redirectUri}"
		}
    	redirect(url:casLogout)
    }

    def logoutCallback() {
    	redirect(url:params.redirectUri)
    }
}
