var app = angular.module('apacheCamelRide',['ui.router','ngStorage','ngMaterial', 'ngMessages']);

app.constant('urls', {
    BASE: 'http://localhost:8080/apache-camel-ride',
    USER_SERVICE_API : 'http://localhost:8080/apache-camel-ride/api/user/'
});

app.config(['$stateProvider', '$urlRouterProvider',
    function($stateProvider, $urlRouterProvider) {

        $stateProvider
            .state('home', {
                url: '/',
                templateUrl: 'partials/home',
                controller:'UserController',
                controllerAs:'ctrl',
                resolve: {
                    users: function ($q, UserService) {
                        console.log('Load all users');
                        var deferred = $q.defer();
                        UserService.loadAllUsers().then(deferred.resolve, deferred.resolve);
                        return deferred.promise;
                    }
                }
            });
        $urlRouterProvider.otherwise('/');
    }]);

