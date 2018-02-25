angular.module('be.dispatcher.pages.vehicleshop', ['restangular'])
	.config(function ($stateProvider) {
		var shopState = {
			name: 'shop',
			url: '/shop',
			templateUrl: 'pages/vehicleshop/vehicleshop.pages.html',
			controller: 'VehicleshopController',
			controllerAs: 'ctrl'
		}

		$stateProvider.state(shopState);
	})
	.controller('VehicleshopController', function (VehicleClient) {
		var ctrl = this;

		ctrl.buyAmbulance = function () {
			VehicleClient.buyAmbulance();
			console.log('Buying ambulance');
		};
	});