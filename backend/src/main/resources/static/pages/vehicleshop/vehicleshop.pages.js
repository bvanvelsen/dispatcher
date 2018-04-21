angular.module('be.dispatcher.pages.vehicleshop', ['restangular'])
	.config(function ($stateProvider) {
		var shopState = {
			name: 'shop',
			url: '/shop',
			templateUrl: 'pages/vehicleshop/vehicleshop.pages.html',
			controller: 'VehicleshopController',
			controllerAs: 'ctrl'
		}

		$stateProvider.state(shopState);
	})
	.controller('VehicleshopController', function (ImagesMarkersClient, BasesClient, VehicleClient, IncidentClient, $interval) {
		var ctrl = this;
		var mymap;
		var bases;
		var ambulanceIcons = new Array();
		var mugIcons = new Array();
		var rvIcons = new Array();
		var tsIcons = new Array();
		var hvIcons = new Array();
		var policeCombiIcons = new Array();
		var policeInterceptorIcons = new Array();
		var incidentIcons = new Array();

		mymap = L.map('mapid').setView([50.92669, 5.342462], 13);
		L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {
			attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="http://mapbox.com">Mapbox</a>',
			maxZoom: 18,
			id: 'mapbox.streets',
			accessToken: 'pk.eyJ1IjoiYnZhbnZlbHNlbiIsImEiOiJjamYzMGg4NWgwdmVhMzJscjNpYjRmaWUxIn0.t5wgMW7Yzu5IgEWC9FjvUA'
		}).addTo(mymap);

		addHospitalsToMap();
		addFireDepartmentsToMap();
		addPoliceStationsToMap();
		addAmbulanceStationsToMap();

		function addHospitalsToMap() {
			BasesClient.getAllHospitals().then(function (value) {
				bases = value;
				for (i = 0; i < bases.length; i++) {
					L.marker([bases[i].location.lat, bases[i].location.lon], {icon: ImagesMarkersClient.getHospitalIcon(), zIndexOffset: 1000}).addTo(mymap);
				}
			});
		}

		function addFireDepartmentsToMap() {
			BasesClient.getAllFireDepartments().then(function (value) {
				bases = value;
				for (i = 0; i < bases.length; i++) {
					L.marker([bases[i].location.lat, bases[i].location.lon], {icon: ImagesMarkersClient.getBrandweerIcon(), zIndexOffset: 1000}).addTo(mymap);
				}
			});
		}

		function addPoliceStationsToMap() {
			BasesClient.getAllPoliceStations().then(function (value) {
				bases = value;
				for (i = 0; i < bases.length; i++) {
					const marker = L.marker([bases[i].location.lat, bases[i].location.lon], {icon: ImagesMarkersClient.getPoliceIcon(), zIndexOffset: 1000});
					marker.addTo(mymap);
				}
			});
		}

		function addAmbulanceStationsToMap() {
			BasesClient.getAllAmbulanceStations().then(function (value) {
				bases = value;
				for (i = 0; i < bases.length; i++) {
					L.marker([bases[i].location.lat, bases[i].location.lon], {icon: ImagesMarkersClient.getAmbulanceStationIcon(), zIndexOffset: 1000}).addTo(mymap);
				}
			});
		}

		function addVehiclesToMap() {
			for (i = 0; i < ambulanceIcons.length; i++) {
				mymap.removeLayer(ambulanceIcons[i]);
			}
			for (i = 0; i < mugIcons.length; i++) {
				mymap.removeLayer(mugIcons[i]);
			}
			for (i = 0; i < rvIcons.length; i++) {
				mymap.removeLayer(rvIcons[i]);
			}
			for (i = 0; i < tsIcons.length; i++) {
				mymap.removeLayer(tsIcons[i]);
			}
			for (i = 0; i < hvIcons.length; i++) {
				mymap.removeLayer(hvIcons[i]);
			}
			for (i = 0; i < policeCombiIcons.length; i++) {
				mymap.removeLayer(policeCombiIcons[i]);
			}
			for (i = 0; i < policeInterceptorIcons.length; i++) {
				mymap.removeLayer(policeInterceptorIcons[i]);
			}
			ambulanceIcons = [];
			mugIcons = [];
			rvIcons = [];
			hvIcons = [];
			tsIcons = [];
			policeCombiIcons = [];
			policeInterceptorIcons = [];

			var AllMedicalVehicles = VehicleClient.getAllMedicalVehicles();
			for (i in AllMedicalVehicles) {
				var currentMedicalVehicle = AllMedicalVehicles[i];
				if (currentMedicalVehicle.vehicleType === 'AMBULANCE') {
					const marker = L.marker([currentMedicalVehicle.location.lat, currentMedicalVehicle.location.lon], {icon: ImagesMarkersClient.getAmbulanceIcon()});
					ambulanceIcons.push(marker);
					marker.addTo(mymap);
				} else if (currentMedicalVehicle.vehicleType === 'MUG') {
					const marker = L.marker([currentMedicalVehicle.location.lat, currentMedicalVehicle.location.lon], {icon: ImagesMarkersClient.getMugIcon()});
					mugIcons.push(marker);
					marker.addTo(mymap);
				}
			}
			var AllFireTrucks = VehicleClient.getAllFireTrucks();
			for (i in AllFireTrucks) {
				var currentFireTruck = AllFireTrucks[i];
				if (currentFireTruck.vehicleType === 'FD_AUTOPOMP') {
					const marker = L.marker([currentFireTruck.location.lat, currentFireTruck.location.lon], {icon: ImagesMarkersClient.getTSIcon()});
					tsIcons.push(marker);
					marker.addTo(mymap);
				} else if (currentFireTruck.vehicleType === 'RV') {
					const marker = L.marker([currentFireTruck.location.lat, currentFireTruck.location.lon], {icon: ImagesMarkersClient.getRVIcon()});
					rvIcons.push(marker);
					marker.addTo(mymap);
				} else if (currentFireTruck.vehicleType === 'HV') {
					const marker = L.marker([currentFireTruck.location.lat, currentFireTruck.location.lon], {icon: ImagesMarkersClient.getHVIcon()});
					hvIcons.push(marker);
					marker.addTo(mymap);
				}
			}
			var AllPoliceVehicles = VehicleClient.getAllPoliceVehicles();
			for (i in AllPoliceVehicles) {
				var currentPoliceVehicle = AllPoliceVehicles[i];
				if (currentPoliceVehicle.vehicleType === 'COMBI') {
					const marker = L.marker([currentPoliceVehicle.location.lat, currentPoliceVehicle.location.lon], {icon: ImagesMarkersClient.getPoliceCombiIcon()});
					policeCombiIcons.push(marker);
					marker.addTo(mymap);
				} else if (currentPoliceVehicle.vehicleType === 'INTERCEPTOR') {
					const marker = L.marker([currentPoliceVehicle.location.lat, currentPoliceVehicle.location.lon], {icon: ImagesMarkersClient.getPoliceInterceptorIcon()});
					policeInterceptorIcons.push(marker);
					marker.addTo(mymap);
				}
			}

		}

		function removeAllIncidentMarkers() {
			for (i = 0; i < incidentIcons.length; i++) {
				mymap.removeLayer(incidentIcons[i]);
			}
			incidentIcons = [];
		}

		function addAllIncidentMarkers() {
			var allIncidents = IncidentClient.getAllIncidents();
			for (incidentId in allIncidents) {
				console.log(allIncidents[incidentId]);
				const marker = L.marker([allIncidents[incidentId].location.lat, allIncidents[incidentId].location.lon], {icon: ImagesMarkersClient.getIncidentIcon()});
				incidentIcons.push(marker);
				marker.addTo(mymap);
			}
		}

		function addIncidentsToMap() {
			removeAllIncidentMarkers();
			addAllIncidentMarkers();
		}

		ctrl.loadBases = function () {
			BasesClient.getAllHospitals().then(function (value) {
				bases = value;
			});
			console.log('load bases');

		}

		ctrl.createIncident = function () {
			IncidentClient.createIncident();
		};

		ctrl.getAllIncidentsFromBackend = function () {
			IncidentClient.getAllIncidentsFromBackend();
		};

		$interval(function () {
			addIncidentsToMap();
			addVehiclesToMap();
		}, 1000);
	});