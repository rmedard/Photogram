<%@page import="be.kayiranga.model.User"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/pages/public/templates/header.jsp" />
<c:if test="${request.getSession(false).getAttribute(\"user\") ne null}">
	<c:redirect url="/pages/private/displayUserProfile.jsp" />
</c:if>
<div class="container">
	<div class="row">
		<%@include file="/pages/public/templates/sidebar.jsp"%>

		<div class="col-md-9">
			<div class="col-md-5">
				<form class="form-horizontal" role="form"
					action="${pageContext.request.contextPath}/login?action=login"
					method="post">
					<div class="form-group">
						<label for="username" class=" control-label">Nom
							d'utilisateur</label>
						<div class="">
							<input type="text" class="form-control" name="usernameTxt"
								placeholder="Nom d'utilisateur">
						</div>
					</div>
					<div class="form-group">
						<label for="password" class=" control-label">Mot
							de passe</label>
						<div class="">
							<input type="password" class="form-control" name="passwordTxt"
								placeholder="Mot de passe">
						</div>
					</div>
					<div class="form-group" hidden="hidden">
						<div class="col-sm-offset-2 col-sm-10">
							<div class="checkbox">
								<label> <input type="checkbox"> Remember me
								</label>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="">
							<button type="submit" class="btn btn-default">Login</button>
						</div>
					</div>
				</form>
			</div>
			<div class="col-md-4" id="rightCol">
				<fieldset>
					<legend>Nouvel utilisateur?</legend>
					<div class="col-sm-offset-2 col-sm-10">
						<a href="/Photogram/pages/public/editAddUser.jsp"
							class="btn btn-default">S'inscrire</a>
					</div>
				</fieldset>
			</div>
		</div>
	</div>
</div>
<%@include file="/pages/public/templates/footer.jsp"%>