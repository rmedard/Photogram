<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row">
	<fieldset>
		<legend>Mes Photos</legend>
		<c:forEach var="img" items='${sessionScope["images"]}'>
			<div class="col-md-3">
				<div class="photo-thumbnail">
					<a
						href="${pageContext.request.contextPath}/imagedatadisplay?imageId=${img.imageId}&owner=${true}">
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