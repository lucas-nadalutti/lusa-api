package lusa.api

import grails.transaction.Transactional

@Transactional
class UserRoleService {

    def findAllByUser(user) {
    	UserRole.findAllByUser(user)
    }
}
