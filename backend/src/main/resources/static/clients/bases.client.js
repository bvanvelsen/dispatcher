angular.module('be.dispatcher.client.bases', [])
	.factory('BasesClient', function (Restangular) {

		function getAllBases() {
			return Restangular.all('bases').getList().then(function (allBases) {
				return allBases;
			});
		}

		return {
			getAllBases: getAllBases
		};
	});