angular.module('be.dispatcher.client.incident', [])
	.factory('IncidentClient', function (Restangular, $interval) {

		var allIncidents;
		const GET_INCIDENTS_REFRESH_RATE_IN_MS = 3000;

		function getAllIncidents() {
			return allIncidents;
		}

		function createIncident() {
			return Restangular.all('/incidents/create').post();
		}


		function getAllIncidentsFromBackend() {
			console.log("get all incidents from backend");
			Restangular.all('/incidents/all').getList().then(function (incidents) {
				allIncidents = incidents;
			});
		}

		$interval(function () {
			getAllIncidentsFromBackend();
		}, GET_INCIDENTS_REFRESH_RATE_IN_MS);

		return {
			createIncident: createIncident,
			getAllIncidents: getAllIncidents
		};
	});