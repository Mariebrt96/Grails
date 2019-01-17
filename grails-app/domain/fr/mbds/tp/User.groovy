package fr.mbds.tp

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
@EqualsAndHashCode(includes='username')
@ToString(includes='username', includeNames=true, includePackage=false)
class User implements Serializable {

    private static final long serialVersionUID = 1

    String username
    String password
    String firstName
    String lastName
    String mail
    String tel
    Date dob
    boolean isDelete

    Date dateCreated
    Date lastUpdated

    boolean enabled = true
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    Set<Role> getAuthorities() {
        (UserRole.findAllByUser(this) as List<UserRole>)*.role as Set<Role>
    }

    static constraints = {
        password nullable: false, blank: false, password: true
        username nullable: false, blank: false, unique: true
        mail nullable: false, bank: false, unique: true
        dob nullable: true
        tel nullable: true
        firstName nullable: false, bank:false
        lastName nullable: false, bank: false
        isDelete nullable: false, bank: false
    }

    static mapping = {
	    password column: '`password`'
    }
}
