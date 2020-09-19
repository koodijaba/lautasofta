<#macro post post>
    <#-- @ftlvariable name="post" type="io.github.koodijaba.lautasofta.domain.Post" -->
    <#local createdAt=post.createdAt!?datetime.iso>
    <article>
        <header><h1>Id ${post.id?c}</h1> <time datetime="${createdAt?iso_local}">${createdAt}</time></header>
        <section>${post.content}</section>
    </article>
</#macro>