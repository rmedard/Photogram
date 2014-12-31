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
						<div class="col-md-6 user-details">
							<label>Nom:</label>
							<c:out value="${user.name}" />
							<br> <label>Prénom:</label>
							<c:out value="${user.postname}" />
							<br> <label>Email:</label>
							<c:out value="${user.email}" />
							<br>
							<c:choose>
								<c:when test="${user.name eq null}">
									<c:set var="editOption" value="false" />
								</c:when>
								<c:otherwise>
									<c:set var="editOption" value="true" />
								</c:otherwise>
							</c:choose>
							<div class="btn-group">
								<a href="<c:url value="/pages/public/editAddUser.jsp"/>"
									class="btn btn-default">Modifier</a> <a href="#"
									class="btn btn-default" data-toggle="modal"
									data-target="#confirmDelete">Supprimer</a>
							</div>
							<div class="modal fade" id="confirmDelete" tabindex="-1"
								role="dialog" aria-labelledby="confirmDelete" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal"
												aria-hidden="true">&times;</button>
											<h4 class="modal-title" id="myModalLabel">Confirmez</h4>
										</div>
										<div class="modal-body">
											<h3>Voulez-vous supprimer votre compte?</h3>
										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-default"
												data-dismiss="modal">Fermer</button>
											<a
												href="${pageContext.request.contextPath}/deleteUser?userId=${user.userId}"
												class="btn btn-primary">Supprimer</a>
										</div>
									</div>
								</div>
							</div>
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