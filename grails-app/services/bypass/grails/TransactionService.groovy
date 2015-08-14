package bypass.grails

import grails.transaction.Transactional
import mercadolibre.utils.PoolUtils


class TransactionService {

    
    def createTransaction(String trxId, Long paymentId ) {

        Transaction trx = new Transaction([trxId:trxId, paymentId:paymentId, dateCreated:new Date()])
        
        if (trx.validate()) {

            println "Sin transaccion antes del save(flush:true): ${PoolUtils.printPoolStats()}"

            trx.save(flush:true)

            println "Sin transaccion despues del save(flush:true): ${PoolUtils.printPoolStats()}"

            Thread.sleep(10000)
            }
        
        return trx.id
    }

    private printStackTrace() {
        StringBuilder sb = new StringBuilder()
        for ( StackTraceElement ste in Thread.currentThread().getStackTrace()) {
            sb <<   ste.className + "." + ste.methodName + "() line: " + ste.lineNumber + "\n"
        }
        return sb.toString()
    }


    def createTransaction2(String trxId, Long paymentId ) {
        Transaction trx

        println "antes de llamar internal withTransaction: ${PoolUtils.printPoolStats()}"

        Transaction.withTransaction {
            trx = new Transaction([trxId:trxId, paymentId:paymentId, dateCreated:new Date()])

            if (trx.validate()) {

                trx.save()

                println "Dentro de internal withTransaction: ${PoolUtils.printPoolStats()}"

                Thread.sleep(10000)
            }

        }

        println "Fuera de internal withTransaction: ${PoolUtils.printPoolStats()}"

        return trx.id
    }


    def createWithTransactionTwice(String trxId, Long paymentId ) {
        Transaction trx

        println "antes de llamar internal findByTrxId: ${PoolUtils.printPoolStats()}"

        def tran = Transaction.findByTrxId(trxId)

        println "antes de llamar internal withTransaction: ${PoolUtils.printPoolStats()}"

        Transaction.withTransaction {
            trx = new Transaction([trxId:trxId, paymentId:paymentId, dateCreated:new Date()])

            if (trx.validate()) {

                trx.save()

                println "Dentro del primer bloque internal withTransaction: ${PoolUtils.printPoolStats()}"

                Thread.sleep(10000)
            }

        }

            println "Entre bloques internos withTransaction: ${PoolUtils.printPoolStats()}"

        Transaction.withTransaction {
            trx = new Transaction([trxId:trxId, paymentId:paymentId, dateCreated:new Date()])

            if (trx.validate()) {

                trx.save()

                println "Dentro del segundo bloque de internal withTransaction: ${PoolUtils.printPoolStats()}"

                Thread.sleep(10000)
            }

        }

        println "Fuera de internal withTransaction: ${PoolUtils.printPoolStats()}"

        tran = Transaction.findByTrxId(trxId)
        println "Despues de findByTrxId: ${PoolUtils.printPoolStats()}"

        return trx.id
    }

    def testCreationIdle(long time) {
        Transaction.withNewSession {
            Thread.sleep(time)
        }

        return "ok"
    }

    @Transactional
    def withTransaction(String trxId, Long paymentId ) {
        println "antes del @Transactional save: ${PoolUtils.printPoolStats()}"

        Transaction trx = new Transaction([trxId:trxId, paymentId:paymentId, dateCreated:new Date()])

        if (trx.validate()) {

            printStackTrace()

            trx.save()
        }

        Thread.sleep(10000)


        println "Despues del @Transactional save: ${PoolUtils.printPoolStats()}"

        return trx.id
    }

    def tellMe(String msg) {
        return ["receive":msg]
    }

        def findTransaction(String trxId, Long paymentId ) {

        Transaction trx = new Transaction([trxId:trxId, paymentId:paymentId, dateCreated:new Date()])
        
        if (trx.validate()) {

            println "Sin transaccion findByTrxId: ${PoolUtils.printPoolStats()}"

            def tran = Transaction.findByTrxId(trx_id)

            println "Sin transaccion despues findByTrxId: ${PoolUtils.printPoolStats()}"

            Thread.sleep(10000)
            }
        
        return null
            }

@Transactional
    def findTransactionTransactional(String trxId, Long paymentId ) {

        Transaction trx = new Transaction([trxId:trxId, paymentId:paymentId, dateCreated:new Date()])
        
        if (trx.validate()) {

            println "Sin transaccion findByTrxId: ${PoolUtils.printPoolStats()}"

            def tran = Transaction.findByTrxId(trx_id)

            println "Sin transaccion despues findByTrxId: ${PoolUtils.printPoolStats()}"

            Thread.sleep(10000)
            }
        
        return null
            }

}
