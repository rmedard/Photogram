<%@page import="be.kayiranga.daoImpl.FollowshipDaoImpl"%>
<%@page import="be.kayiranga.dao.FollowshipDao"%>
<%@page import="be.kayiranga.model.User"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="col-md-3" id="leftCol">
	<div class="well" id="users-list">
		<header>Les utilisateurs</header>
		<div class="row">
			<form action="${pageContext.request.contextPath}/search" role="form"
				method="get">
				<div class="input-group">
					<input type="text" class="form-control" placeholder="Recherche"
						name="searchTxt"> <span class="input-group-btn">
						<button class="btn btn-default" type="submit">Go!</button>
					</span>
				</div>
			</form>
		</div>
		<hr>
		<div class="">
			<ul class="nav nav-stacked" id="allUsers">
				<c:choose>
					<c:when test="${sessionScope.user ne null}">
						<c:choose>
							<c:when test="${requestScope.requestSent}">
								<c:set var="followees" value="${requestScope.followees}" />
								<c:set var="nonfollowees" value="${requestScope.nonfollowees}" />
							</c:when>
							<c:otherwise>
								<c:set var="followees" value="${sessionScope.followees}" />
								<c:set var="nonfollowees" value="${sessionScope.nonfollowees}" />
							</c:otherwise>
						</c:choose>
						<c:forEach items="${followees}" var="followee">
							<li>
								<div class="">
									<a
										href="${pageContext.request.contextPath}/userDetails?selectedUserId=${followee.userId}&isFollowed=${1}"
										class="btn"> <img
										src="${pageContext.request.contextPath}/displayProfilePic?userId=${followee.userId}"
										class="img-thumbnail img-circle" /> <c:out
											value="${followee.username}" />
									</a>
									<c:url value="/unfollow" var="unfollowURL">
										<c:param name="ferid" value="${sessionScope.userId}" />
										<c:param name="fedid" value="${followee.userId}" />
									</c:url>
									<a href="${unfollowURL}" class="btn" style="float: right;"><c:out
											value="Unfollow" /></a>
								</div>
							</li>
						</c:forEach>
						<c:forEach items="${nonfollowees}" var="nonfollowee">

							<li>
								<div class="">
									<a
										href="${pageContext.request.contextPath}/userDetails?selectedUserId=${nonfollowee.userId}&isFollowed=${0}"
										class="btn"> <img
										src="${pageContext.request.contextPath}/displayProfilePic?userId=${nonfollowee.userId}"
										class="img-thumbnail img-circle" /> <c:out
											value="${nonfollowee.username}" />
									</a>

									<c:url value="/follow" var="followURL">
										<c:param name="ferid" value="${sessionScope.userId}" />
										<c:param name="fedid" value="${nonfollowee.userId}" />
									</c:url>

									<a href="${followURL}" class="btn" style="float: right;"><c:out
											value="Follow" /></a>
								</div>
							</li>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<c:forEach var="user" items="${applicationScope.allUsers}">
							<li>
								<div class="">
									<a
										href="${pageContext.request.contextPath}/userDetails?selectedUserId=${user.userId}&isFollowed=${-1}"
										class="btn"> <img
										src="${pageContext.request.contextPath}/displayProfilePic?userId=${user.userId}"
										class="img-thumbnail img-circle" /> <c:out
											value="${user.username}" />
									</a>
								</div>
							</li>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
	</div>
</div>