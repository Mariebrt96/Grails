<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'message.label', default: 'Message')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#show-message" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="show-message" class="content scaffold-show" role="main">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            %{--<f:display bean="message" />--}%
            <ol class="property-list">
            <li class="fieldcontain">
                <span id="messageContent-label" class="property-label">Message</span>
                <div class="property-value" aria-labelledby="messageContent-label">${message.messageContent}</div>
            </li>

            <li class="fieldcontain">
                <span id="author-label" class="property-label">Auteur</span>
                <div class="property-value" aria-labelledby="author-label">
                    <g:link controller="user" action="show" id="${message.author.id}">${message.author.username}</g:link>
                </div>
            </li>

                <li class="fieldcontain">
                    <span id="author-label" class="property-label">Destinataires</span>
                    <div class="property-value" aria-labelledby="author-label">
                        <g:each in="${userList}" var="user">
                            ${user.username}
                        </g:each>
                    </div>
                </li>
            </ol>
            <g:form resource="${this.message}" method="DELETE">
                <fieldset class="buttons">
                    <input class="delete" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
