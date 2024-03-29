package fr.mbds.tp

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class RoleController {

    RoleService roleService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Role.findAllByIsDelete(false,params), model:[userCount: roleService.count()]
    }

    def show(Long id) {
        def userRoleInstance = Role.get(id)
        def roleInstance = UserRole.findAllByRole(userRoleInstance)
        def listInstance = roleInstance.collect{it.user}

       /* respond roleService.get(id)*/
        respond userRoleInstance, model:[userList:listInstance]
    }

    def create() {
        respond new Role(params)
    }

    def save(Role role) {
        if (role == null) {
            notFound()
            return
        }

        try {
            //créer un userrole, récupérer le user
            roleService.save(role)
        } catch (ValidationException e) {
            respond role.errors, view:'create'
            return
        }

        if (params.user !=""){
            (params.user).each{
                def userInstance = User.get(it)
                UserRole.create(userInstance, role, true)
            }
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'role.label', default: 'Role'), role.id])
                redirect role
            }
            '*' { respond role, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond roleService.get(id)
    }

    def update(Role role) {
        if (role == null) {
            notFound()
            return
        }

        try {
            roleService.save(role)
        } catch (ValidationException e) {
            respond role.errors, view:'edit'
            return
        }


        (params.user).each{
                def userInstance = User.get(it)
                UserRole.create(userInstance, role, true)
            }


        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'role.label', default: 'Role'), role.id])
                redirect role
            }
            '*'{ respond role, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        def roleInstance = Role.get(id)

        roleInstance.isDelete = true

        roleInstance.save(flush: true)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'role.label', default: 'Role'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'role.label', default: 'Role'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
