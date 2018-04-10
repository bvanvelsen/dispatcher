angular.module('be.dispatcher.directive.vehicle', [])
	.directive('emergencyVehicle', function(VehicleClient, IncidentClient) {
		return {
			restrict: 'E',
			scope: {
				vehicles: '=vehicles'
			},
			controller: function () {
				var ctrl = this;

				ctrl.getAllIncidents = function () {
					return IncidentClient.getAllIncidents();
				};

				ctrl.sendVehicleToIncident = function (vehicleId, incidentId) {
					VehicleClient.sendVehicleToIncident(vehicleId, incidentId);
				};

				ctrl.disableDispatch = function(vehicle) {
					return vehicle.vehicleStatus !== 'AT_BASE'
				}
			},
			templateUrl: 'directives/vehicleDirective.html',
			controllerAs: 'ctrl',
			bindToController: true
		};
	});