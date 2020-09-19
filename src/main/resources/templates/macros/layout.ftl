<#import "/spring.ftl" as spring>

<#assign defaultTitle><@spring.message code="title"/></#assign>

<#macro layout title=defaultTitle>
    <!doctype html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>${title}</title>
        <link rel="stylesheet" href="${springMacroRequestContext.contextPath}/static/css/index.css">
    </head>
    <body>
        <div class="container">
            <header>
                <nav>
                    <ul class="list-inline">
                        <#list boards as board>
                            <li class="list-inline-item"><a href="${springMacroRequestContext.contextPath}/${board.name?url}">${board.name?url}</a></li>
                        </#list>
                    </ul>
                </nav>
            </header>
            <main>
                <#nested>
            </main>
        </div>
    </body>
    </html>
</#macro>