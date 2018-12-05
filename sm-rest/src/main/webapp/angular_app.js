'use strict';

var pa165soccerManagerApp = angular.module('pa165soccerManagerApp', ['ngRoute', 'soccerManagerControllers']);
var soccerManagerControllers = angular.module('soccerManagerControllers', []);

pa165soccerManagerApp.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.
        when('/teams', {templateUrl: 'partials/manager.html', controller: 'TeamsCtrl'}).
        when('/teams/:teamId', {templateUrl: 'partials/team_detail.html', controller: 'TeamDetailCtrl'}).
        when('/matches', {templateUrl: 'partials/matchesList.html', controller: 'MatchesCtrl'}).
        when('/matches/create', {templateUrl: 'partials/matchCreate.html', controller: 'MatchCreateCtrl'}).
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
			
			//define method delete
			$scope.deleteMatch = function(match) {
				console.log('deleteMatch: matchId = '+match.id);
			    $http.delete(findHrefFromLinks(match.links, 'delete')).then(
					function success(response) {
						console.log('delete match ' + match.id + ' on server');

						$rootScope.successAlert = 'Deleted match "'+match.id+'"';
						$rootScope.errorAlert = null;
						
						//load new list of all products
						loadMatches($scope, $http);
					},
					function error(response) {
						console.log('server returned error');
					    $rootScope.successAlert = null;
						$rootScope.errorAlert = 'Cannot delete match "'+match.id+'"!';
					});
				};
});

soccerManagerControllers.controller('MatchCreateCtrl',
	    function ($scope, $routeParams, $http, $location, $rootScope) {
	        //set object bound to form fields
	        var now = new Date();
	        now = new Date(now.getFullYear(), now.getMonth(), now.getDate(), now.getHours(), 0);
	        var min = new Date(now);
	        min.setFullYear(min.getFullYear() - 5);
	        var max = new Date(now);
	        max.setFullYear(max.getFullYear() + 5);
	        
	        $scope.match = {
	        	'date': now,
	        };
	        
	        $scope.minMatchDate = min;
	        $scope.maxMatchDate = max;
	        
	        console.log('match create cntrl');
	        
        	loadTeams($scope, $http);

	        // function called when submit button is clicked, creates match on server
	        $scope.create = function (match) {      	
	            $http({
	                method: 'POST',
	                url: '/pa165/matches',
	                data: match
	            }).then(function success(response) {
	                console.log('created match');
	                var createdMatch = response.data;
	                //display confirmation alert
	                $rootScope.successAlert = 'A new match #'+createdMatch.id+' was created';
	                //change view to list of categories
	                $location.path("/matches");
	            }, function error(response) {
	                //display error
	            	$rootScope.errorAlert = 'Cannot create match !';
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
        sortMatchesByDate($scope.matches);
	});
}

function loadTeams($scope, $http) {
	$http.get('/pa165/teams/').then(function (response) {
		console.log('AJAX response.data: ' + response.data);
        $scope.teams = response.data.content;
        console.log('AJAX loaded list of ' + $scope.teams.length + 'teams.');
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
	
	var date = dateTimeStrToDate(match.date);
	var now = new Date();
	
	if(now < date) return false;
	
	return true;
}

function sortMatchesByDate(matches) {
	matches.sort(compareMatchesByDate);
}

function compareMatchesByDate(m1, m2) {
	var d1 = dateTimeStrToDate(m1.date);
	var d2 = dateTimeStrToDate(m2.date);
	
	if (d1 > d2) return 1;
	if (d1 < d2) return -1;
	return 0;
}

function dateTimeStrToDate(dateTimeString) {
	return Date.parse(dateTimeString);
}

