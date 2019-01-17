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
        <h1>Show User</h1>

        <ol class="property-list user">

            <li class="fieldcontain">
                <span id="password-label" class="property-label">Password</span>
                <div class="property-value" aria-labelledby="password-label">$2a$10$846H2FuU/C6sow9gdNJU5eG313SxkhShX1AdeEHR79g3N5quf888a</div>
            </li>

            <li class="fieldcontain">
                <span id="username-label" class="property-label">Username</span>
                <div class="property-value" aria-labelledby="username-label">username1</div>
            </li>

            <li class="fieldcontain">
                <span id="mail-label" class="property-label">Mail</span>
                <div class="property-value" aria-labelledby="mail-label">mail-1</div>
            </li>

            <li class="fieldcontain">
                <span id="tel-label" class="property-label">Tel</span>
                <div class="property-value" aria-labelledby="tel-label"></div>
            </li>

            <li class="fieldcontain">
                <span id="firstName-label" class="property-label">First Name</span>
                <div class="property-value" aria-labelledby="firstName-label">firstName</div>
            </li>

            <li class="fieldcontain">
                <span id="lastName-label" class="property-label">Last Name</span>
                <div class="property-value" aria-labelledby="lastName-label">last</div>
            </li>

        </ol>
        <form action="/user/delete/2" method="post" ><input type="hidden" name="_method" value="DELETE" id="_method" />
            <fieldset class="buttons">
                <a href="/user/edit/2" class="edit">Edit</a>
                <input class="delete" type="submit" value="Delete" onclick="return confirm('Are you sure?');" />
            </fieldset>
        </form>
    </div>
    </body>
</html>
