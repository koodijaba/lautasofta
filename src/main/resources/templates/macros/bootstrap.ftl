<#macro card>
    <div class="card mt-4">
        <div class="card-header">
            <#nested "header">
        </div>
        <div class="card-body">
            <p class="card-text">
                <#nested "body">
            </p>
        </div>
    </div>
</#macro>