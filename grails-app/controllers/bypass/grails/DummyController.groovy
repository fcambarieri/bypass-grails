package bypass.grails

import grails.converters.JSON

class DummyController {

    def index() { }

    def testing() {
        render(text:(["message":"hola"] as JSON).toString(), status: 200, contentType:"application/json", encoding:"UTF-8")
    }

    def createDummyTransaction() {

    }
}
