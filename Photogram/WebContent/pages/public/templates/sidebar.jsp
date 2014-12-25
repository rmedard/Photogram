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
			<c:forEach var="user" items="${applicationScope.allUsers}">
				<c:choose>
					<c:when test="${sessionScope.user ne user}">
						<li>
							<div class="well well-sm">
								<img
									src="${pageContext.request.contextPath}/displayProfilePic?userId=${user.userId}"
									class="img-thumbnail" hidden="true" />
								<c:out value="${user.username}" />
								<c:set var="follower" value="sessionScope.user" />
								<c:set var="followerId" value="${follower.userId}"/>
								<c:set var="followedId" value="${user.userId}" />
								<%
									User follower = (User)pageContext
														.getAttribute("follower");
												User followed = (User) pageContext
														.getAttribute("followed");
												FollowshipDao fdao = new FollowshipDaoImpl();
												Boolean follows = fdao.checkFollowship(follower,
														followed);
												pageContext.setAttribute("follows", follows);
								%>
								<c:choose>
									<c:when test="follows">
										<a href="" class="btn" style="float: right;"><c:out
												value="Unfollow" /></a>
									</c:when>
									<c:otherwise>
										<a href="" class="btn" style="float: right;"><c:out
												value="Follow" /></a>
									</c:otherwise>
								</c:choose>

							</div>
						</li>
					</c:when>
				</c:choose>
			</c:forEach>
		</ul>
	</div>
</div>