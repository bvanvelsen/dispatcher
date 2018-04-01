angular.module('be.dispatcher.client.vehicle', [])
	.factory('VehicleClient', function (Restangular, $interval) {

		const GET_VEHICLES_REFRESH_RATE_IN_MS = 1000;
		var allAmbulances;
		var allMugs;
		var refresh = true;

		function getAllAmbulances() {
			return allAmbulances;
		}

		function getAllMugs() {
			return allAmbulances;
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
			Restangular.all('/vehicles/ambulances/all').getList().then(function (vehicles) {
				allAmbulances = vehicles;
			});
			Restangular.all('/vehicles/mugs/all').getList().then(function (vehicles) {
				allMugs = vehicles;
			});
		}

		return {
			getAllAmbulances: getAllAmbulances,
			getAllMugs: getAllMugs,
			sendVehicleToIncident: sendVehicleToIncident,
			goToNearestHospital: goToNearestHospital,
			toggleRefresh: toggleRefresh
		};
	});