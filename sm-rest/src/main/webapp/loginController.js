//  Login Controller. Wrapped in an IIFE to avoid global variables
//  Purpose: Hides the badgers?


var app = angular.module("SoccerManagerApp");

var LoginController = function($scope, $rootScope, $location, AuthenticationService) {

    // Reset the login status before we start
    AuthenticationService.ClearCredentials();
    $rootScope.showMenu = false;
    $scope.login = function (username, password) {
        $scope.dataLoading = true;
        var status = AuthenticationService.Login(username, password);
        
            if(status.success) {
                AuthenticationService.SetCredentials(username, password); //plus token
                $rootScope.showMenu = true;
                $location.path('/');
            } else {
                $scope.error = status.message;
                $scope.dataLoading = false;
            }
        };
    };


app.controller("LoginController", LoginController);
