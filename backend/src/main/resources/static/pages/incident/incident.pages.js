angular.module('be.dispatcher.pages.incident', ['restangular'])
	.config(function ($stateProvider) {
		var incidentState = {
			name: 'incident',
			url: '/incident',
			templateUrl: 'pages/incident/incident.pages.html',
			controller: 'IncidentController',
			controllerAs: 'ctrl'
		}

		$stateProvider.state(incidentState);
	})
	.controller('IncidentController', function ($scope, IncidentClient) {
	var ctrl = this;

		ctrl.getAllIncidents = function() {
			const allIncidents = IncidentClient.getAllIncidents();
			return allIncidents;
		}

ctrl.createIncident = function () {
	IncidentClient.createIncident();
};
})
;