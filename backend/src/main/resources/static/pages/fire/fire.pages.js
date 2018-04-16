angular.module('be.dispatcher.pages.fire', ['restangular'])
	.config(function ($stateProvider) {
		var fireState = {
			name: 'fire',
			url: '/fire',
			templateUrl: 'pages/fire/fire.pages.html',
			controller: 'FireController',
			controllerAs: 'ctrl'
		}

		$stateProvider.state(fireState);
	})
	.controller('FireController', function ($scope, VehicleClient, IncidentClient) {
		var ctrl = this;

		ctrl.getAllFireTrucks = function () {
			return VehicleClient.getAllFireTrucks();
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

	});