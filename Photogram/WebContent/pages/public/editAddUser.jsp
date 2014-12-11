<%-- <%@include file="/pages/public/templates/header.jsp"%> --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/pages/public/templates/header.jsp" />
<div class="container">
	<div class="row">
		<%-- <%@include file="/pages/public/templates/sidebar.jsp"%> --%>
		<jsp:include page="/pages/public/templates/sidebar.jsp" />
		<div class="col-md-9">

			<%
				boolean newUser;
				if (request.getSession(false).getAttribute("user") != null) {
					newUser = false;
				}else{
					newUser = true;
				}
				String name = "";
				String postname = "";
				String username = "";
				String email = "";
				String password = "";
				if (!newUser) {
					name = request.getAttribute("name").toString();
					postname = request.getAttribute("postname").toString();
					username = request.getAttribute("username").toString();
					email = request.getAttribute("email").toString();
					password = request.getAttribute("password").toString();
				}
			%>
			<form class="form-horizontal" role="form" action="editAddUser" method="post">
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
<%-- <%@include file="/pages/public/templates/footer.jsp"%> --%>