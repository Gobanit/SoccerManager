var app = angular.module("SoccerManagerApp", ["ngRoute", "ngCookies", "soccerManagerControllers"]);
var soccerManagerControllers = angular.module('soccerManagerControllers', []);
app.config(['$routeProvider', function($routeProvider) {

    $routeProvider
        .when('/login', {controller: 'LoginController', templateUrl: 'partials/login.html', showMenu: false})
        .when('/home', {templateUrl: 'partials/home.html'})
        .when('/admin/teams', {templateUrl: 'partials/admin_teams.html', controller: 'AdminTeamsCtrl'})
        .when('/teams/:teamId', {templateUrl: 'partials/team_detail.html', controller: 'TeamDetailCtrl'})
        .when('/admin/newteam', {templateUrl: 'partials/admin_new_team.html', controller: 'AdminNewTeamCtrl'})

        .otherwise({
            redirectTo: '/home'
        });

}]);

app.run(function($rootScope, $location, $cookies, $http) {

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
    $rootScope.globals = $cookies.get('globals') || {};
    // keep user logged in after page refresh
    console.log($rootScope.globals.currentUser);
    if ($rootScope.globals.currentUser) {

        $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata;
        $rootScope.showMenu = true;
    }

    $rootScope.$on('$locationChangeStart', function (event, next, current) {
        // redirect to login page if not logged in

        if ($location.path() !== '/login' && !$rootScope.globals.currentUser) {
            $location.path('/login');
        }
    });
});


soccerManagerControllers.controller('AdminTeamsCtrl', function ($scope, $http, $rootScope, $route) {
    console.log('calling  /pa165/teams/');
    $http.get('/pa165/teams/').then(function (response) {
        var teams = response.data.content;
        $scope.teams = teams;
        console.log('AJAX loaded all teams');
        $scope.deleteTeam = function (team) {
            $http({
                method: 'DELETE',
                url: '/pa165/teams/' + team.id
            }).then(function success() {
                console.log('team removed');
                //display confirmation alert
                $rootScope.successAlert = 'A team was removed: "' + team.clubName;
                //change view to list of products
                $route.reload();

            }, function error(response) {
                //display error
                console.log("error when team");
                console.log(response);
                switch (response.data.code) {
                    case 'InvalidRequestException':
                        $rootScope.errorAlert = 'Sent data were found to be invalid by server ! ';
                        break;
                    default:
                        $rootScope.errorAlert = 'Cannot remove team ! Reason given by the server: '+response.data.message;
                        break;
                }
            });
        };

    });
});

function loadTeamPlayers($http, team, prodLink) {
    $http.get(prodLink).then(function (response) {
        var players = response.data.content;
        team.players = players;
        console.log('AJAX loaded ' + players.length + ' players to team ' + team.clubName);
    });
}

soccerManagerControllers.controller('TeamDetailCtrl',
    function ($scope, $rootScope, $http, $route) {
        // get product id from URL fragment #/product/:productId
        var userName = $rootScope.globals.currentUser.username;
        $http.get('/pa165/teams/users/' , {
            params: { userName: userName }}).then(function (response) {
            var team = response.data;
            $scope.team = team;
            console.log('AJAX loaded detail of team ' + $scope.team.clubName);
            console.log(team.links[1].href);
            loadTeamPlayers($http, team, team.links[1].href);
            $scope.removePlayerFromTeam = function (team, player) {
                $http({
                    method: 'DELETE',
                    url: '/pa165/teams/' + team.id + '/players/' + player.id
                }).then(function success() {
                    console.log('player removed from team');
                    //display confirmation alert
                    $rootScope.successAlert = 'A player ' + player.name + 'was removed from "' + team.clubName;
                    //change view to list of products
                    $route.reload();
                }, function error(response) {
                    //display error
                    console.log("error when removing player from team");
                    console.log(response);
                    switch (response.data.code) {
                        case 'InvalidRequestException':
                            $rootScope.errorAlert = 'Sent data were found to be invalid by server ! ';
                            break;
                        default:
                            $rootScope.errorAlert = 'Cannot remove player ! Reason given by the server: '+response.data.message;
                            break;
                    }
                });
            };
        });
    });

soccerManagerControllers.controller('AdminNewTeamCtrl',
    function ($scope, $routeParams, $http, $location, $rootScope) {
        //prepare data for selection lists
        $scope.countries = ['SLOVAKIA', 'CZECH REPUBLIC', 'ITALY', 'SPAIN', 'ENGLAND', "GERMANY"];
        //get categories from server
        $scope.team = {
            'clubName': '',
            'country': $scope.countries[0],
            'championshipName': '',
            'budget': 1
        };
        // function called when submit button is clicked, creates product on server
        $scope.create = function (team) {
            $http({
                method: 'POST',
                url: '/pa165/teams',
                data: team
            }).then(function success() {
                console.log('created team');
                //display confirmation alert
                $rootScope.successAlert = 'A new team "' + team.name + '" was created';
                //change view to list of products
                $location.path("/admin/teams");
            }, function error(response) {
                //display error
                console.log("error when creating product");
                console.log(response);
                switch (response.data.code) {
                    case 'InvalidRequestException':
                        $rootScope.errorAlert = 'Sent data were found to be invalid by server ! ';
                        break;
                    default:
                        $rootScope.errorAlert = 'Cannot create team ! Reason given by the server: '+response.data.message;
                        break;
                }
            });
        };
    });
