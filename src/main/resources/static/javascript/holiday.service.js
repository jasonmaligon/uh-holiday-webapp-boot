"use strict";
/*global holidayApp*/
(function() {
    holidayApp.factory("dataProvider", function($http, $log) {
        return {
            loadData: function(callback, url) {
                $http.get(encodeURI(url))
                    .success(callback)
                    .error(function(data, status) {
                        $log.error("Error in dataProvider; status: ", status);
                    });
            }
        };
    });

    holidayApp.service("holidayJsService", ["$http", function($http) {
        function getHolidays(pageNumber, size) {
            pageNumber = pageNumber > 0 ? pageNumber - 1 : 0;
            return $http({
                method: "GET",
                url: "api/holidaygrid/get?page=" + pageNumber + "&size=" + size
            });
        }

        return {
            getHolidays: getHolidays
        };
    }]);

})();
