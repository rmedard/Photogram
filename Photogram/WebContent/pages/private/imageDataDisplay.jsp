<%@include file="/pages/public/templates/header.jsp"%>
<c:set var="image" value="${requestScope['active-image']}" />
<div class="container">
	<div class="row">
		<%@include file="/pages/public/templates/sidebar.jsp"%>
		<div class="col-md-9">
			<div class="row">
				<img
					src="${pageContext.request.contextPath}/displayImg?imageId=${image.imageId}"
					class="img-thumbnail full-image">
			</div>
			<div class="row">
				<fieldset>
					<legend>Description</legend>
					<c:out value="${image.description}" />
				</fieldset>
				<c:if test="${requestScope['owner']}">
					<div class="user-private-edit">
						<fieldset>
							<legend>Visibilité</legend>
							<div class="control-group">
								<label class="control-label">Photo publique:</label>
								<c:choose>
									<c:when test="${image.publicPic}">
										<c:out value="Oui" />
									</c:when>
									<c:otherwise>
										<c:out value="Non"></c:out>
									</c:otherwise>
								</c:choose>
							</div>
							<div class="control-group">
								<label class="control-label">Photo de profile:</label>
								<c:choose>
									<c:when test="${image.profilePic}">
										<c:out value="Oui" />
									</c:when>
									<c:otherwise>
										<c:out value="Non"></c:out>
									</c:otherwise>
								</c:choose>
							</div>
						</fieldset>
						<fieldset>
							<div class="control-group">
								<button class="btn btn-default" data-toggle="modal"
									data-target="#image-data-edit">Modifier</button>
								<button class="btn btn-default" data-toggle="modal"
									data-target="#image-delete">Supprimer</button>
							</div>
						</fieldset>
					</div>
				</c:if>

			</div>
		</div>
		<div class="modal fade" id="image-delete" tabindex="-1" role="dialog"
			aria-labelledby="confirmDelete" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel">Confirmez</h4>
					</div>
					<div class="modal-body">
						<h3>Voulez-vous supprimer cette image?</h3>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Fermer</button>
						<a
							href="${pageContext.request.contextPath}/delete-image?imageId=${image.imageId}"
							class="btn btn-primary">Supprimer</a>
					</div>
				</div>
			</div>
		</div>
		<div class="modal fade" id="image-data-edit" tabindex="-1"
			role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<form
						action="${pageContext.request.contextPath}/editImage?imageId=${image.imageId}"
						method="post">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
							</button>
							<h4 class="modal-title">Modifier</h4>
						</div>
						<div class="modal-body">
							<div class="form-group">
								<label for="descr">Description</label>
								<textarea rows="5" id="descr" style="width: 100%;"
									name="descriptionTxt">${image.description}</textarea>
							</div>
							<fieldset>
								<legend>Photo publique</legend>
								<c:choose>
									<c:when test="${image.publicPic}">
										<label class="radio-inline"><input type="radio"
											name="publicRd" value="true" checked="checked">Oui</label>
										<label class="radio-inline"><input type="radio"
											name="publicRd" value="false">Non</label>
									</c:when>
									<c:otherwise>
										<label class="radio-inline"><input type="radio"
											name="publicRd" value="true">Oui</label>
										<label class="radio-inline"><input type="radio"
											name="publicRd" value="false" checked="checked">Non</label>
									</c:otherwise>
								</c:choose>

							</fieldset>
							<fieldset>
								<legend>Photo de profil</legend>
								<c:choose>
									<c:when test="${image.profilePic}">
										<label class="radio-inline"><input type="radio"
											name="profileRd" value="true" checked="checked">Oui</label>
										<label class="radio-inline"><input type="radio"
											name="profileRd" value="false">Non</label>
									</c:when>
									<c:otherwise>
										<label class="radio-inline"><input type="radio"
											name="profileRd" value="true">Oui</label>
										<label class="radio-inline"><input type="radio"
											name="profileRd" value="false" checked="checked">Non</label>
									</c:otherwise>
								</c:choose>

							</fieldset>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">Fermer</button>
							<button type="submit" class="btn btn-primary">Sauvegarder</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
<%@include file="/pages/public/templates/footer.jsp"%>