angular.module('be.dispatcher.config.rest', ['restangular'])
    .config(['RestangularProvider', function (RestangularProvider) {
            RestangularProvider.setBaseUrl('api');
        RestangularProvider.setDefaultHeaders({
            'Content-Type': 'application/json',
            'X-Requested-With': 'XMLHttpRequest'
        });
    }]).run(function(Restangular, $http) {

    });