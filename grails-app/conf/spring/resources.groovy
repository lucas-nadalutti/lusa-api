import lusa.api.LusaAuthProvider

beans = {
	authenticationEntryPoint(org.springframework.security.web.authentication.Http403ForbiddenEntryPoint)
	
	userService(lusa.api.UserService) {

	}

	lusaAuthProvider(LusaAuthProvider) {
		userService = ref('userService')
	}
}
