<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<body>
    <h1>Liste des messages</h1>
    <ul>
        <c:forEach var="item" items="${message}">
            <li>${item}</li>
        </c:forEach>
    </ul>
</body>
</html>