angular.module('be.dispatcher.client.images.markers', [])
	.factory('ImagesMarkersClient', function () {

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
		var mugIcon = L.icon({
			iconUrl: 'mug.png',

			iconSize: [30, 30], // size of the icon
			iconAnchor: [15, 15], // point of the icon which will correspond to marker's location
			popupAnchor: [-3, -76] // point from which the popup should open relative to the iconAnchor
		});
		var rvIcon = L.icon({
			iconUrl: 'rv.png',

			iconSize: [30, 30], // size of the icon
			iconAnchor: [15, 15], // point of the icon which will correspond to marker's location
			popupAnchor: [-3, -76] // point from which the popup should open relative to the iconAnchor
		});
		var tsIcon = L.icon({
			iconUrl: 'ts.png',

			iconSize: [30, 30], // size of the icon
			iconAnchor: [15, 15], // point of the icon which will correspond to marker's location
			popupAnchor: [-3, -76] // point from which the popup should open relative to the iconAnchor
		});
		var policeIcon = L.icon({
			iconUrl: 'politie.gif',

			iconSize: [30, 30], // size of the icon
			iconAnchor: [15, 15], // point of the icon which will correspond to marker's location
			popupAnchor: [-3, -76] // point from which the popup should open relative to the iconAnchor
		});
		var policeCombiIcon = L.icon({
			iconUrl: 'police_combi.png',

			iconSize: [30, 30], // size of the icon
			iconAnchor: [15, 15], // point of the icon which will correspond to marker's location
			popupAnchor: [-3, -76] // point from which the popup should open relative to the iconAnchor
		});
		var policeInterceptorIcon = L.icon({
			iconUrl: 'police_interceptor.png',

			iconSize: [30, 30], // size of the icon
			iconAnchor: [15, 15], // point of the icon which will correspond to marker's location
			popupAnchor: [-3, -76] // point from which the popup should open relative to the iconAnchor
		});

		function getAmbulanceIcon() {
			return ambulanceIcon;
		}

		function getMugIcon() {
			return mugIcon;
		}

		function getIncidentIcon() {
			return incidentIcon;
		}

		function getBrandweerIcon() {
			return brandweerIcon;
		}

		function getHospitalIcon() {
			return hospitalIcon;
		}

		function getRVIcon() {
			return rvIcon;
		}

		function getTSIcon() {
			return tsIcon;
		}

		function getPoliceIcon() {
			return policeIcon;
		}

		function getPoliceInterceptorIcon() {
			return policeInterceptorIcon;
		}

		function getPoliceCombiIcon() {
			return policeCombiIcon;
		}

		return {
			getAmbulanceIcon: getAmbulanceIcon,
			getIncidentIcon: getIncidentIcon,
			getBrandweerIcon: getBrandweerIcon,
			getHospitalIcon: getHospitalIcon,
			getMugIcon: getMugIcon,
			getRVIcon: getRVIcon,
			getTSIcon: getTSIcon,
			getPoliceIcon: getPoliceIcon,
			getPoliceInterceptorIcon: getPoliceInterceptorIcon,
			getPoliceCombiIcon: getPoliceCombiIcon,
		};
	});