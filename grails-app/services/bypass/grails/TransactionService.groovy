package bypass.grails

import grails.transaction.Transactional


class TransactionService {

    def createTransaction(String trxId, Long paymentId ) {

        Transaction trx = new Transaction([trxId:trxId, paymentId:paymentId, dateCreated:new Date()])

        if (trx.validate()) {

            trx.save(flush:true)
        }

        return trx.id
    }

    def testCreationIdle(long time) {
        Transaction.withNewSession {
            Thread.sleep(time)
        }

        return "ok"
    }

    def tellMe(String msg) {
        return ["receive":msg]
    }
}
