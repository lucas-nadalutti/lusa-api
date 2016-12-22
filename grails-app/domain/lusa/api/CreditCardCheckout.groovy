package lusa.api

class CreditCardCheckout {

	enum CardFlag {
		VISA, MASTERCARD
	}

	CardFlag flag
	Checkout checkout
	String number
	String name
	String dueDate
	String securityCode
	Date dateCreated
	Date lastUpdated

    static constraints = {
    }
}
