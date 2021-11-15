<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="it" class="h-100">
<head>
<!-- Common imports in pages -->
<jsp:include page="../header.jsp" />
<title>Visualizza Utente</title>

</head>
<body class="d-flex flex-column h-100">
	<!-- Fixed navbar -->
	<jsp:include page="../navbar.jsp" />

	<!-- Begin page content -->
	<main class="flex-shrink-0">
		<div class="container">

			<div class='card'>
				<div class='card-header'>Visualizza dettaglio</div>

				<div class='card-body'>
					<dl class="row">
						<dt class="col-sm-3 text-right">Id:</dt>
						<dd class="col-sm-9">${show_utente_attr.id}</dd>
					</dl>

					<dl class="row">
						<dt class="col-sm-3 text-right">Nome:</dt>
						<dd class="col-sm-9">${show_utente_attr.nome}</dd>
					</dl>

					<dl class="row">
						<dt class="col-sm-3 text-right">Cognome:</dt>
						<dd class="col-sm-9">${show_utente_attr.cognome}</dd>
					</dl>

					<dl class="row">
						<dt class="col-sm-3 text-right">Data Creazione:</dt>
						<dd class="col-sm-9">
							<fmt:formatDate type="date"
								value="${show_utente_attr.dateCreated}" />
						</dd>
					</dl>

					<dl class="row">
						<dt class="col-sm-3 text-right">Username:</dt>
						<dd class="col-sm-9">${show_utente_attr.username}</dd>
					</dl>

					<dl class="row">
						<dt class="col-sm-3 text-right">Stato:</dt>
						<dd class="col-sm-9">${show_utente_attr.stato}</dd>
					</dl>

					<dl class="row">
						<dt class="col-sm-3 text-right">Ruoli:</dt>
							<dd class="col-sm-9">
								<table>
									<c:forEach items="${show_utente_attr.ruoli}" var="ruoloItem">
										<tr>
											<td>${ruoloItem.descrizione}</td>
										</tr>
									</c:forEach>
								</table>
							</dd>
					</dl>
					<!-- end card body -->
				</div>


				<div class='card-footer'>
					<a href="${pageContext.request.contextPath }/utente/"
						class='btn btn-outline-secondary' style='width: 80px'> <i
						class='fa fa-chevron-left'></i> Back
					</a>
				</div>
				<!-- end card -->
			</div>

			<!-- end container -->
		</div>

	</main>
	<jsp:include page="../footer.jsp" />

</body>
</html>