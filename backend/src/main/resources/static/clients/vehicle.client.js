angular.module('be.dispatcher.client.vehicle', [])
	.factory('VehicleClient', function (Restangular, $interval) {

		const GET_VEHICLES_REFRESH_RATE_IN_MS = 3000;
		var allVehicles;

		function getAllVehicles() {
			return allVehicles;
		}
		function buyAmbulance() {

			return Restangular.all('/vehicles/create/ambulance').post();
		}
		function sendVehicleToIncident(vehicleId, incidentId) {

			return Restangular.one('vehicles', vehicleId).one('sendTo', incidentId).post();
		}


		$interval(function () {
			getAllVehiclesFromBackend();
		}, GET_VEHICLES_REFRESH_RATE_IN_MS);

		function getAllVehiclesFromBackend() {
			console.log("get all vehicles from backend")
			Restangular.all('/vehicles/all').getList().then(function(vehicles) {
				allVehicles = vehicles;
			});
		}

		return {
			getAllVehicles: getAllVehicles,
			buyAmbulance: buyAmbulance,
			sendVehicleToIncident: sendVehicleToIncident
		};
	});