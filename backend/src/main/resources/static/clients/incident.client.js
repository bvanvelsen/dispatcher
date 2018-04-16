angular.module('be.dispatcher.client.incident', [])
	.factory('IncidentClient', function (Restangular, $interval) {

		var allIncidents = {};
		const GET_INCIDENTS_REFRESH_RATE_IN_MS = 1000;

		function getAllIncidents() {
			return allIncidents;
		}

		function createIncident() {
			return Restangular.all('/incidents/create').post();
		}


		function getAllIncidentsFromBackend() {
			Restangular.all('/incidents/allIncidents').getList().then(function (incidents) {
				if (incidents.length != allIncidents.length) {
					allIncidents = {};
				}
				for (i = 0; i < incidents.length; i++) {
					if (!allIncidents[incidents[i].id]) {
						allIncidents[incidents[i].id] = incidents[i];
					}
					else {
						allIncidents[incidents[i].id].fireTasks = incidents[i].fireTasks;
						allIncidents[incidents[i].id].medicalTasks = incidents[i].medicalTasks;
						allIncidents[incidents[i].id].policeTasks = incidents[i].policeTasks;
					}

				}
			});
		}

		$interval(function () {
			getAllIncidentsFromBackend();
		}, GET_INCIDENTS_REFRESH_RATE_IN_MS);

		return {
			createIncident: createIncident,
			getAllIncidents: getAllIncidents,
			getAllIncidentsFromBackend:getAllIncidentsFromBackend
		};
	});