<html>

<head>
    <meta charset="utf-8" />
    <title>AngularJS Basic HTTP Authentication</title>
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootswatch/3.3.7/yeti/bootstrap.min.css" />
    <script src="//code.jquery.com/jquery-2.0.3.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.5/angular.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.5/angular-resource.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.5/angular-route.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.5/angular-cookies.js"></script>

    <script src="angular_app.js"></script>
    <script src="authService.js"></script>
    <script src="loginController.js"></script>
</head>

<body ng-app="SoccerManagerApp">
<nav class="navbar navbar-inverse navbar-static-top" ng-show="showMenu">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${pageContext.request.contextPath}">PA165 AngularJS Soccer Manager</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li><a href="#!/players">List of players</a></li>
                <li><a href="#!/teams/pick">Pick team</a></li>
                <li><a href="#!/matches">List of matches</a></li>
                <li><a href="#!/teams/:teamId">Manage my team</a></li>
                <li class="dropdown">
                    <a href="" class="dropdown-toggle" data-toggle="dropdown">Admin<b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="#!/admin/players">Players</a></li>
                        <li><a href="#!/admin/teams">Teams</a></li>
                    </ul>
                </li>
                <li><a href="#!/login">Logout</a></li>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</nav>

<div class="jumbotron">
    <div class="container">
        <div class="col-xs-offset-2 col-xs-8">
            <div ng-show="warningAlert" class="alert alert-warning alert-dismissible" role="alert">
                <button type="button" class="close" aria-label="Close" ng-click="hideWarningAlert()"> <span aria-hidden="true">&times;</span></button>
                <strong>Warning!</strong> <span>{{warningAlert}}</span>
            </div>
            <div ng-show="errorAlert" class="alert alert-danger alert-dismissible" role="alert">
                <button type="button" class="close" aria-label="Close" ng-click="hideErrorAlert()"> <span aria-hidden="true">&times;</span></button>
                <strong>Error!</strong> <span>{{errorAlert}}</span>
            </div>
            <div ng-show="successAlert" class="alert alert-success alert-dismissible" role="alert">
                <button type="button" class="close" aria-label="Close" ng-click="hideSuccessAlert()"> <span aria-hidden="true">&times;</span></button>
                <strong>Success !</strong> <span>{{successAlert}}</span>
            </div>
            <div ng-view></div>
        </div>
    </div>
</div>

<div class="text-center">
    <footer class="footer">
        <p>&copy;&nbsp;Masaryk University</p>
    </footer>
</div>
</body>

</html>
