package lusa.api


import grails.rest.*
import grails.converters.*

class CheckoutController {
	static responseFormats = ['json', 'xml']
	
    def checkoutService
    
    def index() {
        respond checkoutService.all()
    }
    
    def show() {
        respond checkoutService.get(params.id)
    }

    def save() {
        def data = request.JSON
        def method = Checkout.PaymentMethod.valueOf(data.paymentMethod);
        
    	def checkout = new Checkout(data)

        if (method == Checkout.PaymentMethod.INVOICE) {
            checkout.invoiceCheckout = new InvoiceCheckout(data.paymentData)
            checkout.invoiceCheckout.checkout = checkout
        }
        else if (method == Checkout.PaymentMethod.CREDIT_CARD) {
            checkout.creditCardCheckout = new CreditCardCheckout(data.paymentData)
            checkout.creditCardCheckout.checkout = checkout
        }
    	if (checkoutService.save(checkout)) {
	    	respond checkout
    	}
    	else {
    		render(status:400, text:checkout.errors.fieldErrors as JSON)
    	}
    }
}
