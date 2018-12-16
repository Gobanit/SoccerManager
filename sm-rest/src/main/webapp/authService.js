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
    
//    /*
//     * Login using Spring login servlet - not used currently
//     */
//    function doSpringLogin($http, username, password, callback) {
//    	var success = function success(response) {
//        	findCurrentUser($http, callback);
//        };
//    	var failure = function failure(response) {
//            console.log('Error throw!');
//            var status = {
//               	success: false,
//                message: 'Could not log in, probably incorrect username/password.'
//            };
//            callback(status);                 	
//        };
//    	
//    	$http({
//            method: 'POST',
//            url: 'http://localhost:8080/pa165/login',
//            params: {
//            	'username': username,
//            	'password': password
//            }
//        }).then(success, failure);
//    }
//    
//    /**
//     * Used to find currently logged in user.
//     */
//    function findCurrentUser($http, callback) {
//    	// success function
//    	var success = function success(response) {
//    		// request succesfful, but not returning any value!
//        	if (response.data === "") {
//                console.log('Error auth!');
//                var status = {
//               		success: false,
//                   	message: 'Error in finding user info.'
//                };
//                callback(status); 
//            // request successful and found data
//            } else {
//                console.log('Success!');
//                var status = { 
//                	success: true,
//                	message: 'Successfully found user',
//                	username: response.data.username,
//                	admin: response.data.admin
//                };
//                callback(user);                 	
//            }
//    	};
//    	
//    	// failure function
//    	var failure = function failure(response) {
//            console.log('Error throw in find current user!');
//            var status = {
//               	success: false,
//                message: 'Error in finding user info.'
//            };
//            callback(status);                 	
//        };
//        
//        
//        // executing request
//        $http({
//            method: 'GET',
//            url: 'http://localhost:8080/pa165/users/currentUser',
//        }).then(success, failure);  
//    };

    
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

