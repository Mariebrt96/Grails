<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#show-user" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="show-user" class="content scaffold-show" role="main">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <ol class="property-list user">

                <li class="fieldcontain">
                    <span id="password-label" class="property-label">Mot de passe</span>
                    <div class="property-value" aria-labelledby="password-label">${user.password}</div>
                </li>

                <li class="fieldcontain">
                    <span id="username-label" class="property-label">Nom d'utilisateur</span>
                    <div class="property-value" aria-labelledby="username-label">${user.username}</div>
                </li>

                <li class="fieldcontain">
                    <span id="mail-label" class="property-label">Adresse mail</span>
                    <div class="property-value" aria-labelledby="mail-label">${user.mail}</div>
                </li>

                <li class="fieldcontain">
                    <span id="tel-label" class="property-label">Numéro de téléphone</span>
                    <div class="property-value" aria-labelledby="tel-label">${user.tel}</div>
                </li>

                <li class="fieldcontain">
                    <span id="firstName-label" class="property-label">Prénom</span>
                    <div class="property-value" aria-labelledby="firstName-label">${user.firstName}</div>
                </li>

                <li class="fieldcontain">
                    <span id="lastName-label" class="property-label">Nom</span>
                    <div class="property-value" aria-labelledby="lastName-label">${user.lastName}</div>
                </li>

            </ol>
            <g:form resource="${this.user}" method="DELETE">
                <fieldset class="buttons">
                    <g:link class="edit" action="edit" resource="${this.user}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                    <input class="delete" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
