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
	.controller('PoliceController', function ($scope, VehicleClient) {
		var ctrl = this;

		ctrl.getAllPoliceVehicles = function () {
			return VehicleClient.getAllPoliceVehiclesMap();
		}
	});