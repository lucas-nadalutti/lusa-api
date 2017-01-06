package lusa.api


import grails.rest.*
import grails.converters.*
import org.springframework.security.access.annotation.Secured

class ProductController {
	static responseFormats = ['json', 'xml']

	def productService
	
    @Secured(['ROLE_ADMIN'])
    def index() {
    	respond productService.all()
    }
	
    @Secured(['ROLE_USER', 'ROLE_ADMIN'])
    def available() {
    	respond productService.available()
    }

    @Secured(['ROLE_ADMIN'])
    def show() {
    	respond productService.get(params.id)
    }

    @Secured(['ROLE_ADMIN'])
    def save() {
    	def product = new Product(request.JSON)
    	if (product.save()) {
	    	respond product
    	}
    	else {
    		render(status:400, text:product.errors.fieldErrors as JSON)
    	}
    }

    @Secured(['ROLE_ADMIN'])
    def update() {
    	def product = Product.get(params.id)
    	product.properties = request
    	if (product.save()) {
	    	respond product
    	}
    	else {
    		render(status:400, text:product.errors.fieldErrors as JSON)
    	}
    }

    @Secured(['ROLE_ADMIN'])
    def delete() {
    	productService.delete(params.id)
    	render status:204
    }
}
