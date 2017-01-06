package lusa.api


import grails.rest.*
import grails.converters.*
import org.springframework.security.access.annotation.Secured

class CheckoutController {
	static responseFormats = ['json', 'xml']
	
    def checkoutService
    
    @Secured(['ROLE_ADMIN'])
    def index() {
        respond checkoutService.all()
    }
    
    @Secured(['ROLE_ADMIN'])
    def show() {
        respond checkoutService.get(params.id)
    }

    @Secured(['ROLE_USER', 'ROLE_ADMIN'])
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
