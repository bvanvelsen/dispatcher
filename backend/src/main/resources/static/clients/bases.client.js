angular.module('be.dispatcher.client.bases', [])
	.factory('BasesClient', function (Restangular) {

		function getAllBases() {
			return Restangular.all('bases').getList().then(function (allBases) {
				return allBases;
			});
		}

		function getAllHospitals() {
			return Restangular.all('bases/hospitals').getList().then(function (allBases) {
				return allBases;
			});
		}

		function getAllFireDepartments() {
			return Restangular.all('bases/fire_departments').getList().then(function (allBases) {
				return allBases;
			});
		}

		function getAllPoliceStations() {
			return Restangular.all('bases/police_stations').getList().then(function (allBases) {
				return allBases;
			});
		}


		return {
			getAllBases: getAllBases,
			getAllHospitals:getAllHospitals,
			getAllFireDepartments:getAllFireDepartments,
			getAllPoliceStations:getAllPoliceStations
		};
	});