package fr.mbds.tp

import grails.converters.JSON
import grails.converters.XML

import javax.servlet.http.HttpServletRequest

class ApiController {

    def index() { render "ok" }

    def message(){
        switch(request.getMethod()){
            case "GET":
                if(params.id) //on doit retourner une instance de message
                {
                    def messageInstance = Message.get(params.id)
                    if (messageInstance) {
                        reponseFormat(messageInstance, request)
                    }
                    else
                        response.status = 404
                }
                else //on doit retourner la liste de tous les messages
                {
                    forward action: "messages"
                }
                break
            case "POST":
                forward action:"messages"
                break
            case "DELETE":
                def messageInstance = params.id ? Message.get(params.id) : null
                if(messageInstance){
                    def userMessages = UserMessage.findAllByMessage(messageInstance)
                    userMessages.each {
                        UserMessage userMessage ->
                            userMessage.delete(flush: true)
                    }
                    messageInstance.delete(flush: true)
                    render (status: 200, text: "message effacé")
                }
                else {
                    render(status: 404, text: "message introuvable")
                }
                break
            case "PUT":
                def messageInstance = params.id ? Message.get(params.id) : null
                if(messageInstance)
                {
                    if(params.get("author.id"))
                    {
                        def authorInstance = User.get((Long)params.get("author.id"))
                        if(authorInstance)
                            messageInstance.author = authorInstance
                    }
                    if(params.messageContent)
                        messageInstance.messageContent = params.messageContent
                    if(messageInstance.save(flush: true))
                        render (status: 200, text: "Mise à jour effectuée pour le message ${messageInstance.id}")
                    else
                        render (status: 400, text: "Mise à jour effectuée pour le message ${messageInstance.id}")
                }
                else
                    render (status: 404, text: "Le message désigné est introuvable")
                break

            default:
                response.status=405
                break
        }

    }

    def user(){
        switch(request.getMethod()){
            case "GET":
                if(params.id) //on doit retourner une instance de user
                {
                    def userInstance = User.get(params.id)
                    if (userInstance) {
                        reponseFormat(userInstance, request)
                    }
                }
                else //on doit retourner la liste de tous les users
                {
                    forward(action: "users")
                }
                break
            case "POST":
                forward action:"users"
                break
            case "DELETE":
                def userInstance = params.id ? User.get(params.id) : null
                if(userInstance){
                    userInstance.isDelete = true
                    if(userInstance.save(flush: true))
                        render (status: 200, text: "utilisateur effacé")
                    else
                        render (status: 400, text: "utilisateur introuvable")
                }
                break

            case "PUT":
                def userInstance = params.id ? User.get(params.id) : null
                if(userInstance)
                {
                    if(params.password)
                        userInstance.password = params.password
                    if(userInstance.save(flush: true))
                        render (status: 200, text: "Mise à jour effectuée pour le mot de passe de ${userInstance.id}")
                    else
                        render (status: 400, text: "Mise à jour effectuée pour le mot de passe de ${userInstance.id}")

                    if(params.username)
                        userInstance.username = params.username
                    if(userInstance.save(flush: true))
                        render (status: 200, text: "Mise à jour effectuée pour le nom d'utilisateur de ${userInstance.id}")
                    else
                        render (status: 400, text: "Mise à jour effectuée pour le nom d'utilisateur de ${userInstance.id}")

                    if(params.mail)
                        userInstance.mail = params.mail
                    if(userInstance.save(flush: true))
                        render (status: 200, text: "Mise à jour effectuée pour le mail de ${userInstance.id}")
                    else
                        render (status: 400, text: "Mise à jour effectuée pour le mail de ${userInstance.id}")

                    if(params.tel)
                        userInstance.tel = params.tel
                    if(userInstance.save(flush: true))
                        render (status: 200, text: "Mise à jour effectuée pour le tel de ${userInstance.id}")
                    else
                        render (status: 400, text: "Mise à jour effectuée pour le tel de ${userInstance.id}")

                    if(params.firstName)
                        userInstance.firstName = params.firstName
                    if(userInstance.save(flush: true))
                        render (status: 200, text: "Mise à jour effectuée pour le prénom de ${userInstance.id}")
                    else
                        render (status: 400, text: "Mise à jour effectuée pour le prénom de ${userInstance.id}")

                    if(params.lastName)
                        userInstance.lastName = params.lastName

                    if(userInstance.save(flush: true))
                        render (status: 200, text: "Mise à jour effectuée pour le nom de famille de ${userInstance.id}")
                    else
                        render (status: 400, text: "Mise à jour effectuée pour le nom de famille de ${userInstance.id}")

                }
                else
                    render (status: 404, text: "L'utilisateur désigné est introuvable")
                break

            default:
                response.status=405
                break
        }

    }

    def messages(){
        switch (request.getMethod())
        {
            case "GET":
                reponseFormatList(Message.list(), request)
                break
            case "POST":
                //vérifier l'auteur
                def authorInstance = params.author.id ? User.get(params.author.id) : null
                def messageInstance
                if(authorInstance)
                {
                    messageInstance = new Message(author: authorInstance, messageContent: params.messageContent)
                    if(messageInstance.save(flush: true))
                        render (status: 201)
                }


                if (response.status != 201)
                    response.status = 400
                break;
        }
    }

    def users(){
        switch (request.getMethod())
        {
            case "GET":
                reponseFormatList(User.list(), request)
                break
            case "POST":

                def userInstance
                //créer un user
                    userInstance = new User(password: params.password, username: params.username, mail: params.mail, tel: params.tel, firstName: params.firstName,
                    lastName: params.lastName)
                         if(userInstance.save(flush: true))
                            render (status: 201)

                         if (response.status != 201)
                            response.status = 400
                break
        }
    }

    def messageToUser(){
        switch(request.getMethod()) {
            case "POST":
                if(params.messageid){
                    def messageIns = Message.get(params.messageid)
                    if(messageIns){
                        def messageInstance = params.messageid ? Message.get(params.message.id) : null
                        if (messageInstance) {
                            if(params.userid){
                                def userIns = User.get(params.userid)
                                if(userIns){
                                    def userInstance = params.userid ? User.get(params.user.id ): null
                                    if (userInstance) {
                                        def userMessageInstance
                                        userMessageInstance = new UserMessage(user: userInstance, message: messageInstance)
                                        if (userMessageInstance.save(flush: true))
                                            render(status: 201, text: "Message bien adressé")
                                        else
                                            render(status: 400, text:"Erreur")
                                    }

                                }
                            }

                    }
                }



                }
                else {
                     def authorInstance = params.author.id ? User.get(params.author.id ): null
                     if (authorInstance) {
                         def messageInstance = new Message(author: authorInstance, messageContent: params.messageContent)
                         if (messageInstance.save(flush: true))
                             render(status: 201)
                     }
                     if (response.status != 201)
                         response.status = 400

                break
        }
    }
    }
    def reponseFormat(Object instance, HttpServletRequest request) {
        println "lolololol"
        switch ( request.getHeader ( "Accept" ) ) {
        case "text/xml":
        render instance as XML
        break
        case "text/json":
        render instance as JSON
        break
        }

    }

    def reponseFormatList(List list, HttpServletRequest request){
        switch(request.getHeader("Accept")){
            case "text/xml":
                render list as XML
                break
            case "text/json":
                render list as JSON
                break
        }
    }


}


/* 1. GET        message/             liste de messages           200/405/301
                 messages/

   2. POST          ''                ajouter à la table          201/400/301

   3. GET       message/id            retourne une instance       200/404

   4. POST      message               créer l'instance            201/400

   5. DELETE    message/id            supprime l'instance         200/404

   6. PUT          ''                 update l'instance           200/404

 */