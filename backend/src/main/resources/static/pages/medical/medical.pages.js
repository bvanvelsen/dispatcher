angular.module('be.dispatcher.pages.medical', ['restangular'])
	.config(function ($stateProvider) {
		var medicalState = {
			name: 'medical',
			url: '/medical',
			templateUrl: 'pages/medical/medical.pages.html',
			controller: 'MedicalController',
			controllerAs: 'ctrl'
		}

		$stateProvider.state(medicalState);
	})
	.controller('MedicalController', function ($scope, VehicleClient, IncidentClient, $interval) {
		var ctrl = this;

		ctrl.getAllMedicalVehicles = function () {
			return VehicleClient.getAllMedicalVehicles();
		}

		ctrl.toggleRefresh = function () {
			VehicleClient.toggleRefresh();
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