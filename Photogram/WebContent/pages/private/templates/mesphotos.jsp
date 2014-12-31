<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row">
	<c:choose>
		<c:when test="${requestScope.ownerId ne null }">
			<c:set var="imgs" value="${sessionScope.followeeImages}" />
			<fieldset>
				<legend>Les photos de ${requestScope.username}</legend>
				<c:forEach var="img" items="${imgs}">
					<c:if test="${img.ownerId eq requestScope.ownerId and img.publicPic}">
						<div class="col-md-3">
							<div class="photo-thumbnail">
								<a
									href="${pageContext.request.contextPath}/imagedatadisplay?imageId=${img.imageId}&owner=${false}">
									<img
									src="${pageContext.request.contextPath}/displayImg?imageId=${img.imageId}"
									class="img-thumbnail" />
								</a>
							</div>
						</div>
					</c:if>
				</c:forEach>
			</fieldset>
		</c:when>
		<c:otherwise>
			<c:set var="images" value="${sessionScope['images']}" />
			<fieldset>
				<legend>Mes Photos</legend>
				<c:forEach var="img" items='${images}'>
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
			<fieldset>
				<legend>Ajouter des images</legend>
				<form action="${pageContext.request.contextPath}/uploadImage"
					method="post" enctype="multipart/form-data">
					<input type="file" name="file" accept="image/*" /> <br /> <input
						type="submit" value="Télécharger" />
				</form>
			</fieldset>
		</c:otherwise>
	</c:choose>
</div>