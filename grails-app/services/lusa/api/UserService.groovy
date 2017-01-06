package lusa.api

import grails.transaction.Transactional

@Transactional
class UserService {

    def findByUsername(username) {
    	User.findByUsername(username)
    }
}
