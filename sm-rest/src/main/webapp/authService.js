//  Authentication service. Wrapped in an IIFE to avoid global variables
//  Purpose: To handle all user authentication methods



var AuthenticationService = function($http, $cookies, $rootScope, $timeout) {

    //  Function defined for when the user login is initiate
    var Login = function (username, password, callback) {
        var user = {
            'username': username,
            'rawPassword': password
        };
        
        $http({
            method: 'POST',
            url: 'http://localhost:8080/pa165/users/auth',
            data: user
        }).then(function success(response) {
            if (response.data === "") {
                console.log('Error auth!');
                response.message = 'Password is incorrect';
                callback(response);
            } else {
                console.log('Success!');
                var status = { success: response };
                callback(status);
            }

        }, function error(response) {
            response.message = 'Username is incorrect';
            console.log('Error throw!');
            callback(response);
        });

    };

    //  Sets the cookie and the state to logged in
    var SetCredentials = function (username, data) {
        $rootScope.globals = {
            currentUser: {
                username: username,
                data: data
            }
        };

        $http.defaults.headers.common['Authorization'] = 'Basic ' + data;
        $cookies.put('globals', $rootScope.globals);
    };

    //  Clears the cookie and the state for the application to recognise a logged out state
    var ClearCredentials = function () {
        $rootScope.globals = {};
        $cookies.remove('globals');
        $http.defaults.headers.common.Authorization = 'Basic ';
    };


    return {
        Login: Login,
        SetCredentials: SetCredentials,
        ClearCredentials: ClearCredentials
    };

};

//  Register the service with the application
var module = angular.module("SoccerManagerApp");
module.factory("AuthenticationService", AuthenticationService)

