<#import "/spring.ftl" as spring>
<#import "macros/layout.ftl" as layout>
<#import "macros/post.ftl" as post>
<#import "macros/bootstrap.ftl" as bs>

<@layout.layout title=thread.content?truncate(20, "...")>
    <main>
        <section>
            <form action="" method="POST">
                <div class="form-group">
                    <label for="content"><@spring.message code="post.content"/></label>
                    <@spring.formTextarea path="reply.content" attributes="class='form-control'"/>
                </div>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button type="submit" class="btn btn-primary"><@spring.message code="submit"/></button>
            </form>
        </section>
        <section>
            <@bs.card; slot>
                <#switch slot>
                    <#case "header">
                        <a href="${springMacroRequestContext.contextPath}/${board.name?url}/${thread.id?c?url}">
                            ${thread.content?truncate(20, "...")}
                        </a>
                        <#break>
                    <#case "body">
                        <@post.post post=thread/>
                        <#list thread.replies as reply>
                            <section class="ml-4">
                                <@post.post post=reply/>
                            </section>
                        </#list>
                        <#break>
                </#switch>
            </@bs.card>
        </section>
    </main>
</@layout.layout>