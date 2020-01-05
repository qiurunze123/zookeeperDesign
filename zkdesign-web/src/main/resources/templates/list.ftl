<!DOCTYPE html>
<html lang="en">
<head>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.3.1/semantic.min.css" rel="stylesheet">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.3.1/semantic.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.form/3.51/jquery.form.min.js"></script>
    <meta charset="UTF-8">
    <title>集群管理系统</title>
</head>
<body>


<div class="ui container ">
    <h1 class="ui center aligned header" style="margin-top: 20px">
       集群管理系统
    </h1>
    <table class="ui  green  table">
        <thead>
        <tr>
            <th>pid</th>
            <th>ip</th>
            <th>CPU负载</th>
            <th>占用内存</th>
            <th>剩余内存</th>
        </tr>
        </thead>
        <tbody>
        <#list items as item>
        <tr>
            <td>${item.pid!}</td>
            <td>${item.ip}</td>
            <td>${item.cpu}</td>
            <td>${item.usedMemorySize}MB</td>
            <td>${item.usableMemorySize}MB</td>
        </tr>
        </#list>
        </tbody>
    </table>
</div>


</body>
</html>