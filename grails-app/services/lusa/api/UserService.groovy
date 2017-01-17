package lusa.api

import grails.transaction.Transactional

@Transactional
class UserService {

    def findOrSave(data) {
    	def user = User.findOrCreateByUsername(data.login)
    	user.email = data.email
		user.save()
    	UserRole.create(user, Role.findByAuthority("ROLE_USER"))
    	return user
    }
}
