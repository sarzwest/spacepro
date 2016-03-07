var phonecatApp = angular.module('spaceProApp', []);

phonecatApp.controller('TopicsCtrl', function ($scope, $http) {

    $scope.view = "list";
    $scope.token = {};
    $scope.loggedIn = false;
    $scope.topics = [];
    $scope.topic = {};
    $scope.comments = {};
    $scope.newTopic = {"header": "", "content": ""};
    $scope.newComment = "";

    $scope.orderProp = "header";
    $scope.userReg = {"loginName": "sarzwest", "password": "popopo"};
    $scope.user = {"loginName": "sarzwest", "password": "popopo"};
    $scope.register = function (user) {
        $http({
            "method": "POST",
            "url": "rest/registration",
            "headers": {"X-loginName": user.loginName, "X-password": user.password}
        }).then(
            function () {
                window.alert("Successfully registered")
            }, function () {
                window.alert("Problem during registration")
            }
        );
    };

    $scope.login = function (user) {
        $http({
            "method": "POST",
            "url": "rest/login",
            "headers": {"X-loginName": user.loginName, "X-password": user.password}
        }).then(
            function (response) {
                window.alert("Successfully logged in");
                $scope.token = response.headers("X-token");
                console.log($scope.token);
                $scope.loggedIn = true;
                $scope.getTopicsList();
            }, function () {
                window.alert("Problem during logging in")
            }
        );
    };

    $scope.logout = function () {
        $scope.loggedIn = false;
    }

    $scope.getTopicsList = function () {
        $scope.view = "list";
        $http({
            "method": "GET",
            "url": "rest/topic",
            "headers": {"X-token": $scope.token}
        }).then(
            function (response) {
                $scope.topics = response.data;
                console.log($scope.topics);
            }, function (response) {
                window.alert("Problem during getting topics from server");
            }
        );
    }

    $scope.detail = function (topic) {
        $scope.topic = topic;
        $scope.view = "detail";
        $http({
            "method": "GET",
            "url": "rest/topic/" + topic.id + "/comment",
            "headers": {"X-token": $scope.token}
        }).then(
            function (response) {
                $scope.comments = response.data;
                console.log($scope.comments);
            }, function (response) {
                window.alert("Problem during getting comments from server");
            }
        )
    }

    $scope.sendComment = function (comment) {
        $http({
            "method": "POST",
            "url": "rest/topic/" + $scope.topic.id + "/comment",
            "headers": {"X-token": $scope.token},
            "data": {"content": comment}
        }).then(
            function (response) {
                window.alert("Successfully sent comment");
                $scope.detail($scope.topic);
            }, function () {
                window.alert("Problem during sending comment")
            }
        );
    };

    $scope.createTopic = function (topic) {
        $http({
            "method": "POST",
            "url": "rest/topic",
            "headers": {"X-token": $scope.token},
            "data": topic
        }).then(
            function (response) {
                window.alert("Successfully created topic");
                $scope.getTopicsList();
            }, function () {
                window.alert("Problem during creation of topic")
            }
        );
    };

});

