angular.module('be.dispatcher.pages.vehicleshop', ['restangular'])
	.config(function ($stateProvider) {
		var shopState = {
			name: 'shop',
			url: '/shop',
			templateUrl: 'pages/vehicleshop/vehicleshop.pages.html',
			controller: 'VehicleshopController',
			controllerAs: 'ctrl'
		}

		$stateProvider.state(shopState);
	})
	.controller('VehicleshopController', function (VehicleClient, BasesClient, $interval) {
		var ctrl = this;

		var bases;
		var elem = document.getElementById('myCanvas');
		var params = {width: 2000, height: 2000};
		var two = new Two(params).appendTo(elem);

		$interval(function () {
			init();
		}, 1000);

		function displayBases() {
			if (bases) {
				for (var i = 0; i < bases.length; i++) {
					var para = document.createElement("i");
					para.className += " fas fa-hospital-symbol";
					para.style.position = "absolute";
					para.style.left = bases[i].location.x + 'px';
					para.style.top = bases[i].location.y + 'px';
					elem.appendChild(para);
				}
			}
		}

		function displayVehicles() {
			var allVehicles = VehicleClient.getAllVehicles();
			if (allVehicles) {
			for (var i = 0; i < allVehicles.length; i++) {
				var circle = two.makeCircle(allVehicles[i].location.x, allVehicles[i].location.y, 5);
				circle.fill = '#FF8000';
				circle.stroke = 'orangered'; // Accepts all valid css color
				circle.linewidth = 5;
			}

			}
		}

		function init() {
			two.clear();

			displayBases();
			displayVehicles();

			two.update();
		}

		ctrl.loadBases = function () {
			BasesClient.getAllBases().then(function (value) {
				bases = value;
			});
			console.log('load bases');

		}

		ctrl.buyAmbulance = function () {
			VehicleClient.buyAmbulance();
			console.log('Buying ambulance');
		};
	});