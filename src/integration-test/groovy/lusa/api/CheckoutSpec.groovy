package lusa.api


import grails.test.mixin.integration.Integration
import grails.transaction.*
import static grails.web.http.HttpHeaders.*
import static org.springframework.http.HttpStatus.*
import spock.lang.*
import geb.spock.*
import grails.plugins.rest.client.RestBuilder

@Integration
@Rollback
class CheckoutSpec extends GebSpec {

    def product

    def setup() {
        product = new Product(
            name:'Produto',
            description:'Um produto',
            quantity:10,
            fullPrice:10.5,
            status:Product.ProductStatus.ACTIVE,
            imageUrl:'http://produto.produto'
        )
        product.save()
    }

    def cleanup() {
        def checkouts = Checkout.findAll()
        checkouts*.delete()
        def products = Product.findAll()
        products*.delete()
    }

    void "Test the invoice checkout creation"() {
        when:"The creation endpoint is hit"
            def resp = restBuilder().post("$baseUrl/checkout") {
                json([
                    name:'Lucas',
                    email:'lucas@lucas.com',
                    address:'Casa do Lucas',
                    product:product.id,
                    deliveryMethod:'PAC',
                    paymentMethod:'INVOICE',
                    paymentData:[:]
                ])
            }

        then:"The checkout is created"
            resp.status == OK.value()
            resp.json.invoiceCheckout.number == '12345'
    }

    void "Test the credit card checkout creation"() {
        when:"The creation endpoint is hit"
            def resp = restBuilder().post("$baseUrl/checkout") {
                json([
                    name:'Lucas',
                    email:'lucas@lucas.com',
                    address:'Casa do Lucas',
                    product:product.id,
                    deliveryMethod:'PAC',
                    paymentMethod:'CREDIT_CARD',
                    paymentData:[
                        flag:'VISA',
                        number:'123456789',
                        name:'LUCAS',
                        dueDate:'10/2020',
                        securityCode:'123'
                    ]
                ])
            }

        then:"The checkout is created"
            resp.status == OK.value()
            resp.json.creditCardCheckout.number == '123456789'
    }

    RestBuilder restBuilder() {
        new RestBuilder()
    }
}
