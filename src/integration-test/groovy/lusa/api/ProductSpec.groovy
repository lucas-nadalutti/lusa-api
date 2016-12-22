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
class ProductSpec extends GebSpec {

    def setup() {
        def product1 = new Product(
            name:'Produto1',
            description:'Um produto',
            quantity:10,
            fullPrice:10.5,
            status:Product.ProductStatus.ACTIVE,
            imageUrl:'http://produto.produto'
        )
        product1.save()

        def product2 = new Product(
            name:'Produto2',
            description:'Um produto',
            quantity:0,
            fullPrice:10.5,
            status:Product.ProductStatus.ACTIVE,
            imageUrl:'http://produto.produto'
        )
        product2.save()

        def product3 = new Product(
            name:'Produto3',
            description:'Um produto',
            quantity:10,
            fullPrice:10.5,
            status:Product.ProductStatus.INACTIVE,
            imageUrl:'http://produto.produto'
        )
        product3.save()
    }

    def cleanup() {
        def products = Product.findAll()
        products*.delete()
    }

    void "Test available products"() {
        when:"The products list is requested"
            def resp = restBuilder().get("$baseUrl/product/available")

        then:"The response is the only available product"
            resp.status == OK.value()
            resp.json.size() == 1

            def jsonProduct = resp.json[0]
            jsonProduct.name == 'Produto1'
    }

    RestBuilder restBuilder() {
        new RestBuilder()
    }
}
