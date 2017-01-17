package lusa.api

class BootStrap {

    def init = { servletContext ->
    	Role userRole = new Role("ROLE_USER").save()
    	Role adminRole = new Role("ROLE_ADMIN").save()
		User user = new User("user", "pass").save()
		UserRole.create(user, adminRole)
    }
    def destroy = {
    }
}
