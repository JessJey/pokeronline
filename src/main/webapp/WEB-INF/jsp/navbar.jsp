<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<header>
	<!-- Fixed navbar -->
	<nav class="navbar navbar-expand-lg navbar-dark bg-primary"
		aria-label="Eighth navbar example">
		<div class="container">


				<button class="navbar-toggler" type="button"
					data-bs-toggle="collapse" data-bs-target="#navbarsExample07"
					aria-controls="navbarsExample07" aria-expanded="false"
					aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>

					<div class="collapse navbar-collapse" id="navbarsExample07">
						<ul class="navbar-nav me-auto mb-2 mb-lg-0">
							<li class="nav-item"><a class="nav-link active"
								aria-current="page"
								href="${pageContext.request.contextPath}/home">Home</a></li>
								
								<sec:authorize access="hasRole('ADMIN') || hasRole('SPECIAL_PLAYER')">
							<li class="nav-item dropdown"><a
								class="nav-link dropdown-toggle" href="#" id="dropdown07"
								data-bs-toggle="dropdown" aria-expanded="false">Gestione My Tavoli</a>
								<ul class="dropdown-menu" aria-labelledby="dropdown07">
									<li><a class="dropdown-item"
										href="${pageContext.request.contextPath}/home">Home</a></li>
										<li><a class="dropdown-item"
										href="${pageContext.request.contextPath}/tavolo/gestione">Cerca tra i tuoi tavoli</a></li>
									<li><a class="dropdown-item"
										href="${pageContext.request.contextPath}/tavolo/mieitavoli">Lista My Tables</a></li>
									<li><a class="dropdown-item"
										href="${pageContext.request.contextPath}/tavolo/insert">Crea un Tavolo</a></li>
								</ul></li>
								</sec:authorize>
								
							<sec:authorize access="hasRole('ADMIN')">
								<li class="nav-item dropdown"><a
									class="nav-link dropdown-toggle" href="#" id="dropdown01"
									data-bs-toggle="dropdown" aria-haspopup="true"
									aria-expanded="false">Gestione Utenze</a>
									<div class="dropdown-menu" aria-labelledby="dropdown01">
										<a class="dropdown-item"
											href="${pageContext.request.contextPath}/utente/search">Ricerca
											Utenti</a> <a class="dropdown-item"
											href="${pageContext.request.contextPath}/utente/insert">Inserisci
											Utente</a>
									</div></li>
							</sec:authorize>
						</ul>
					</div>

				<div class="text-end">
					<div class="collapse navbar-collapse" id="navbarsExample07">
						<ul class="navbar-nav me-auto mb-2 mb-lg-0">

							<sec:authorize access="isAuthenticated()">

								<li class="nav-item dropdown"><a
									class="nav-link dropdown-toggle" type="button" href="#"
									id="dropdown03" data-bs-toggle="dropdown" aria-expanded="false">Utente:
										<sec:authentication property="name" />
								</a>
									<div class="dropdown-menu" aria-labelledby="dropdown03">
										<a class="dropdown-item"
											href="${pageContext.request.contextPath}/utente/resetuserpassword">Reset
											Password</a> 
											<a class="dropdown-item"
											href="${pageContext.request.contextPath}/logout">Logout</a>
											<a class="dropdown-item"
											href="${pageContext.request.contextPath}/utente/credito">Add Credito
											</a> 
									</div></li>
							</sec:authorize>

						</ul>
					</div>
				</div>



			</div>

	</nav>


</header>