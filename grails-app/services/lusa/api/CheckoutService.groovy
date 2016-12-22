package lusa.api

import grails.transaction.Transactional
import groovy.time.TimeCategory
import java.text.NumberFormat

@Transactional
class CheckoutService {

	def all() {
		Checkout.list()
	}

    def get(id) {
    	Checkout.get(id)
    }

    def save(checkout) {
    	if (checkout.paymentMethod == Checkout.PaymentMethod.INVOICE) {
    		checkout = fillInvoiceCheckout(checkout)
    	}

    	def savedCheckout = checkout.save()
        if (savedCheckout) {
	    	def product = Product.get(savedCheckout.product.id)
        	product.quantity--
        	product.save()

        	def formatter = java.text.NumberFormat.currencyInstance
        	def price = formatter.format(product.price)
	        sendMail {
	            from "lucasnadalutti@gmail.com"
	            to savedCheckout.email
	            subject "Compra realizada com sucesso!"
	            text "Sua compra de ${product.name} no valor de ${price} foi realizada com sucesso!"
	        }
	        return savedCheckout
        }
    }

	def fillInvoiceCheckout(checkout) {
		checkout.invoiceCheckout.number = '12345'
		use (TimeCategory) {
			checkout.invoiceCheckout.dueDate = new Date() + 7.day			
		}
		checkout
	}
}
