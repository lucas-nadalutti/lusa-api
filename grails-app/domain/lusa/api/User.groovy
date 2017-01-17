package lusa.api

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.springframework.security.core.userdetails.UserDetails

@EqualsAndHashCode(includes='username')
@ToString(includes='username', includeNames=true, includePackage=false)
class User implements Serializable, UserDetails {

	private static final long serialVersionUID = 1

	// transient springSecurityService

	String username
	String password
	String email
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

	User(String username, String password) {
		this()
		this.username = username
		this.password = password
	}

	Set<Role> getAuthorities() {
		UserRole.withTransaction {
			return UserRole.findAllByUser(this)*.role
		}
	}

	boolean isCredentialsNonExpired() {
		!passwordExpired
	}

	boolean isAccountNonExpired() {
		!accountExpired
	}

	boolean isAccountNonLocked() {
		!accountLocked
	}

	// static transients = ['springSecurityService']

	static constraints = {
		username blank: false, unique: true
		password nullable: true
	}
}
