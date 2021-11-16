<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!doctype html>
<html lang="it" class="h-100">
	<head>
		<jsp:include page="../header.jsp" />
		<title>Ricerca</title>
	</head>
	<body class="d-flex flex-column h-100">
		<jsp:include page="../navbar.jsp" />
		
		<!-- Begin page content -->
		<main class="flex-shrink-0">
		  <div class="container">
		
				<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none':'' }" role="alert">
				  ${errorMessage}
				  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
				</div>
				
				<div class='card'>
				    <div class='card-header'>
				        <h5>Ricerca elementi</h5> 
				    </div>
				    <div class='card-body'>
		
							<form method="post" action="listGestione" class="row g-3">
							
								<div class="col-md-6">
									<label for="denominazione" class="form-label">Denominazione</label>
									<input type="text" name="denominazione" id="denominazione" class="form-control" placeholder="Inserire denominazione" value="${search_gestione_tavolo_attr.denominazione}">
								</div>
								
								<div class="col-md-6">
									<label for="dataCreazione" class="form-label">Data di Creazione</label>
	                        		<input class="form-control" id="dataCreazione" type="date" placeholder="dd/MM/yy"
	                            		title="formato : gg/mm/aaaa"  name="dataCreazione" value="${search_gestione_tavolo_attr.dataCreazione}">
								</div>
								
								<div class="col-md-6">
									<label for="cifraMinima" class="form-label">Cifra minima</label>
									<input type="number" name="cifraMinima" id="cifraMinima" class="form-control" placeholder="Inserire cifra minima" value="${search_gestione_tavolo_attr.cifraMinima}">
								</div>
								
								<div class="col-md-6">
									<label for="esperienzaMin" class="form-label">Esperienza minima</label>
									<input type="number" class="form-control" name="esperienzaMin" id="esperienzaMin" placeholder="Inserire esperienza minima" value="${search_gestione_tavolo_attr.esperienzaMin}">
								</div>
								
								<sec:authorize access="hasRole('ADMIN')">
									<div class="col-md-6">
										<label for="utenteCreatoreSearchInput" class="form-label">Creatore tavolo:</label>
										<input class="form-control " type="text" id="utenteCreatoreSearchInput"
												name="utenteCreatoreInput" value="${search_gestione_tavolo_attr.utenteCreatore.nome}${search_gestione_tavolo_attr.utenteCreatore.cognome}">
										<input type="hidden" name="utenteCreatore.id" id="utenteCreatoreSearchInput" value="${search_gestione_tavolo_attr.utenteCreatore.id}">
									</div>
								</sec:authorize>
								
									
								<div class="col-12">
									<button type="submit" name="submit" value="submit" id="submit" class="btn btn-primary">Conferma</button>
									<input class="btn btn-outline-warning" type="reset" value="Ripulisci">
									<a class="btn btn-outline-primary ml-2" href="${pageContext.request.contextPath }/film/insert">Add New</a>
								</div>
								
							</form>
		
				    		<script>
								$("#utenteCreatoreSearchInput").autocomplete({
									 source: function(request, response) {
									        $.ajax({
									            url: "../utente/searchUtentiAjax",
									            datatype: "json",
									            data: {
									                term: request.term,   
									            },
									            success: function(data) {
									                response($.map(data, function(item) {
									                    return {
										                    label: item.label,
										                    value: item.value
									                    }
									                }))
									            }
									        })
									    },
									//quando seleziono la voce nel campo deve valorizzarsi la descrizione
								    focus: function(event, ui) {
								        $("#utenteCreatoreSearchInput").val(ui.item.label)
								        return false
								    },
								    minLength: 2,
								    //quando seleziono la voce nel campo hidden deve valorizzarsi l'id
								    select: function( event, ui ) {
								    	$('#utenteCreatoreSearchInputId').val(ui.item.value);
								    	//console.log($('#registaId').val())
								        return false;
								    }
								});
							</script>
							
				    
					<!-- end card-body -->			   
				    </div>
				<!-- end card -->
				</div>	
				
			<!-- end container -->
			</div>
		<!-- end main -->	
		</main>
		<jsp:include page="../footer.jsp" />
		
	</body>
</html>