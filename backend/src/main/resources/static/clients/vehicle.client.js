angular.module('be.dispatcher.client.vehicle', [])
	.factory('VehicleClient', function (Restangular, $interval) {

			const GET_VEHICLES_REFRESH_RATE_IN_MS = 1000;
			var allMedicalVehicles = {};
			var allMedicalVehiclesMap = {};
			var allFireTrucks = {};
			var allFireTrucksMap = {};
			var allPoliceVehicles = {};
			var allPoliceVehiclesMap = {};
			var refresh = true;

			function getAllMedicalVehicles() {
				return allMedicalVehicles;
			}

			function getAllMedicalVehiclesMap() {
				return allMedicalVehiclesMap;
			}

			function getAllFireTrucks() {
				return allFireTrucks;
			}

			function getAllFireTrucksMap() {
				return allFireTrucksMap;
			}

			function getAllPoliceVehicles() {
				return allPoliceVehicles;
			}

			function getAllPoliceVehiclesMap() {
				return allPoliceVehiclesMap;
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
				Restangular.one('/vehicles/medical_vehicles_per_station').get().then(function (vehicles) {
					syncVehiclesMap(vehicles.plain(), allMedicalVehiclesMap);
				});
				Restangular.all('/vehicles/fire_trucks').getList().then(function (vehicles) {
					syncVehicles(vehicles, allFireTrucks);
				});

				Restangular.one('/vehicles/fire_trucks_per_station').get().then(function (vehiclesMap) {
					syncVehiclesMap(vehiclesMap.plain(), allFireTrucksMap);
				});
				Restangular.all('/vehicles/police_vehicles').getList().then(function (vehicles) {
					syncVehicles(vehicles, allPoliceVehicles);
				});
				Restangular.one('/vehicles/police_vehicles_per_station').get().then(function (vehicles) {
					syncVehiclesMap(vehicles.plain(), allPoliceVehiclesMap);
				});
			}

			function syncVehiclesMap(vehiclesMap, localObject) {
				for (var key in vehiclesMap) {
					if (vehiclesMap.hasOwnProperty(key)) {
						if (!localObject[key]) {
							localObject[key] = vehiclesMap[key];
						} else {
							syncVehicles(vehiclesMap[key], localObject[key])
						}
					}
				}
			}

			function syncVehicles(vehicles, localObject) {
				for (i = 0; i < vehicles.length; i++) {
					if (!localObject[i]) {
						localObject[i] = vehicles[i];
					} else {
						localObject[i].location = vehicles[i].location;
						localObject[i].vehicleStatus = vehicles[i].vehicleStatus;
						localObject[i].filled = vehicles[i].filled;
						localObject[i].incident = vehicles[i].incident;
					}
				}
			}

			return {
				getAllMedicalVehicles: getAllMedicalVehicles,
				getAllMedicalVehiclesMap: getAllMedicalVehiclesMap,
				getAllFireTrucks: getAllFireTrucks,
				getAllFireTrucksMap: getAllFireTrucksMap,
				getAllPoliceVehicles: getAllPoliceVehicles,
				getAllPoliceVehiclesMap: getAllPoliceVehiclesMap,
				sendVehicleToIncident: sendVehicleToIncident,
				goToNearestHospital: goToNearestHospital,
				toggleRefresh: toggleRefresh
			};
		}
	);