"use strict";

function HolidayJsController($scope, dataProvider) {
    var url = "api/holidays";
    var closestUrl = "";
    $scope.years = [];
    $scope.orderByField = "observedDateFull.toEpochDay";
    $scope.reverseSort = false;
    $scope.holidays = [];
    $scope.closestHoliday = [];

    $scope.init = function() {
        var date = new Date();
        var year = date.getFullYear();
        $scope.yearCode = year.toString();
        $scope.years = [];
        $scope.years.push(year);
        $scope.closestDate = $scope.formatDate(date);
        closestUrl = "api/holidays/closest?date=" + $scope.closestDate + "&search-forward=true";
        $scope.loadData();
        $scope.showArrow();
    };

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

        dataProvider.loadData(function(d) {
            $scope.closestHoliday = d.data;
        }, closestUrl);
    };

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
    };

    $scope.sortBy = function(column) {
        $scope.orderByField = column;
        $scope.reverseSort = !$scope.reverseSort;
    };

    $scope.showHoliday = function(holiday) {
        $scope.holiday = holiday;
        $("#holiday").modal();
    };

    $scope.showArrow = function() {
        $scope.direction = $scope.reverseSort ? "up" : "down";
    };

    $scope.formatDate = function(date) {
        var d = new Date(date);
        var day = "" + d.getDate();
        var month = "" + (d.getMonth() + 1);
        var year = d.getFullYear();

        if (month.length < 2) {
            month = "0" + month;
        }
        if (day.length < 2) {
            day = "0" + day;
        }

        return [year, month, day].join("-");
    }
}

holidayApp.controller("HolidayJsController", HolidayJsController);

function HolidayGridJsController($scope, holidayJsService) {
    var options = {
        pageNumber: 1,
        pageSize: 10,
        sort: null
    };

    holidayJsService.getHolidays(options.pageNumber, options.pageSize).success(function(data) {
        $scope.gridOptions.data = data.content;
        $scope.gridOptions.totalItems = data.totalElements;
    });

    $scope.gridOptions = {

        paginationPageSizes: [10, 15, 20, 250],
        paginationPageSize: options.pageSize,
        enableColumnMenus: false,
        useExternalPagination: true,
        columnDefs: [{
            name: "description"
        }, {
            name: "observedDate"
        }, {
            name: "officialDate"
        }],
        onRegisterApi: function(gridApi) {
            $scope.gridApi = gridApi;
            gridApi.pagination.on.paginationChanged($scope, function(newPage, pageSize) {
                options.pageNumber = newPage;
                options.pageSize = pageSize;
                holidayJsService.getHolidays(newPage, pageSize).success(function(data) {
                    $scope.gridOptions.data = data.content;
                    $scope.gridOptions.totalItems = data.totalElements;
                });
            });
        }
    };
}

holidayApp.controller("HolidayGridJsController", HolidayGridJsController);
