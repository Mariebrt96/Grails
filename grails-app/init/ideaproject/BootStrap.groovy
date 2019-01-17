package ideaproject

import fr.mbds.tp.Message
import fr.mbds.tp.Role
import fr.mbds.tp.User
import fr.mbds.tp.UserMessage
import fr.mbds.tp.UserRole

class BootStrap {

    def init = { servletContext ->
        def userAdmin = new User(username: "admin", password: "secret", isDelete: false, firstName: "admin", lastName: "admin", mail: "admin").save(flush: true, failOnError: true)
        def roleAdmin = new Role(authority:'ROLE_ADMIN' ).save(flush: true, failOnError : true)
        UserRole.create(userAdmin, roleAdmin, true)
        (1..50).each {
            def userInstance = new User(username: "username${it}", isDelete: false, password: "password", firstName: "firstName", lastName: "last", mail: "mail-${it}").save(flush: true, failOnError: true)
            new Message(messageContent: "lala", author: userInstance, isDelete: false).save(flush: true, failOnError: true)
        }

        Message.list().each {
            Message messageInstance ->
            User.list().each {
                User userInstance ->
                    UserMessage.create(userInstance, messageInstance, true)
            }
        }
    }
    def destroy = {
    }
}
