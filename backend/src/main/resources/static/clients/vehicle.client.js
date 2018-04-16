angular.module('be.dispatcher.client.vehicle', [])
	.factory('VehicleClient', function (Restangular, $interval) {

			const GET_VEHICLES_REFRESH_RATE_IN_MS = 1000;
			var allMedicalVehicles = {};
			var allFireTrucks = {};
			var allPoliceVehicles = {};
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
				Restangular.all('/vehicles/medical_vehicles').getList().then(function (vehicles) {
					syncVehicles(vehicles, allMedicalVehicles);
				});
				Restangular.all('/vehicles/fire_trucks').getList().then(function (vehicles) {
					syncVehicles(vehicles, allFireTrucks);
				});
				Restangular.all('/vehicles/police_vehicles').getList().then(function (vehicles) {
					syncVehicles(vehicles, allPoliceVehicles);
				});
			}

			function syncVehicles(vehicles, localObject) {
				for (i = 0; i < vehicles.length; i++) {
					if (!localObject[vehicles[i].id]) {
						localObject[vehicles[i].id] = vehicles[i];
					} else {
						localObject[vehicles[i].id].location = vehicles[i].location;
						localObject[vehicles[i].id].vehicleStatus = vehicles[i].vehicleStatus;
						localObject[vehicles[i].id].filled = vehicles[i].filled;
						localObject[vehicles[i].id].incident = vehicles[i].incident;
					}
				}
			}

			return {
				getAllMedicalVehicles: getAllMedicalVehicles,
				getAllFireTrucks: getAllFireTrucks,
				getAllPoliceVehicles: getAllPoliceVehicles,
				sendVehicleToIncident: sendVehicleToIncident,
				goToNearestHospital: goToNearestHospital,
				toggleRefresh: toggleRefresh
			};
		}
	);