<%@page import="be.kayiranga.model.User"%>
<%@page import="be.kayiranga.model.Image"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="/pages/public/templates/header.jsp" />
<div class="container">
	<div class="row">
		<jsp:include page="/pages/public/templates/sidebar.jsp" />
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
			<div class="row">
				<fieldset>
					<legend>Mes Photos</legend>
					<c:forEach var="img" items='${sessionScope["images"]}'>
						<div class="col-md-3">
							<div class="photo-thumbnail">
								<a
									href="${pageContext.request.contextPath}/imagedatadisplay?imageId=${img.imageId}">

									<img
									src="${pageContext.request.contextPath}/displayImg?imageId=${img.imageId}"
									class="img-thumbnail" />
								</a>
							</div>
						</div>
					</c:forEach>
				</fieldset>
			</div>
			<div class="row">
				<fieldset>
					<legend>Ajouter des images</legend>
					<form action="${pageContext.request.contextPath}/uploadImage"
						method="post" enctype="multipart/form-data">
						<input type="file" name="file" accept="image/*" /> <br /> <input
							type="submit" value="Télécharger" />
					</form>
				</fieldset>
			</div>
		</div>
	</div>

</div>
<jsp:include page="/pages/public/templates/footer.jsp" />