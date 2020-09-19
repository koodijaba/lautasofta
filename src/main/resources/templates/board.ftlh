<#import "/spring.ftl" as spring>
<#import "macros/layout.ftl" as layout>
<#import "macros/post.ftl" as post>
<#import "macros/bootstrap.ftl" as bs>

<@layout.layout title=board.name>
    <section>
        <form action="" method="POST">
            <div class="form-group">
                <label for="content"><@spring.message code="post.content"/></label>
                <@spring.formTextarea path="thread.content" attributes="class='form-control'"/>
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button type="submit" class="btn btn-primary"><@spring.message code="submit"/></button>
        </form>
    </section>
    <section>
        <#list threads.content as thread>
            <div class="card mt-4">
                <div class="card-body">
                    <h5 class="card-title">
                        <a href="${springMacroRequestContext.contextPath}/${board.name?url}/${thread.id?c?url}">
                            ${thread.content?truncate(20, "...")}
                        </a>
                    </h5>
                    <h6 class="card-subtitle text-muted mb-2">${thread.createdAt!?datetime.iso}</h6>
                    <p class="card-text">
                        ${thread.content}
                    </p>
                </div>
                <img src="https://via.placeholder.com/700" class="card-img-top" alt="...">

            </div>
            <#if thread?is_last && !threads.last>
                <div class="my-4 text-center">
                    <a href="?before=${thread.modifiedAt}" ><@spring.message code="loadMore"/></a>
                </div>
            </#if>
        </#list>
    </section>
</@layout.layout>