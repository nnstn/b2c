<html>
<head>
   <title>${titlet!"title为空取默认值"}</title>
<body>
<#include "/first.ftl">
<div>${stu0.num!}--${stu0.name!"默认值"}--${stu0.hobby!} </div>
<table border='2'> 
<#list stu0.child as s>
<#if s_index%2==0 &&s_index!=2 >
<tr style="color:red">
	<td>${s_index}</td><td>${s.num}</td><td>${s.name}</td>
</tr>
<#else>
<tr style="">
	<td>${s_index}</td><td>${s.num}</td><td>${s.name}</td>
</tr>
</#if>
</#list>
</table>

${curDate?date}<br/>
${curDate?time}<br/>
${curDate?datetime}<br/>
${curDate?string("yyyy/MM/dd HH:mm:ss")}<br/>

</body>
</html>