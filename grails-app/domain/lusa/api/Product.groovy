package lusa.api

class Product {

	enum ProductStatus {
		ACTIVE, INACTIVE
	}

	String name
	String description
	ProductStatus status
	Double fullPrice
	Double currentPrice
	String imageUrl
	Date dateCreated
	Date lastUpdated	

    static constraints = {
    	name nullable:false, maxSize:128, unique:['imageUrl']
    	description nullable:false
    	status nullable:false
    	fullPrice nullable:false, min:0d
    	currentPrice nullable:true, min:0d
    }
}
