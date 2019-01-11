//  Login Controller. Wrapped in an IIFE to avoid global variables
//  Purpose: Hides the badgers?


var app = angular.module("SoccerManagerApp");

var LoginController = function($scope, $rootScope, $location, AuthenticationService) {

    // Reset the login status before we start
    // AuthenticationService.ClearSessionInfo();
    $rootScope.showMenu = false;
    $scope.login = function (username, password) {
        $scope.dataLoading = true;
        AuthenticationService.Login(username, password, function(status) {
            if(status.success) {
                AuthenticationService.SetSessionInfo(status.username, status.admin);
                $rootScope.showMenu = true;
                console.log('currentUser: '+JSON.stringify($rootScope.globals.currentUser));
                $location.path('/');
            } else {
                $scope.error = status.message;
                $scope.dataLoading = false;
            }
            $scope.dataLoading = false;
        });
    };
};

app.controller("LoginController", LoginController);