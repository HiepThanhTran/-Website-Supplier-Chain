<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<aside class="sidebar">
    <div class="sidebar-logo">Danh sách các bảng</div>

    <ul class="sidebar-nav">
        <c:forEach var="entity" items="${entities}">
            <c:url value="/admin/${entity['key']}" var="url"/>
            <li class="sidebar-item">
                <a href="${url}" class="sidebar-link">${entity.value}</a>
            </li>
        </c:forEach>
    </ul>
</aside>