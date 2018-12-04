'use strict';

var pa165soccerManagerApp = angular.module('pa165soccerManagerApp', ['ngRoute', 'soccerManagerControllers']);
var soccerManagerControllers = angular.module('soccerManagerControllers', []);

pa165soccerManagerApp.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.
        when('/teams', {templateUrl: 'partials/manager.html', controller: 'TeamsCtrl'}).
        when('/teams/:teamId', {templateUrl: 'partials/team_detail.html', controller: 'TeamDetailCtrl'}).
        when('/matches', {templateUrl: 'partials/matchesList.html', controller: 'MatchesCtrl'}).
        otherwise({redirectTo: '/teams'});
    }]);

pa165soccerManagerApp.run(function ($rootScope,$http) {
    // alert closing functions defined in root scope to be available in every template
    $rootScope.hideSuccessAlert = function () {
        $rootScope.successAlert = undefined;
    };
    $rootScope.hideWarningAlert = function () {
        $rootScope.warningAlert = undefined;
    };
    $rootScope.hideErrorAlert = function () {
        $rootScope.errorAlert = undefined;
    };
    //change the HTTP Accept header globally to signal accepting the HAL format
    $http.defaults.headers.common.Accept = 'application/hal+json, */*';
});


soccerManagerControllers.controller('TeamsCtrl', function ($scope, $http) {
    console.log('calling  /soccer-manager/api/v1/teams/');
    $http.get('/soccer-manager/api/v1/teams/').then(function (response) {
        var teams = response.data['teams'];
        $scope.teams = teams;
        console.log('AJAX loaded all teams');
        // for (var i = 0; i < teams.length; i++) {
        //     var team = teams[i];
        //     var categoryProductsLink = category['_links'].products.href;
        //     loadCategoryProducts($http, category, categoryProductsLink);
        // }
    });
});

soccerManagerControllers.controller('TeamDetailCtrl',
    function ($scope, $routeParams, $http) {
        // get product id from URL fragment #/product/:productId
        var teamId = $routeParams.teamId;
        $http.get('/soccer-manager/api/v1/teams/' + teamId).then(function (response) {
            $scope.team = response.data;
            console.log('AJAX loaded detail of product ' + $scope.product.name);
        });
    });

soccerManagerControllers.controller('MatchesCtrl',
    function ($scope, $routeParams, $rootScope, $http) {			
		loadMatches($scope, $http);			
		
		//define method isAlreadyPlayed
		$scope.alreadyPlayed = function (match) {
			return alreadyPlayed(match);
		};
		//define method canBeSimulated
		$scope.canBeSimulated = function (match) {
			return canBeSimulated(match);
		}
		
		//define method simulateMatch
		$scope.simulateMatch = function(match) {
			console.log('simulateMatch: matchId = '+match.id);
		    $http.post(findHrefFromLinks(match.links, 'simulate')).then(
				function success(response) {
					console.log('simulated match ' + match.id + ' on server');

					$rootScope.successAlert = 'Simulated match "'+match.id+'"';
					$rootScope.errorAlert = null;
					
					//load new list of all products
					loadMatches($scope, $http);
				},
				function error(response) {
					console.log('server returned error');
				    $rootScope.successAlert = null;
					$rootScope.errorAlert = 'Cannot simulate match "'+match.id+'"!';
				});
			};
});


/* 
 * Matches functions
 */

function loadMatches($scope, $http) {
	$http.get('/pa165/matches/').then(function (response) {
		console.log('AJAX response.data: ' + response.data);
        $scope.matches = response.data.content;
        console.log('AJAX loaded list of ' + $scope.matches.length + 'matches.');
	});
}

function findHrefFromLinks(links, relName) {
	var i;
	for(i=0;i<links.length;i++) {
		if(links[i].rel == relName) return links[i].href;
	}
	return null;
}

function alreadyPlayed(match) {
	if(match.homeTeamGoals != null) return true;
	if(match.awayTeamGoals != null) return true;
	return false;
}
function canBeSimulated(match) {
	if(alreadyPlayed(match)) return false;
	
	var date = Date.parse(match.date);
	var now = new Date();
	
	if(now < date) return false;
	
	return true;
}


