<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row">
	<fieldset>
		<legend>Les photos de mes amis</legend>
		<c:forEach var="followee" items="${sessionScope['followees']}">
			<fieldset class="user-profile">
				<legend class="well well-sm" id="followed-users">
					<img
						src="${pageContext.request.contextPath}/displayProfilePic?userId=${followee.userId}"
						class="img-thumbnail img-circle" />
					<label>Nom d'utilisateur:</label>
					<c:out value=" ${followee.username}" /><br/>
					<label>Nom et prénom:</label>
					<c:out value=" ${followee.name} ${followee.postname }"/><br/>
					<label>Email:</label>
					<c:out value=" ${followee.email}"/>
				</legend>
				<c:forEach var="img" items='${sessionScope["followeeImages"]}'>
					<c:if test="${followee.userId eq img.ownerId and img.publicPic}">
						<div class="col-md-3">
							<div class="photo-thumbnail">
								<a
									href="${pageContext.request.contextPath}/imagedatadisplay?imageId=${img.imageId}&owner=${false}">
									<c:set var="owner" value="${false}" scope="request" /> <img
									src="${pageContext.request.contextPath}/displayFriendImage?imageId=${img.imageId}"
									class="img-thumbnail" />
								</a>
							</div>
						</div>
					</c:if>
				</c:forEach>
			</fieldset>
			<br/>
		</c:forEach>
	</fieldset>
</div>