<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>환전 페이지</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/styles.css">
    <script src="${pageContext.request.contextPath}/resources/script.js"></script>
</head>
<body>
<h1>환전 기능</h1>
<form id="conversionForm">
    <label for="points">포인트 입력:</label>
    <input type="number" id="points" name="points" required>
    <button type="submit">환전</button>
</form>
<div id="result"></div>
</body>
</html>
