beans = {

	restAuthenticationFilter(lusa.api.LusaAuthFilter) {
		userService = ref("userService")
		grailsLinkGenerator = ref("grailsLinkGenerator")
		casUri = application.config.lusa.cas.uri
		casLoginEndpoint = application.config.lusa.cas.loginEndpoint
		casValidationEndpoint = application.config.lusa.cas.validationEndpoint
		casTicketParam = application.config.lusa.cas.ticketParam
		casServiceParam = application.config.lusa.cas.serviceParam
		casClientRedirectUriParam = application.config.lusa.cas.clientRedirectUriParam

		endpointUrl = application.config.grails.plugin.springsecurity.rest.login.endpointUrl
        authenticationEventPublisher = ref("authenticationEventPublisher")
        tokenGenerator = ref("tokenGenerator")
        tokenStorageService = ref("tokenStorageService")
	}

}
