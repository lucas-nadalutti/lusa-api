package lusa.api

import grails.rest.*

@Resource(uri='/products', formats=['json'])
class Product {

	enum ProductStatus {
		ACTIVE, INACTIVE
	}

	String name
	String description
	ProductStatus status
	double fullPrice
	double currentPrice
	String imageUrl
	Date dateCreated
	Date lastUpdated	

    static constraints = {
    	name nullable:false, maxSize:128
    	description nullable:false
    	status nullable:false
    	fullPrice nullable:false, min:0d
    	currentPrice min:0d
    }
}
