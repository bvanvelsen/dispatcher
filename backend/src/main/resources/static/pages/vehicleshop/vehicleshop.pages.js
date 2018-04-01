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
	.controller('VehicleshopController', function (BasesClient, VehicleClient, IncidentClient, $interval) {
		var ctrl = this;
		var mymap;
		var bases;
		var ambulances = new Array();
		var incidents = new Array();
		var hospitalIcon = L.icon({
			iconUrl: 'hospital.png',

			iconSize: [30, 30], // size of the icon
			iconAnchor: [15, 15], // point of the icon which will correspond to marker's location
			popupAnchor: [-3, -76] // point from which the popup should open relative to the iconAnchor
		});
		var brandweerIcon = L.icon({
			iconUrl: 'brandweer.png',

			iconSize: [30, 30], // size of the icon
			iconAnchor: [15, 15], // point of the icon which will correspond to marker's location
			popupAnchor: [-3, -76] // point from which the popup should open relative to the iconAnchor
		});
		var incidentIcon = L.icon({
			iconUrl: 'incident.png',

			iconSize: [30, 30], // size of the icon
			iconAnchor: [15, 15], // point of the icon which will correspond to marker's location
			popupAnchor: [-3, -76] // point from which the popup should open relative to the iconAnchor
		});
		var ambulanceIcon = L.icon({
			iconUrl: 'ambulance.png',

			iconSize: [30, 30], // size of the icon
			iconAnchor: [15, 15], // point of the icon which will correspond to marker's location
			popupAnchor: [-3, -76] // point from which the popup should open relative to the iconAnchor
		});


		mymap = L.map('mapid').setView([50.92669, 5.342462], 13);
		L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {
			attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="http://mapbox.com">Mapbox</a>',
			maxZoom: 18,
			id: 'mapbox.streets',
			accessToken: 'pk.eyJ1IjoiYnZhbnZlbHNlbiIsImEiOiJjamYzMGg4NWgwdmVhMzJscjNpYjRmaWUxIn0.t5wgMW7Yzu5IgEWC9FjvUA'
		}).addTo(mymap);

		addHospitalsToMap();
		addFireDepartmentsToMap();

		function addHospitalsToMap() {
			BasesClient.getAllHospitals().then(function (value) {
				bases = value;
				for (i = 0; i < bases.length; i++) {
					L.marker([bases[i].location.lat, bases[i].location.lon], {icon: hospitalIcon, zIndexOffset:1000}).addTo(mymap);
				}
			});
		}
		function addFireDepartmentsToMap() {
			BasesClient.getAllFireDepartments().then(function (value) {
				bases = value;
				for (i = 0; i < bases.length; i++) {
					L.marker([bases[i].location.lat, bases[i].location.lon], {icon: brandweerIcon, zIndexOffset:1000}).addTo(mymap);
				}
			});
		}

		function addAmbulancesToMap() {
			for (i = 0; i < ambulances.length; i++) {
				mymap.removeLayer(ambulances[i]);
			}
			ambulances = [];

			var Allambulances = VehicleClient.getAllAmbulances();
			for (i = 0; i < Allambulances.length; i++) {
				const marker = L.marker([Allambulances[i].location.lat, Allambulances[i].location.lon], {icon: ambulanceIcon});
				ambulances.push(marker)
				marker.addTo(mymap)
			}
		}

		function removeAllIncidentMarkers() {
			for (i = 0; i < incidents.length; i++) {
				mymap.removeLayer(incidents[i]);
			}
			incidents = [];
		}

		function addAllIncidentMarkers() {
			var allIncidents = IncidentClient.getAllIncidents();
			for (i = 0; i < allIncidents.length; i++) {
				const marker = L.marker([allIncidents[i].location.lat, allIncidents[i].location.lon], {icon: incidentIcon});
				incidents.push(marker);
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

		ctrl.buyAmbulance = function () {
			VehicleClient.buyAmbulance();
			console.log('Buying ambulance');
		};

		ctrl.createIncident = function () {
			IncidentClient.createIncident();
		};

		ctrl.getAllIncidentsFromBackend = function () {
			IncidentClient.getAllIncidentsFromBackend();
		};

		IncidentClient

		$interval(function () {
			addIncidentsToMap();
			addAmbulancesToMap();
		}, 1000);
	});