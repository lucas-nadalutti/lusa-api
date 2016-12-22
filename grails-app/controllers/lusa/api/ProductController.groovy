package lusa.api


import grails.rest.*
import grails.converters.*

class ProductController {
	static responseFormats = ['json', 'xml']

	def productService
	
    def index() {
    	respond productService.all()
    }
	
    def available() {
    	respond productService.available()
    }

    def show() {
    	respond productService.get(params.id)
    }

    def save() {
    	def product = new Product(request.JSON)
    	if (product.save()) {
	    	respond product
    	}
    	else {
    		render(status:400, text:product.errors.fieldErrors as JSON)
    	}
    }

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

    def delete() {
    	productService.delete(params.id)
    	render status:204
    }
}
