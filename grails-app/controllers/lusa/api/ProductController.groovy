package lusa.api


import grails.rest.*
import grails.converters.*

class ProductController {
	static responseFormats = ['json', 'xml']
	
    def index() {
    	respond Product.list()
    }

    def save() {
    	def product = new Product(request.JSON)
    	if (!product.save()) {
    		return render(status:400, text:product.errors.fieldErrors as JSON)
    	}
    	respond product
    }
}
