<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>测试页面</title>
	</head>
	<body>
		学生信息
		<table border="1">
			<tr>
				<th>序号</th>
				<th>学号</th>
				<th>姓名</th>
				<th>年龄</th>
				<th>地址</th>
			</tr>
			<#list students as student>
				<#if student?index % 2==0>
				<tr bgcolor="red">
				<#else>
				<tr bgcolor="blue">
				</#if>
					<td>${student?index}</td>
					<td>${student.id}</td>
					<td>${student.name}</td>
					<td>${student.age}</td>
					<td>${student.address}</td>
				</tr>
			</#list>
		</table>
		<br />
		日期类型处理: ${date?string("yyyy/MM/dd HH:mm:ss")}
		<br />
		null值处理: ${val!"默认值"}
		<br />
		使用if判断null值：
		<#if val??>
		val有值
		<#else>
		val值为null
		</#if>
		<br />
		include标签测试：
		<#include "hello.ftl">
	</body>
</html>