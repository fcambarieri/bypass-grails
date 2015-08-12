class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
        "500"(view:'/error')
        "/grails/withtransaction" (controller: "dummy" , action: [POST: "createTransactional"])
        "/grails/internalTransaction" (controller: "dummy" , action: [POST: "createInternalTransactional"])
        "/grails/transaction" (controller: "dummy" , action: [POST: "createTransaction"])
        "/grails/withTransctionalAndWithoutTransactional" (controller: "dummy" , action: [POST: "createTransactionsWithAndWithoutTransactional"])
        "/grails/withoutTransctionalAndWithTransactional" (controller: "dummy" , action: [POST: "createWithoutTransctionalAndWithTransactional"])
        "/grails/TwiceWithoutTransactional" (controller: "dummy" , action: [POST: "createTwiceWithoutTransactional"])
        "/grails/twiceWithoutTransactionalAndUseConnection" (controller: "dummy" , action: [POST: "createTwiceWithoutTransactionalAndUseConnection"])
        
	}
}
