<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="/pages/public/templates/header.jsp" />
<div class="container">
	<div class="row">
		<jsp:include page="/pages/public/templates/sidebar.jsp" />
		<div class="col-md-9">
			<div class="col-md-3">
				<div class="row">
					<h4>Profile Image here</h4>
				</div>
			</div>
			<div class="col-md-6">
				<h3>Nom: ${user.name}</h3>
				<h3>Prénom: ${user.postname}</h3>
				<h3>Emai: ${user.email}</h3>
				Other details <a href="<c:url value="/editAddUser"/>"
					class="btn btn-default">Modifier</a>
			</div>
		</div>
	</div>
	<div class="row">
		<h1>Mes Photos</h1>
		<c:forEach items="${images}" var="img">
			<div class="col-md-3">
				<img src="displayImg?imageId=${img.imageId}" class="img-thumbnail">
				<%-- <img src="<c:url value="/displayImg?imageId=${img.imageId}"/>"
					class="img-thumbnail"> --%>
			</div>
		</c:forEach>
	</div>
	<div class="row">
		<h1>Ajouter des images:</h1>
		<form action="${pageContext.request.contextPath}/uploadImage"
			method="post" enctype="multipart/form-data">
			<input type="file" name="file" accept="image/*" /> <br /> <input
				type="submit" value="Télécharger" />
		</form>
	</div>
</div>
<jsp:include page="/pages/public/templates/footer.jsp" />