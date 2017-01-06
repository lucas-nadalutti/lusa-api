package lusa.api

class Product {

	enum ProductStatus {
		ACTIVE, INACTIVE
	}

	String name
	String description
	ProductStatus status
	Integer quantity
	Double fullPrice
	Double currentPrice
	String imageUrl
	Date dateCreated
	Date lastUpdated

	static hasMany = [checkouts: Checkout]

	static transients = ['price']

    static constraints = {
    	name maxSize:128, unique:['imageUrl']
    	quantity min:0
    	fullPrice min:0d
    	currentPrice nullable:true, min:0d
    }

	Double getPrice() {
		currentPrice ?: fullPrice
	}
}
