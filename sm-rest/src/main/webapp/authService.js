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
                var status = {
               		success: false,
                   	message: 'Password is incorrect'
                };
                callback(status);                 	
            } else {
                console.log('Success!');
                var status = { 
                	success: true,
                	message: 'Successfully logged in',
                	token: response.data.token,
                	username: response.data.user.username,
                	admin: response.data.user.admin
                };
                callback(status);                 	
            }

        }, function error(response) {
            console.log('Error throw!');
            var status = {
               	success: false,
                message: 'Username is incorrect'
            };
            callback(status);                 	
        });

    };

    
    var SetSessionInfo = function(token, username, admin) {
    	$rootScope.globals = {
           currentUser: {
        	   'username': username,
               'sessionToken': token,
               'admin': admin
           }
    	};
    	console.log('token: '+token);
    	$http.defaults.headers.common['Authorization'] = 'Bearer ' + token;
        $cookies.put('globals', $rootScope.globals);
    };

    //  Clears the cookie and the state for the application to recognise a logged out state
    var ClearSessionInfo = function () {
        $rootScope.globals = {};
        $cookies.remove('globals');
        $http.defaults.headers.common.Authorization = 'Bearer ';
    };


    return {
        Login: Login,
        SetSessionInfo: SetSessionInfo,
        ClearSessionInfo: ClearSessionInfo
    };

};

//  Register the service with the application
var module = angular.module("SoccerManagerApp");
module.factory("AuthenticationService", AuthenticationService)

