<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="templates/header.jsp" />
<div class="container">
	<c:set var="owner" value="${requestScope.owner}" />
	<c:set var="isfollowed" value="${requestScope.isFollowed}" />
	<div class="row">
		<jsp:include page="/pages/public/templates/sidebar.jsp" />
		<div class="col-md-9">
			<div class="row">
				<div class="col-md-3">
					<div class="row">
						<div class="photo-thumbnail">
							<a href=""> <img
								src="${pageContext.request.contextPath}/displayProfilePic?userId=${owner.userId}"
								class="img-thumbnail" />
							</a>
						</div>
					</div>
				</div>
				<div class="col-md-6 user-details" >
					<label>Nom:</label>
					<c:out value="${owner.name}" />
					<br> <label>Prénom:</label>
					<c:out value="${owner.postname}" />
					<br>
					<label>Pseudonyme:</label>
					<c:out value="${owner.username}" />
					<br> <label>Email:</label>
					<c:out value="${owner.email}" />
					<br>
				</div>
			</div>
			<c:choose>
				<c:when test="${requestScope.isFollowed eq 1}">
					<jsp:include page="/pages/private/templates/mesphotos.jsp"
						flush="true" />
					<hr>
					<c:url value="/unfollow" var="unfollowURL">
						<c:param name="ferid" value="${sessionScope.userId}" />
						<c:param name="fedid" value="${owner.userId}" />
					</c:url>
					<a href="${unfollowURL}" class="btn btn-primary"
						style="float: right;"><c:out value="Unfollow" /></a>
				</c:when>
				<c:when test="${requestScope.isFollowed eq 0}">
					<hr>
					<c:url value="/follow" var="followURL">
						<c:param name="ferid" value="${sessionScope.userId}" />
						<c:param name="fedid" value="${owner.userId}" />
					</c:url>
					<a href="${followURL}" class="btn btn-primary"
						style="float: right;"><c:out value="Follow" /></a>
				</c:when>
				<c:otherwise>
					<hr>
					<a href="${pageContext.request.contextPath}/pages/public/login.jsp"
						class="btn btn-primary">Login to follow</a>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>
<jsp:include page="templates/footer.jsp" />