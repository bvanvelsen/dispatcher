angular.module('be.dispatcher.pages.police', ['restangular'])
	.config(function ($stateProvider) {
		var policeState = {
			name: 'police',
			url: '/police',
			templateUrl: 'pages/police/police.pages.html',
			controller: 'PoliceController',
			controllerAs: 'ctrl'
		}

		$stateProvider.state(policeState);
	})
	.controller('PoliceController', function ($scope, VehicleClient, IncidentClient, $interval) {
		var ctrl = this;

		ctrl.getAllPoliceVehicles = function () {
			return VehicleClient.getAllPoliceVehicles();
		}

		ctrl.getAllIncidents = function () {
			return IncidentClient.getAllIncidents();
		}

		ctrl.createIncident = function () {
			IncidentClient.createIncident();
		};

		ctrl.sendVehicleToIncident = function (vehicleId, incidentId) {
			VehicleClient.sendVehicleToIncident(vehicleId, incidentId);
		}

		ctrl.goToNearestHospital = function (vehicleId) {
			VehicleClient.goToNearestHospital(vehicleId);
		}

		ctrl.getTimes = function (n) {
			return new Array(n);
		};

		$interval(function () {

		}, 1000);

	});