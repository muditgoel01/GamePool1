
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


app.controller('FindCtrl', function($scope, $rootScope){
	$scope.games = [
		{name:'Age Of Empires', image:'image1.png', owner:'John', distance:'1.5'},
		{name:'Roller Coaster Tycoon', image:'image2.png', owner:'Alex', distance:'2.5'},
		{name:'Prince of Persia', image:'image3.jpg', owner:'Crazy', distance:'0.5'},
		{name:'Midtown Madness', image:'image4.jpg', owner:'John', distance:'7'}
	];
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
});

app.controller('PostedCtrl', function($scope){
	$scope.games = [
		{name:'Age Of Empires', image:'image1.png', location:'Sunnyvale, CA', views:'2', requests:'0'},
		{name:'Prince of Persia', image:'image2.png', location:'Sunnyvale, CA', views:'5', requests:'1'}
	];
	$scope.addGame = function(){
		$scope.games.push({name:$scope.newGameName, image:$scope.newGamePic, location:'Sunnyvale, CA', views:'0', requests:'0'});
		$scope.newGameName = '';
		$scope.$emit('setTabInNavCtrl', {tabName:'posted'});
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



