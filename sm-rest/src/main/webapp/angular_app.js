'use strict';

var pa165soccerManagerApp = angular.module('pa165soccerManagerApp', ['ngRoute', 'soccerManagerControllers']);
var soccerManagerControllers = angular.module('soccerManagerControllers', []);

pa165soccerManagerApp.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.
        when('/teams', {templateUrl: 'partials/manager.html', controller: 'TeamsCtrl'}).
        when('/teams/:teamId', {templateUrl: 'partials/team_detail.html', controller: 'TeamDetailCtrl'}).
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
