//YOUTUBE API KEY AIzaSyDWfWekjSirbbcW5C3ziEUlOzzNiznOZSI
//FACEBOOK API KEY 641232165979215


var ruseHackApp = angular.module('ruseHackApp', ['ngRoute', 'infinite-scroll', 'services', 'directives', 'filters']);

function ruseHackAppConfig($routeProvider) {
  $routeProvider.when('/', {
    controller: 'mainController'
  }).when('/movies', {
    controller: 'movieController',
    templateUrl: 'movies.html'
  }).when('/movies/:category', {
    controller: 'movieController',
    templateUrl: 'movies.html'
  }).when('/player/:id/:title', {
    controller: 'playerController',
    templateUrl: 'player.html'
  }).when('/youtubeVideos', {
    controller: 'youtubeController',
    templateUrl: 'youtubeVideos.html'
  })

      .otherwise({
        redirectTo: '/index.html'
      });
}
ruseHackApp.config(ruseHackAppConfig);

ruseHackApp.controller('mainController', function ($q, $scope, $http, $rootScope, cache) {

  $(document).ready(function () {
    //cache.invalidate('CACHE');
    var preloader = $('.preloader');
    $(window).load(function(){
      preloader.remove();
    });

    //#main-slider
    var slideHeight = $(window).height();
    $('#home-slider .item').css('height',slideHeight);

    $(window).resize(function(){'use strict',
        $('#home-slider .item').css('height',slideHeight);
    });

    new WOW().init();
  });

  window.fbAsyncInit = function () {
    FB.init({
      appId: '641232165979215',
      xfbml: true,
      version: 'v2.5'
    });
  };

  (function (d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) {
      return;
    }
    js = d.createElement(s);
    js.id = id;
    js.src = "//connect.facebook.net/en_US/sdk.js";
    fjs.parentNode.insertBefore(js, fjs);
  }(document, 'script', 'facebook-jssdk'));

  $rootScope.testDisplay = "display: initial";

  $scope.userName = "user";
  $scope.userMail = "";
  $scope.userId = "";
  $scope.userPic = "";
  $scope.categoryButtonsVisibility = "display:none";
  $rootScope.loginButtonVisibility = "display: inline";
  $scope.userPicVisibility = "visibility: hidden";

  $scope.login = function () {
    var user = loginFacebook();
    user.then(function (result) {
      $scope.userName = result.name;
      $scope.userMail = result.email;
      $scope.userId = result.id;
      setPic(result.id).then(function (response) {
        $scope.userPic = response.data.url;
        $scope.userPicVisibility = "visibility: visible";
        $rootScope.testDisplay = "display:none";
      })
    });
  };

  function loginFacebook() {
    var deferred = $q.defer();
    FB.login(function (response) {
      if (response.authResponse) {
        FB.api('/me?fields=email,name', function (response) {
          deferred.resolve(response);
        });
      }
    }, {
      scope: 'email'
    });
    return deferred.promise;
  }

  function setPic(id) {
    var defferred = $q.defer();
    FB.api('/' + id + "/picture?type=large", function (response) {
      defferred.resolve(response);
    });
    return defferred.promise;
  }


  $rootScope.selectCategory = function (id) {
    switch (id) {
      case 0:
      {
        $rootScope.selectedButton0 = "border-color:#036dc0";
        $rootScope.selectedButton1 = "border-color:white";
        $rootScope.selectedButton2 = "border-color:white";
        $rootScope.selectedButton3 = "border-color:white";
        $rootScope.selectedButton4 = "border-color:white";
        $rootScope.selectedButton5 = "border-color:white";
      }
        break;
      case 1:
      {

        $rootScope.selectedButton0 = "border-color:white";
        $rootScope.selectedButton1 = "border-color:#036dc0";
        $rootScope.selectedButton2 = "border-color:white";
        $rootScope.selectedButton3 = "border-color:white";
        $rootScope.selectedButton4 = "border-color:white";
        $rootScope.selectedButton5 = "border-color:white";
      }
        break;
      case 2:
      {

        $rootScope.selectedButton0 = "border-color:#036dc0";
        $rootScope.selectedButton1 = "border-color:white";
        $rootScope.selectedButton2 = "border-color:#036dc0";
        $rootScope.selectedButton3 = "border-color:white";
        $rootScope.selectedButton4 = "border-color:white";
        $rootScope.selectedButton5 = "border-color:white";
      }
        break;
      case 3:
      {

        $rootScope.selectedButton0 = "border-color:#036dc0";
        $rootScope.selectedButton1 = "border-color:white";
        $rootScope.selectedButton2 = "border-color:white";
        $rootScope.selectedButton3 = "border-color:#036dc0";
        $rootScope.selectedButton4 = "border-color:white";
        $rootScope.selectedButton5 = "border-color:white";
      }
        break;
      case 4:
      {

        $rootScope.selectedButton0 = "border-color:#036dc0";
        $rootScope.selectedButton1 = "border-color:white";
        $rootScope.selectedButton2 = "border-color:white";
        $rootScope.selectedButton3 = "border-color:white";
        $rootScope.selectedButton4 = "border-color:#036dc0";
        $rootScope.selectedButton5 = "border-color:white";
      }
        break;
      case 5:
      {

        $rootScope.selectedButton0 = "border-color:#036dc0";
        $rootScope.selectedButton1 = "border-color:white";
        $rootScope.selectedButton2 = "border-color:white";
        $rootScope.selectedButton3 = "border-color:white";
        $rootScope.selectedButton4 = "border-color:white";
        $rootScope.selectedButton5 = "border-color:#036dc0";
      }
        break;
      default:
    }
  };

});

ruseHackApp.controller('movieController', function ($scope, $http, $routeParams, cache, movieDao) {
  $scope.showMovieInfo = "visibility: hidden";
  if ($routeParams.category) {
    $scope.current = 0;
    $scope.step = 10;
    $scope.numberOfItemsToDisplay = 10;
    $scope.movieItems = [];
    $scope.totalCount = 0;
    $scope.categoryCount = 0;
    $scope.buffer = [];
    $scope.category = $routeParams.category;
    var flag = false;
    $scope.showMovieInfo = "visibility: visible";
    cache.init();

    var totalCount = movieDao.getCount();
    totalCount.then(function (result) {
      $scope.totalCount = result;
    });

    var categoryCount = movieDao.getCountByCategory($scope.category);

    categoryCount.then(function (result) {
      $scope.categoryCount = result;
      flag = true;
      getMovies();
    });

    /**
     * Load movie item in controller scope.
     */
    function getMovies() {
      if (flag == true) {
        flag = false;

        /**
         * if cash take from the cache else call function fromServer().
         */
        var items = cache.select($scope.category, $scope.current, $scope.step);
        items.then(function (result) {
          $scope.buffer = result;
          if (result.length > 0 || undefined || null) {
            addItems();
          } else {
            fromServer();
          }
        });

        /**
         * Get item list from server and save in cache.
         *
         */
        function fromServer() {
          var it = movieDao.getMovieByCategory($scope.category, $scope.current, $scope.step);
          it.then(function (result) {
            $scope.buffer = result;
            addItems();
          }).then(function () {
            cache.insert($scope.buffer);

          });
        }

        /**
         * Push buffer scope in master scope.
         */
        function addItems() {
          for (var i = 0; i < $scope.buffer.length; i++) {
            $scope.movieItems.push($scope.buffer[i]);
          }
          $scope.current = $scope.movieItems.length;
          flag = true;
        }
      }
    }

    /**
     * add movie item
     */
    $scope.addMoreItem = function () {
      if ($scope.numberOfItemsToDisplay < $scope.categoryCount) {
        getMovies();
        if ($scope.movieItems.length >= $scope.numberOfItemsToDisplay) {
          $scope.numberOfItemsToDisplay += 10;
        }
      }
    };
  }
});

ruseHackApp.controller('playerController', function ($scope, $http, $routeParams, $sce, $rootScope) {
  $rootScope.testDisplay = "display: none";
  $scope.currentTitle = $routeParams.title;
  $rootScope.categoryButtonsVisibility = "display: none";
  $scope.currentProjectUrl = $sce.trustAsResourceUrl('http://www.youtube.com/embed/' + $routeParams.id);
});


ruseHackApp.controller('youtubeController', function ($scope, $http) {
  $scope.videos = [];
  $scope.searchFor = "";

  $scope.search = function () {
    $http.get("https://www.googleapis.com/youtube/v3/search", {
      params: {
        part: 'snippet',
        q: $scope.searchFor,
        type: 'video',
        maxResults: 50,
        key: 'AIzaSyDWfWekjSirbbcW5C3ziEUlOzzNiznOZSI'
      }
    }).success(function (data) {
      $scope.videos = data;
    })
  };

});
