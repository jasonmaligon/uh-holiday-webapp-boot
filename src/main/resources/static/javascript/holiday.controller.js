"use strict";
/*global holidayApp*/

function HolidayJsController($scope, dataProvider) {
    var url = "api/holidays";
    $scope.years = [];
    $scope.orderByField = "observedDateFull.toEpochDay";
    $scope.reverseSort = false;
    $scope.holidays = [];

    $scope.init = function() {
        var date = new Date();
        var year = date.getFullYear();
        $scope.yearCode = year.toString();
        $scope.years = [];
        $scope.years.push(year);
        $scope.loadData();
        $scope.showArrow();
    }

    $scope.loadData = function() {
        dataProvider.loadData(function(d) {
            $scope.holidays = d.data;
            $scope.holidays.forEach(function(h) {
                $scope.holiday = h;
                var y = parseInt(h.year, 10);
                if ($scope.years.indexOf(y) < 0) {
                    $scope.years.push(y);
                }
            });
            $scope.years.sort(function(a, b) {
                return b - a;
            });
        }, url);
    }

    $scope.searchFilter = function() {
        return function(e) {
            var text = $scope.searchFor;
            if (angular.isUndefined(text)) {
                return true;
            }
            text = text.trim().toLowerCase();
            if (text === "") {
                return true;
            } else if (e.description.toLowerCase().indexOf(text) !== -1) {
                return true;
            } else if (e.observedDateFull.toLowerCase().indexOf(text) !== -1) {
                return true;
            } else if (e.officialDateFull.toString().indexOf(text) !== -1) {
                return true;
            }
            return false;
        };
    }

    $scope.sortBy = function(column) {
        $scope.orderByField = column;
        $scope.reverseSort = !$scope.reverseSort;
    }

    $scope.showHoliday = function(holiday) {
        $scope.holiday = holiday;
        $("#holiday").modal();
    }

    $scope.showArrow = function() {
        $scope.direction = $scope.reverseSort ? "up" : "down";
    }
}

holidayApp.controller("HolidayJsController", HolidayJsController);

