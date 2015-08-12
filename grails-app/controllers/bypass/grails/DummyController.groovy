package bypass.grails

import grails.converters.JSON
import mercadolibre.utils.PoolUtils

class DummyController {

    def transactionService

    def index() { }

    def testing() {
        render(text:(["message":"hola"] as JSON).toString(), status: 200, contentType:"application/json", encoding:"UTF-8")
    }

    def createTransactional() {
        def json = request.getJSON()
        def id = transactionService.withTransaction(json.trx_id, json.payment_id)
        Thread.sleep(5000)
        println "[Controller]Fuera del @Transactional: " + PoolUtils.printPoolStats()
        render(text:(["id":id, trace:Thread.currentThread().getStackTrace()] as JSON).toString(), status: 200, contentType:"application/json", encoding:"UTF-8")
    }

    def createInternalTransactional() {
        def json = request.getJSON()
        def id = transactionService.createTransaction2(json.trx_id, json.payment_id)
        Thread.sleep(5000)
        println "[Controller]Fuera del servicio internal withTransaction: ${PoolUtils.printPoolStats()}"
        render(text:(["id":id, trace:Thread.currentThread().getStackTrace()] as JSON).toString(), status: 200, contentType:"application/json", encoding:"UTF-8")
    }

    def createTransaction() {
        def json = request.getJSON()
        def id = transactionService.createTransaction(json.trx_id, json.payment_id)
        Thread.sleep(5000)
        println "[Controller] Fuera de save(flush:true): ${PoolUtils.printPoolStats()}"
        render(text:(["id":id, trace:Thread.currentThread().getStackTrace()] as JSON).toString(), status: 200, contentType:"application/json", encoding:"UTF-8")
    }
}
