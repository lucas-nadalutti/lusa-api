package lusa.api

import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.authentication.*

class LusaAuthProvider implements AuthenticationProvider {

	def userService
	
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		def user = userService.findByUsername(auth.name)

		if (!user) {
			return null
		}

		return new UsernamePasswordAuthenticationToken(
			user, auth.credentials, user.authorities
		)

	}

	boolean supports(Class<? extends Object> authenticationClass) {
    	true
	}
}