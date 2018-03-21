angular.module('be.dispatcher.application', [
    'restangular',
    'ui.bootstrap',
	'ui.router',
    'pascalprecht.translate',
    'ngSanitize',
    'ngCookies',
    'ngAnimate',
    'angular-md5',
    'be.dispatcher.component.appContainer',
    'be.dispatcher.client.vehicle',
    'be.dispatcher.client.incident',
	'be.dispatcher.config.rest',
	'be.dispatcher.pages.vehicleshop',
	'be.dispatcher.pages.dashboard',
	'be.dispatcher.pages.incident',
	'be.dispatcher.component.ambulanceplaces',
	'be.dispatcher.client.bases',
])
	.controller('ApplicationCtrl', function($scope, $interval) {
		var tick = function() {
			$scope.clock = Date.now();
		}
		$interval(tick, 1000);
	});
