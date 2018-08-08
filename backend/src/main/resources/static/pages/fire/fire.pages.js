angular.module('be.dispatcher.pages.fire', ['restangular'])
	.config(function ($stateProvider) {
		var fireState = {
			name: 'fire',
			url: '/fire',
			templateUrl: 'pages/fire/fire.pages.html',
			controller: 'FireController',
			controllerAs: 'ctrl'
		};

		$stateProvider.state(fireState);
	})
	.controller('FireController', function ($scope, VehicleClient) {
		var ctrl = this;

		ctrl.getAllFireTrucks = function () {
			return VehicleClient.getAllFireTrucksMap();
		};
	});