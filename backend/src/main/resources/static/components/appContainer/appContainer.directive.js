angular.module('be.dispatcher.component.appContainer', [])
    .component('appContainer', {
    templateUrl: 'components/appContainer/appContainer-template.html',
    controller: AppContainerController
});

function AppContainerController(VehicleClient) {
    var ctrl = this;

    function getAllVehilces() {
		ctrl.allVehicles = VehicleClient.getAllVehicles();
    }

    getAllVehilces();
}

