//  Authentication service. Wrapped in an IIFE to avoid global variables
//  Purpose: To handle all user authentication methods

var AuthenticationService = function($http, $cookies, $rootScope, $timeout) {
    
	//  Function defined for when the user login is initiate
    var Login = function (username, password, callback) {
    	doCustomLogin($http, username, password, callback)
    };
    
    /*
     * Login using custom login servlet in REST API
     */
    function doCustomLogin($http, username, password, callback) {
    	var success = function success(response) {
    		var status = { 
                success: true,
                message: 'Logged in successfuly!',
                username: response.data.username,
                admin: response.data.admin
            };
            callback(status);  
        };
        
    	var failure = function failure(response) {
            console.log('Error throw!');
            var status = {
               	success: false,
                message: 'Could not log in, probably incorrect username/password'
            };
            callback(status);                 	
        };
        
        $http({
            method: 'POST',
            url: 'http://localhost:8080/pa165/users/auth',
            data: {
            	'username': username,
            	'rawPassword': password
            }
        }).then(success, failure);
    }
    
    var SetSessionInfo = function(username, admin) {
    	$rootScope.globals = {
           currentUser: {
        	   'username': username,
               'admin': admin
           }
    	};
        $cookies.put('globals', $rootScope.globals);
    };

    //  Clears the cookie and the state for the application to recognise a logged out state
    var ClearSessionInfo = function () {
        $rootScope.globals = {};
        $cookies.remove('globals');
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

