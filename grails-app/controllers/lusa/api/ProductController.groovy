package lusa.api

class ProductController {

    def index() {
    	render Product.list() as grails.converters.JSON
    }
}
