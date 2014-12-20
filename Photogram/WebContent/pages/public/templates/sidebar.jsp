<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="col-md-3" id="leftCol">
	<div class="well">
		<ul class="nav nav-stacked" id="sidebar">
			<li><a href="#sec1">Section 1</a></li>
			<li><a href="#sec2">Section 2</a></li>
			<li><a href="#sec3">Section 3</a></li>
			<li><a href="#sec4">Section 4</a></li>
		</ul>
	</div>
	<div class="well" id="users-list">
		<ul>
			<c:forEach var="user" items="${applicationScope['allusers']}">
				<li><img
					src="${pageContext.request.contextPath}/displayProfilePic?userId=${user.userId}"
					class="img-thumbnail" /> <c:out value="${user.username}" /></li>
			</c:forEach>
		</ul>
	</div>
</div>