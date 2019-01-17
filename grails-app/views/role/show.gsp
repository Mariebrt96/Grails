<%@ page import="fr.mbds.tp.User" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'role.label', default: 'Role')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#show-role" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="show-role" class="content scaffold-show" role="main">
            <h1>Show Role</h1>

            <ol class="property-list role">

                <li class="fieldcontain">
                    <span id="authority-label" class="property-label">Authority</span>
                    <div class="property-value" aria-labelledby="authority-label">ROLE_ADMIN</div>
                </li>

                <li class="fieldcontain">
                    <span id="user-name-label" class="property-label">User</span>
                    <div class="property-value" aria-labelledby="user-name-label">
                        <g:each in="${userList}" var="user">
                            ${user.username + "" + user.lastName}
                        </g:each>
                    </div>
                </li>
            </ol>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>

            <g:form resource="${this.role}" method="DELETE">
                <fieldset class="buttons">
                    <g:link class="edit" action="edit" resource="${this.role}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                    <input class="delete" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
