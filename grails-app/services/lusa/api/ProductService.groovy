package lusa.api

import grails.transaction.Transactional

@Transactional
class ProductService {

	def all() {
		Product.list()
	}

    def available() {
    	Product.createCriteria().list{
    		eq 'status', Product.ProductStatus.ACTIVE
    		gt 'quantity', 0
    	}
    }

    def get(id) {
    	Product.get(id)
    }

    def delete(id) {
    	def product = Product.get(id)
    	product.delete()
    }
}
