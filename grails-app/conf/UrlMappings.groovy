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
        "/grails/internalTransaction" (controller: "dummy" , action: [POST: "createTransactional"])
        "/grails/transaction" (controller: "dummy" , action: [POST: "createTransaction"])
	}
}
