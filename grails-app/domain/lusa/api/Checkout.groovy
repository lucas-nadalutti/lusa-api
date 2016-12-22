package lusa.api

class Checkout {

	enum DeliveryMethod {
		SEDEX, PAC
	}

	enum PaymentMethod {
		INVOICE, CREDIT_CARD
	}

	String name
	String email
	String address
	DeliveryMethod deliveryMethod
	PaymentMethod paymentMethod
	Product product
	Date dateCreated
	Date lastUpdated

	static hasOne = [
		creditCardCheckout: CreditCardCheckout,
		invoiceCheckout: InvoiceCheckout
	]

	static mapping = {
		creditCardCheckout cascade:'all'
		invoiceCheckout cascade:'all'
	}

    static constraints = {
    	email nullable:false, email:true
    	creditCardCheckout nullable:true, validator:{value, obj ->
    		!!obj.creditCardCheckout ^ !!obj.invoiceCheckout
    	}
    	invoiceCheckout nullable:true
    	product validator:{value, obj ->
    		value.quantity > 0
    	}
    }

}
