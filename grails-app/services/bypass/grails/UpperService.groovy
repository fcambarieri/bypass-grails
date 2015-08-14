package bypass.grails

import grails.transaction.Transactional
import mercadolibre.utils.PoolUtils



class UpperService {

	
	TransactionService transactionService

    def CallWithTransctionalAndWithoutTransactional(trx_id, payment_id) {
    	println "[UpperService]Al entrar: " + PoolUtils.printPoolStats()
        
        def id = transactionService.withTransaction(trx_id, payment_id)
		
		println "[UpperService]Fuera del @Transactional: " + PoolUtils.printPoolStats()
	
		def id2 = transactionService.createTransaction(trx_id, payment_id)

		println "[UpperService]Fuera del No Transactional: " + PoolUtils.printPoolStats()

		return id2
    }

        def CallWithoutTransctionalAndWithTransactional(trx_id, payment_id) {
    	println "[UpperService]Al entrar: " + PoolUtils.printPoolStats()
        
        def id = transactionService.createTransaction(trx_id, payment_id)
		
		println "[UpperService]Fuera del No Transactional: " + PoolUtils.printPoolStats()
	
		def id2 = transactionService.withTransaction(trx_id, payment_id)
		println "[UpperService]Fuera del @Transactional: " + PoolUtils.printPoolStats()


	
		return id2
    }

        def CallTwiceWithoutTransactional(trx_id, payment_id) {
    	println "[UpperService]Al entrar: " + PoolUtils.printPoolStats()
        
        def id = transactionService.createTransaction(trx_id, payment_id)
		
		println "[UpperService]Fuera del primer no Transactional: " + PoolUtils.printPoolStats()
	
		def id2 = transactionService.createTransaction(trx_id, payment_id)
		println "[UpperService]Fuera del segundo Transactional: " + PoolUtils.printPoolStats()

		return id2
    }


        def CallWithoutTransactionalAndUseConnection(trx_id, payment_id) {
    	println "[UpperService]Al entrar: " + PoolUtils.printPoolStats()
        
        def id = transactionService.createTransaction(trx_id, payment_id)
		
		println "[UpperService]Fuera del @Transactional: " + PoolUtils.printPoolStats()

		def tran = Transaction.findByTrxId(trx_id)

		println "[UpperService]Luego del findByTrxId: " + PoolUtils.printPoolStats()

		return id
    }


    def CallUseConnectionAndWithoutTransactional(trx_id, payment_id) {
    
    	println "[UpperService]Al entrar: " + PoolUtils.printPoolStats()

    	def tran = Transaction.findByTrxId(trx_id)

		println "[UpperService]Luego del findByTrxId: " + PoolUtils.printPoolStats()
    
        def id = transactionService.createTransaction(trx_id, payment_id)
		
		println "[UpperService]Fuera del @Transactional: " + PoolUtils.printPoolStats()

		return id
    }

        def CallUseConnectionAndWithTransactional(trx_id, payment_id) {
    
    	println "[UpperService]Al entrar: " + PoolUtils.printPoolStats()

    	def tran = Transaction.findByTrxId(trx_id)

		println "[UpperService]Luego del findByTrxId: " + PoolUtils.printPoolStats()
    
        def id = transactionService.withTransaction(trx_id, payment_id)
		
		println "[UpperService]Fuera del @Transactional: " + PoolUtils.printPoolStats()

		return id
    }

     def CallFindTransactionAndWithTransactional(trx_id, payment_id) {
    
    	println "[UpperService]Al entrar: " + PoolUtils.printPoolStats()

    	def tran = transactionService.findTransaction(trx_id)

		println "[UpperService]Luego del findTransaction: " + PoolUtils.printPoolStats()
    
        def id = transactionService.withTransaction(trx_id, payment_id)
		
		println "[UpperService]Fuera del @Transactional: " + PoolUtils.printPoolStats()

		return id
    }

        def CallFindTransactionalAndWithTransactional(trx_id, payment_id) {
    
    	println "[UpperService]Al entrar: " + PoolUtils.printPoolStats()

    	def tran = transactionService.findTransactionTransactional(trx_id)

		println "[UpperService]Luego del findTransactionTransactional: " + PoolUtils.printPoolStats()
    
        def id = transactionService.withTransaction(trx_id, payment_id)
		
		println "[UpperService]Fuera del @Transactional: " + PoolUtils.printPoolStats()

		return id
    }

}
