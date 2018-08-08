angular.module('be.dispatcher.pages.medical', ['restangular'])
	.config(function ($stateProvider) {
		var medicalState = {
			name: 'medical',
			url: '/medical',
			templateUrl: 'pages/medical/medical.pages.html',
			controller: 'MedicalController',
			controllerAs: 'ctrl'
		};

		$stateProvider.state(medicalState);
	})
	.controller('MedicalController', function ($scope, VehicleClient) {
		var ctrl = this;

		ctrl.getAllMedicalVehicles = function () {
			return VehicleClient.getAllMedicalVehiclesMap();
		}
	});