angular.module('be.dispatcher.client.images.markers', [])
	.factory('ImagesMarkersClient', function (Restangular) {

		function getAllBases() {
			return Restangular.all('bases').getList().then(function (allBases) {
				return allBases;
			});
		}


		return {
			getAllBases: getAllBases
		};
	});