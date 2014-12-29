<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="templates/header.jsp"/>
<div class="container">
	<div class="row">
		<jsp:include page="templates/sidebar.jsp"/>
		<c:choose>
			<c:when test="${sessionScope.user ne null }">
				<jsp:forward page="/pages/private/displayUserProfile.jsp" />
			</c:when>
			<c:otherwise>
				<jsp:forward page="/pages/public/login.jsp" />
			</c:otherwise>
		</c:choose>
	</div>
</div>
<jsp:include page="templates/footer.jsp" />