$(document).ready(function() {

	var id, nom, prenom, email, telephone, dateNaissance;
	$.ajax({
		url: "UserController",
		method: "POST",
		data: {
			op: "load",
		},
		success: function(response) {
			usertable(response);
		},
		error: function() {
			alert("though luck buddy :D")
		}
	});

	function usertable(users) {
		var ligne = "";
		$(".userSmartphone").html("");
		users.forEach(u => {
			ligne += "<tr><td>" + u.nom + "</td><td>" + u.prenom + "</td><td>" + u.email + "<td>" + u.dateNaissance + "</td><td>" + u.telephone + "</td><td class=" + 'text-center' + "><button   class=" + '"update btn btn-success mx-2"' + " value=" + u.id + " data-toggle=" + '"modal"' + " data-target=" + '"#modal"' + ">" + 'Modifier' + "</button><button class=" + '"dele btn btn-danger mx-2"' + " value=" + u.id + ">" + 'Delete' + "</button></td></tr>";
			$('.userSmartphone').append($('<option>',
				{
					value: u.id,
					text: (u.nom).toString(),
				}));

		});
		$("#userbody").html(ligne);
	}


	$(document).on("click", "#adduser", function() {
		nom = $("#nomu").val();
		prenom = $("#prenomu").val();
		email = $("#emailu").val();
		dateNaissance = $("#datenu").val();
		telephone = $("#telephoneu").val();
		if (nom == "" || prenom == "" || email == "" || dateNaissance == "" || telephone == "") {
			alert("veuillez saisir des donnees");
		}
		else {
			$.ajax({
				url: "UserController",
				method: "POST",
				data: {
					op: "save",
					nomu: nom,
					prenomu: prenom,
					emailu: email,
					datenu: dateNaissance,
					telephoneu: telephone,
				},
				success: function(response) {
					alert("Utilisateur ajoute");
					usertable(response);
				},
				error: function() {
					alert("Though Luck Buddy");
				}
			});
		}
	});
	$(document).on('click', '.dele', function() {
		id = $(this).val();
		$.ajax({
			url: "UserController",
			method: "POST",
			data: {
				op: "dele",
				id: id,
			},
			success: function(response) {
				alert("Utilisateur Supprime ");
				usertable(response);
			},
			error: function() {
				alert("Erreur de suppression");
			}
		});
	});

	$(document).on("click", ".update", function() {
		id = $(this).val();
		$("#modal").modal('toggle');
	});
	$("#updateu").on("click", function() {
		nom = $("#nomup").val();
		prenom = $("#prenomup").val();
		email = $("#emailup").val();
		dateNaissance = $("#datenup").val();
		telephone = $("#telephoneup").val();
		$.ajax({
			url: "UserController",
			method: "POST",
			data: {
				op: "update",
				id: id,
				nom: nom,
				prenom: prenom,
				email: email,
				dateNaissance: dateNaissance,
				telephone: telephone,
			},
			success: function(response) {
				$("#modal").modal('hide');
				alert("Utilisateur Modifie");
				usertable(response);
			},
			error: function() {
				alert("though luck buddy");
			}
		});



	});

	// Gestion Smartphone

	var imei, marque, iduser;

	$("#searchSmartphoneByUser").on("click", function() {

		iduser = $("#searchUserSmart").val();
		$.ajax({
			url: "SmartphoneController",
			method: "POST",
			data: {
				op: "load",
				iduser: iduser,
			},
			success: function(response) {
				smartphonetable(response);
				console.log(response);
				$("#tsmartphone").show();
			},
			error: function() {

			}
		});
	});


	function smartphonetable(smartphones) {
		var ligne = "";
		smartphones.forEach(s => {
			ligne += "<tr><td>" + s.imei + "</td><td>" + s.marque + "</td><td class=" + 'text-center' + "><button   class=" + '"updateSmartphone btn btn-success mx-2"' + " value=" + s.id + ">" + 'Modifier' + "</button><button class=" + '"deleteSmartphone btn btn-danger mx-2"' + " value=" + s.id + ">" + 'Delete' + "</button></td></tr>";

		});
		$("#smartphonebody").html(ligne);
	}

	$("#addSmartphone").on("click", function() {
		imei = $("#imei").val();
		marque = $("#marque").val();
		iduser = $("#userSmartphone").val();
		if (imei == "" || marque == "" || iduser == "") {
			alert("Priere de remplir le formulaire");
		}
		else {
			$.ajax({
				url: "SmartphoneController",
				method: "POST",
				data: {
					op: "save",
					imei: imei,
					marque: marque,
					iduser: iduser,
				},
				success: function() {
					alert("Smartphone Ajoute");
				},
				error: function() {
					alert("Erreur");
				}
			});
		}
	});

	$(document).on("click", ".deleteSmartphone", function(e) {
		id = $(this).val();
		iduser = $("#searchUserSmart").val();
		$.ajax({
			url: "SmartphoneController",
			method: "POST",
			data: {
				op: "delete",
				id: id,
				iduser: iduser,
			},
			success: function(response) {
				smartphonetable(response);
				alert("Smartphone supprime");
			},
			error: function() {
				alert("Erreur Suppresion");
			}
		});

	});
	$(document).on("click", ".updateSmartphone", function() {
		id = $(this).val();
		$("#modalSmartphone").modal('toggle');
	});
	$("#updatesmart").on("click", function() {
		imei = $("#imeiupdate").val();
		marque = $("#marqueupdate").val();
		iduser = $("#searchUserSmart").val();
		$.ajax({
			url: "SmartphoneController",
			method: "POST",
			data: {
				op: "update",
				imei: imei,
				marque: marque,
				id: id,
				iduser: iduser,
			},
			success: function(response) {
				alert("Smartphone Modifie");
				smartphonetable(response);
				$("#modalSmartphone").modal('hide');
			},
			error: function() {
				alert("unlucky Bro");
				$("#modalSmartphone").modal('hide');
			}
		});
	});

	$(".closeModal").on("click", function() {
		$("#modal").modal('hide');
		$("#modalSmartphone").modal('hide');
	});

	// Graphe

	var chart;
	var labels = [];
	var datas = [];
	$.ajax({
		method: "POST",
		url: "GrapheController",
		data: {
			op: "load",
		},
		success: function(response) {
			response.forEach(r => {
				labels.push(r.imei);
				datas.push(r.marque);
			})
			var data = {
				labels,
				datasets: [{
					label: "NBR Position recupere pour chaque Smartphones",
					data: datas,
				}],
			};

			var config = {
				type: 'line',
				data: data,
				options: {
					response: true,
				}
			};
			chart = new Chart($("#myChart"), config);
		},
		error: function() {
			alert("yikes");
		}
	});

	//	Map

	$("#guser").on("click", function() {
		$("#users").show();
		$("#tusers").show();
		$("#smartphones").hide();
		$("#tsmartphone").hide();
		$("#search").hide();
		$("#graph").hide();
		$("#searchPosition").hide();
	});
	$("#gsmartphone").on("click", function() {
		$("#smartphones").show();
		$("#users").hide();
		$("#tusers").hide();
		$("#tsmartphone").hide();
		$("#search").hide();
		$("#graph").hide();
		$("#searchPosition").hide();
	});
	$("#searchsmartphone").on("click", function() {
		$("#search").show();
		$("#users").hide();
		$("#tusers").hide();
		$("#smartphones").hide();
		$("#tsmartphone").hide();
		$("#graph").hide();
		$("#searchPosition").hide();
	});
	$("#graphePosition").on("click", function() {
		$("#searchPosition").hide();
		$("#graph").show();
		$("#search").hide();
		$("#users").hide();
		$("#tusers").hide();
		$("#smartphones").hide();
		$("#tsmartphone").hide();
	});
	$("#mapPosition").on("click", function() {
		$.ajax({
			url: "MapController",
			method: "POST",
			data: {
				op: "load",
			},
			success: function(response) {
				$("#smartphonepositionselect").html("");
				response.forEach(r => {
					$("#smartphonepositionselect").append($('<option>',
						{
							value: r.id,
							text: (r.imei).toString(),
						}));
				});
			},
			error: function() {

			}
		});
		$("#graph").hide();
		$("#search").hide();
		$("#users").hide();
		$("#tusers").hide();
		$("#smartphones").hide();
		$("#tsmartphone").hide();
		$("#searchPosition").show();
	});
});

