

var app = angular.module('GamePool', []);

/*
app.factory('mySharedService', function($rootScope) {
    var sharedService = {};

    sharedService.getQuery = '';

    sharedService.prepForBroadcast = function(msg) {
        this.getQuery = msg;
        this.broadcastItem();
    };

    sharedService.broadcastItem = function() {
        $rootScope.$broadcast('handleBroadcast');
    };

    return sharedService;
}); */

app.controller('NavCtrl', function($scope){
	$scope.tab = 'find';
	$scope.setTab = function(tabName){
		$scope.tab = tabName;
	}
	$scope.$on('setTabInNavCtrl', function(event, args) {
		$scope.setTab(args.tabName);
	});

});


app.controller('FindCtrl', function($scope, $http, $rootScope){

    function init(){
        $scope.loadGames();
    };

    $scope.loadGames = function(){
        $http({
            url: "/game/all",
            method: "GET"//,
            //data: {"foo":"bar"}
        }).success(function(data, status, headers, config) {
            //console.log("Success: "+data);
            $scope.games = data;
        }).error(function(data, status, headers, config) {
            console.log("Error: " + data);
            alert("Error");
            //$scope.status = status;
        });
    }

    $scope.games = [];

//	$scope.games = [
//		{name:'Age Of Empires', image:'image1.png', owner:'John', distance:'1.5'},
//		{name:'Roller Coaster Tycoon', image:'image2.png', owner:'Alex', distance:'2.5'},
//		{name:'Prince of Persia', image:'image3.jpg', owner:'Crazy', distance:'0.5'},
//		{name:'Midtown Madness', image:'image4.jpg', owner:'John', distance:'7'}
//	];

	$scope.getResultsCount = function(){
		return $scope.games.length;
	};
	$scope.requestGame = function(game){
		//alert("Requesting game." + game.name + game.owner);
		$rootScope.name=game;
		//sharedService.prepForBroadcast(game);
		//$scope.games.push({name:game.name, image:game.image, owner:game.owner, distance:game.distance});
		alert(game.name + " is rented, Check your borrowed games.");
		//$scope.newGameName = '';
	};

    // Call the constructor
	init();
});

app.controller('PostedCtrl', function($scope, $http){
	$scope.games = [
		{name:'Age Of Empires', image:'image1.png', location:'Sunnyvale, CA', views:'2', requests:'0'},
		{name:'Prince of Persia', image:'image2.png', location:'Sunnyvale, CA', views:'5', requests:'1'}
	];
	$scope.addGame = function(){

        $http({
            url: "/game/new",
            method: "POST",
            data: {title:$scope.newGameName, console:"XBox", year:"2020", image:$scope.newGamePic}
        }).success(function(data, status, headers, config) {
            //console.log("Success: "+data);
            alert("Success: Game inserted with id "+data);
            $scope.games.push({name:$scope.newGameName, image:$scope.newGamePic, location:'Sunnyvale, CA', views:'0', requests:'0'});
            $scope.newGameName = '';
            $scope.$emit('setTabInNavCtrl', {tabName:'posted'});
        }).error(function(data, status, headers, config) {
            console.log("Error: " + data);
            alert("Error");
            //$scope.status = status;
        });

	};
});

app.controller('RentedCtrl', function($scope, $rootScope){
	$scope.rentedtab = 'owned';
	$scope.borrowedGames = [
		{name:'Age Of Empires', image:'image1.png', location:'Sunnyvale, CA', views:'2', requests:'0'},
		{name:'Prince of Persia', image:'image2.png', location:'Sunnyvale, CA', views:'5', requests:'1'}
	];
	$scope.setRentedTab = function(rentedTabName){
		//alert("Rented Tab. " + $rootScope.name.owner);
		$scope.rentedtab = rentedTabName;
		$scope.borrowedGames.push({name:$rootScope.name.name, image:$rootScope.name.image,
									location:'Mountain View, CA', views:'2', requests:'0'});
	};
});



