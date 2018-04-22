angular.module('be.dispatcher.client.bases', [])
	.factory('BasesClient', function (Restangular) {

		function getAllBases() {
			return Restangular.all('bases').getList().then(function (allBases) {
				return allBases;
			});
		}

		function getAllHospitals() {
			return Restangular.all('bases/HOSPITAL').getList().then(function (allBases) {
				return allBases;
			});
		}

		function getAllFireDepartments() {
			return Restangular.all('bases/FIRE_DEPARTMENT').getList().then(function (allBases) {
				return allBases;
			});
		}

		function getAllAmbulanceStations() {
			return Restangular.all('bases/AMBULANCE_STATION').getList().then(function (allBases) {
				return allBases;
			});
		}

		function getAllPoliceStations() {
			return Restangular.all('bases/POLICE_STATION').getList().then(function (allBases) {
				return allBases;
			});
		}

		return {
			getAllBases: getAllBases,
			getAllHospitals:getAllHospitals,
			getAllFireDepartments:getAllFireDepartments,
			getAllPoliceStations:getAllPoliceStations,
			getAllAmbulanceStations:getAllAmbulanceStations,
		};
	});