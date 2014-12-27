<%@page import="be.kayiranga.daoImpl.FollowshipDaoImpl"%>
<%@page import="be.kayiranga.dao.FollowshipDao"%>
<%@page import="be.kayiranga.model.User"%>
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
		<header>Les utilisateurs</header>
		<ul id="allUsers">
			<c:choose>
				<c:when test="${sessionScope.user ne null}">
					<c:forEach items="${sessionScope.followees}" var="followee">
						<li>
							<div class="well well-sm">
								<img
									src="${pageContext.request.contextPath}/displayProfilePic?userId=${followee.userId}"
									class="img-thumbnail" />
								<c:out value="${followee.username}" />
								<a
									href="${pageContext.request.contextPath}/unfollow?ferId=${sessionScope.userId}&fedId=${followee.userId}"
									class="btn" style="float: right;"><c:out value="Unfollow" /></a>
							</div>
						</li>
					</c:forEach>
					<c:forEach items="${sessionScope.nonfollowees}" var="nonfollowee">
						<li>
							<div class="well well-sm">
								<img
									src="${pageContext.request.contextPath}/displayProfilePic?userId=${nonfollowee.userId}"
									class="img-thumbnail" />
								<c:out value="${nonfollowee.username}" />
								<a
									href="${pageContext.request.contextPath}/follow?ferId=${sessionScope.userId}&fedId=${followee.userId}"
									class="btn" style="float: right;"><c:out value="Follow" /></a>
							</div>
						</li>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<c:forEach var="user" items="${applicationScope.allUsers}">
						<li>
							<div class="well well-sm">
								<img
									src="${pageContext.request.contextPath}/displayProfilePic?userId=${user.userId}"
									class="img-thumbnail" />
								<c:out value="${user.username}" />
							</div>
						</li>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</ul>
	</div>
</div>