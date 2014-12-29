<%@page import="be.kayiranga.model.User"%>
<%@page import="be.kayiranga.model.Image"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/pages/public/templates/header.jsp" />
<div class="container">
	<div class="row">
		<jsp:include page="/pages/public/templates/sidebar.jsp" />
		<c:choose>
			<c:when test="${sessionScope.user ne null}">
				<div class="col-md-9">
					<div class="row">
						<div class="col-md-3">
							<div class="row">
								<div class="photo-thumbnail">
									<a
										href="${pageContext.request.contextPath}/imagedatadisplay?imageId=-1">
										<img
										src="${pageContext.request.contextPath}/displayProfilePic?userId=${user.userId}"
										class="img-thumbnail" />
									</a>
								</div>
							</div>
						</div>
						<div class="col-md-6">
							<h3>Nom: ${user.name}</h3>
							<h3>Prénom: ${user.postname}</h3>
							<h3>Emai: ${user.email}</h3>
							<c:choose>
								<c:when test="${user.name eq null}">
									<c:set var="editOption" value="false" />
								</c:when>
								<c:otherwise>
									<c:set var="editOption" value="true" />
								</c:otherwise>
							</c:choose>
							<a href="<c:url value="/pages/public/editAddUser.jsp"/>"
								class="btn btn-default">Modifier</a>
						</div>
					</div>
					<jsp:include page="/pages/private/templates/mesphotos.jsp"
						flush="true"></jsp:include>
					<jsp:include page="/pages/private/templates/mesamis.jsp"
						flush="true"></jsp:include>
				</div>
			</c:when>
			<c:otherwise>
				<div class="col-md-9">
					<jsp:forward page="/pages/public/login.jsp" />
				</div>
			</c:otherwise>
		</c:choose>
	</div>
</div>
<jsp:include page="/pages/public/templates/footer.jsp" />