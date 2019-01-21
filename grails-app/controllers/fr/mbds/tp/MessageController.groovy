package fr.mbds.tp

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

//@Secured('ROLE_ADMIN')
class MessageController {

    MessageService messageService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Message.findAllByIsDelete(false,params), model:[userCount: messageService.count()]
    }

    def show(Long id) {
        def messageInstance = Message.get(id)
        def userMessageList = UserMessage.findAllByMessage(messageInstance)
        def userList = userMessageList.collect{it.user}
        respond messageInstance,model:[userList:userList]

    }

    def create() {

        respond new Message(params)
    }

    def save(Message message) {
        println params
        if (message == null) {
            notFound()
            return
        }

        try {
            messageService.save(message)
        } catch (ValidationException e) {
            respond message.errors, view: 'create'
            return
        }

        if (params.user != "" && params.role == ""){
            def userInstance = User.get(params.user)
            UserMessage.create(userInstance, message, true)
        }

        if(params.user == "" && params.role != "") {
            def roleInstance = Role.get(params.role)
            if (roleInstance) {
                def userRoleInstance = UserRole.findAllByRole(roleInstance)
                (userRoleInstance).each {
                    def userInstance = User.get(it.user.id)
                    if (userInstance) {
                        UserMessage.create(userInstance, message, true)
                    }
                }

            }
        }
        if(params.user != "" && params.role != "") {
            def roleInstance = Role.get(params.role)
            def i
            i=0
            if (roleInstance) {
                def userRoleInstance = UserRole.findAllByRole(roleInstance)
                def desInstance = User.get(params.user)
                (userRoleInstance).each {
                        def userInstance = User.get(it.user.id)
                        if (userInstance) {
                            UserMessage.create(userInstance, message, true)
                            if (userInstance == desInstance)
                                i++
                        }

                }
                if (i == 0)
                    UserMessage.create(desInstance, message, true)
            }
        }

        request.withFormat {
            form multipartForm {
                flash.message = "Le message a été correctement créé"
                redirect message
            }
            '*' { respond message, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond messageService.get(id)
    }

    def update(Message message) {
        if (message == null) {
            notFound()
            return
        }

        try {
            messageService.save(message)
        } catch (ValidationException e) {
            respond message.errors, view:'edit'
            return
        }





        //Persister l'instance UserMessage nouvellement créee

        if(params.test){ //Récupérer l'id du destinataire est instancé
            def userInstance = User.get(params.test) //Créer une instance de UserMessage correspondant à l'envoi de ce message
            if(userInstance){
                if(UserMessage.findByUserAndMessage(userInstance, message)){
                    flash.message = "Le message a déjà été envoyé à l'utilisateur"
                }
                else {
                    UserMessage.create(userInstance, message, true)
                    flash.message = "Le message ${message.id} a été correctement mis à jour"
                }

            }
        }


        /*Si groupe spécifié :
        * Récupérer l'instance de Role désigné
        * Créer un nouveau UserMessage pour tous les utilisateurs dudit Groupe*/


        request.withFormat {
            form multipartForm {
                flash.message = "Le message a été correctement créé"
                redirect message
            }
            '*'{ respond message, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }


        def messageInstance = Message.get(id)

        messageInstance.isDelete = true

        messageInstance.save(flush: true)


        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'message.label', default: 'Message'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'message.label', default: 'Message'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
