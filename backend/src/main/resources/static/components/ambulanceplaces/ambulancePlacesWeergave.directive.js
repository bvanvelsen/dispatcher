angular.module('be.dispatcher.component.ambulanceplaces', [])
	.directive('ambulancePlacesWeergave', function () {
		return {
			scope: {
				vehicle: '='
			},
			templateUrl: 'components/ambulanceplaces/ambulancePlacesWeergave-template.html',
			controllerAs: 'ctrl',
			controller: 'ambulanceplacesWeergaveController',
			bindToController: true
		}
}).controller('ambulanceplacesWeergaveController', function () {
	var ctrl = this;


});