angular.module('be.dispatcher.client.vehicle', [])
	.factory('VehicleClient', function (Restangular, $interval) {

		const GET_VEHICLES_REFRESH_RATE_IN_MS = 1000;
		var allMedicalVehicles;
		var allFireTrucks;
		var allPoliceVehicles;
		var refresh = true;

		function getAllMedicalVehicles() {
			return allMedicalVehicles;
		}

		function getAllFireTrucks() {
			return allFireTrucks;
		}

		function getAllPoliceVehicles() {
			return allPoliceVehicles;
		}

		function sendVehicleToIncident(vehicleId, incidentId) {
			return Restangular.one('vehicles', vehicleId).one('sendTo', incidentId).post();
		}

		function goToNearestHospital(vehicleId) {
			return Restangular.one('vehicles', vehicleId).one('goToHospital').put();
		}

		function toggleRefresh() {
			refresh = !refresh;
		}


		$interval(function () {
			if (refresh) {
				getAllVehiclesFromBackend();
			}
		}, GET_VEHICLES_REFRESH_RATE_IN_MS);

		function getAllVehiclesFromBackend() {
			Restangular.all('/vehicles/medical_vehicles').getList().then(function (medicalVehicles) {
				allMedicalVehicles = medicalVehicles;
			});
			Restangular.all('/vehicles/fire_trucks').getList().then(function (fireTrucks) {
				allFireTrucks = fireTrucks;
			});
			Restangular.all('/vehicles/police_vehicles').getList().then(function (policeVehicles) {
				allPoliceVehicles = policeVehicles;
			});
		}

		return {
			// getAllAmbulances: getAllAmbulances,
			// getAllMugs: getAllMugs,
			getAllMedicalVehicles:getAllMedicalVehicles,
			getAllFireTrucks:getAllFireTrucks,
			getAllPoliceVehicles:getAllPoliceVehicles,
			sendVehicleToIncident: sendVehicleToIncident,
			goToNearestHospital: goToNearestHospital,
			toggleRefresh: toggleRefresh
		};
	});