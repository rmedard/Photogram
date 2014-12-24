<%@page import="be.kayiranga.model.User"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/pages/public/templates/header.jsp" />
<div class="container">
	<div class="row">
		<%-- <%@include file="/pages/public/templates/sidebar.jsp"%> --%>
		<jsp:include page="/pages/public/templates/sidebar.jsp" />
		<div class="col-md-9">
			<c:choose>
				<c:when test="${sessionScope.user eq null }">
					<c:set var="name" value="" />
					<c:set var="postname" value="" />
					<c:set var="username" value="" />
					<c:set var="email" value="" />
					<c:set var="password" value="" />
				</c:when>
				<c:otherwise>
					<c:set var="user" value="${sessionScope.user}" />
					<c:set var="name" value="${user.name }" />
					<c:set var="postname" value="${user.postname }" />
					<c:set var="username" value="${user.username }" />
					<c:set var="email" value="${user.email }" />
					<c:set var="password" value="${user.password }" />
				</c:otherwise>
			</c:choose>
			<form class="form-horizontal" role="form"
				action="${pageContext.request.contextPath}/editAddUser?newRecord=${sessionScope.user eq null}"
				method="post">
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">Nom</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="name"
							placeholder="Nom" name="nameTxt" required="required"
							value="${name}">
					</div>
				</div>
				<div class="form-group">
					<label for="postname" class="col-sm-2 control-label">Prénom</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="postname"
							placeholder="Prénom" name="postnameTxt" required="required"
							value="${postname}">
					</div>
				</div>
				<div class="form-group">
					<label for="username" class="col-sm-2 control-label">Nom
						d'utilisateur</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="username"
							placeholder="Nom d'utilisateur" name="usernameTxt"
							required="required" value="${username}">
					</div>
				</div>
				<div class="form-group">
					<label for="email" class="col-sm-2 control-label">Email</label>
					<div class="col-sm-10">
						<input type="email" class="form-control" id="email"
							placeholder="Email" name="emailTxt" required="required"
							value="${email}">
					</div>
				</div>
				<div class="form-group">
					<label for="password" class="col-sm-2 control-label">Mot de
						passe</label>
					<div class="col-sm-10">
						<input type="password" class="form-control" id="password"
							placeholder="Mot de passe" name="passwordTxt" required="required"
							value="${password}">
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button type="submit" class="btn btn-default">Sauvegarder</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
<jsp:include page="/pages/public/templates/footer.jsp" />