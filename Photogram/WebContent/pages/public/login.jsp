<%@page import="be.kayiranga.model.User"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- <%@include file="/pages/public/templates/header.jsp"%> --%>
<jsp:include page="/pages/public/templates/header.jsp" />
<c:if test="${request.getSession(false).getAttribute(\"user\") ne null}">
	<c:redirect url="/pages/private/displayUserProfile.jsp" />
</c:if>
<div class="container">
	<div class="row">
		<%@include file="/pages/public/templates/sidebar.jsp"%>

		<div class="col-md-9">
			<form class="form-horizontal" role="form"
				action="${pageContext.request.contextPath}/login?action=login"
				method="post">
				<div class="form-group">
					<label for="username" class="col-sm-2 control-label">Nom
						d'utilisateur</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" name="usernameTxt"
							placeholder="Nom d'utilisateur">
					</div>
				</div>
				<div class="form-group">
					<label for="password" class="col-sm-2 control-label">Mot de
						passe</label>
					<div class="col-sm-10">
						<input type="password" class="form-control" name="passwordTxt"
							placeholder="Mot de passe">
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<div class="checkbox">
							<label> <input type="checkbox"> Remember me
							</label>
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button type="submit" class="btn btn-default">Login</button>
					</div>
				</div>
				<fieldset>
					<legend>S'incrire</legend>
					<div class="col-sm-offset-2 col-sm-10">
						<a href="/Photogram/pages/public/editAddUser.jsp" class="btn btn-default">S'inscrire</a>
					</div>
				</fieldset>
			</form>
		</div>
	</div>
</div>
<%@include file="/pages/public/templates/footer.jsp"%>