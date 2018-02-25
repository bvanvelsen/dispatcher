angular.module('be.dispatcher.pages.dashboard', ['restangular'])
	.config(function ($stateProvider) {
		var dashboardState = {
			name: 'dashboard',
			url: '/dashboard',
			templateUrl: 'pages/dashboard/dashboard.pages.html',
			controller: 'DashboardController',
			controllerAs: 'ctrl'
		}

		$stateProvider.state(dashboardState);
	})
	.controller('DashboardController', function ($scope, VehicleClient, IncidentClient) {
	var ctrl = this;

		ctrl.getAllVehicles = function() {
			return VehicleClient.getAllVehicles();
		}

		ctrl.getAllIncidents = function() {
			return IncidentClient.getAllIncidents();
		}

		ctrl.createIncident = function () {
			IncidentClient.createIncident();
		};

		ctrl.sendVehicleToIncident = function(vehicleId, incidentId) {
			VehicleClient.sendVehicleToIncident(vehicleId, incidentId);
		}
});