<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:choose>
	<c:when test="${sessionScope.user ne null }">
		<jsp:include page="/pages/public/templates/header.jsp" />
		<div class="container">
			<div class="row">
				<jsp:include page="/pages/public/templates/sidebar.jsp" />
				<div class="col-md-9">
					<jsp:include page="/pages/private/templates/mesamis.jsp" />
				</div>
			</div>
		</div>
		<jsp:include page="/pages/public/templates/footer.jsp" />
	</c:when>
	<c:otherwise>
		<jsp:forward page="/pages/public/login.jsp" />
	</c:otherwise>
</c:choose>
