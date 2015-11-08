/**
 * Created by leozhekov on 11/7/15.
 */
var filters = angular.module('filters', []);

filters.filter('datetime', function ($filter) {
  return function (input) {
    if (input == null) {
      return "";
    }

    var _date = $filter('date')(new Date(input),
        'MM/dd/yyyy HH:mm');

    return _date.toUpperCase();

  };
});

filters.filter('trusted', ['$sce', function ($sce) {
  return function (url) {
    return $sce.trustAsResourceUrl(url);
  };
}]);