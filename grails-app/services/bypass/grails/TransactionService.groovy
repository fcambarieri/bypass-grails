package bypass.grails

import grails.transaction.Transactional


class TransactionService {

    def createTransaction(String trxId, Long paymentId ) {

        Transaction trx = new Transaction([trxId:trxId, paymentId:paymentId, dateCreated:new Date()])

        if (trx.validate()) {

            println printStackTrace()

            trx.save(flush:true)

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
        Transaction.withTransaction {
            trx = new Transaction([trxId:trxId, paymentId:paymentId, dateCreated:new Date()])

            if (trx.validate()) {

                println printStackTrace()

                trx.save()

                Thread.sleep(10000)
            }

        }

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
        Transaction trx = new Transaction([trxId:trxId, paymentId:paymentId, dateCreated:new Date()])

        if (trx.validate()) {

            printStackTrace()

            trx.save()
        }

        Thread.sleep(10000)

        return trx.id
    }

    def tellMe(String msg) {
        return ["receive":msg]
    }
}
