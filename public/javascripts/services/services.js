/**
 * Created by nslavov on 11/7/15.
 */
var services = angular.module('services', []);

/**
 * Query constants.
 */
services.constant('QUERY', {
    "INVALIDATE": "DELETE FROM ",
    "INSERT": "INSERT INTO CACHE (id,title,overview,release_date,poster_path,vote_average,key,popular,upcoming,top_rated,now_playing) VALUES (?,?,?,?,?,?,?,?,?,?,?)",
    "CREATE": "CREATE TABLE IF NOT EXISTS CACHE (id BLOB, title BLOB, overview BLOB, release_date BLOB, poster_path BLOB, vote_average BLOB, key BLOB, popular BLOB ,upcoming BLOB, top_rated BLOB, now_playing BLOB,PRIMARY KEY(id))",
    "INDEX_NOW_PLAYING": "CREATE INDEX  IF NOT EXISTS idx_now_playing ON CACHE (now_playing)",
    "INDEX_POPULAR": "CREATE INDEX  IF NOT EXISTS idx_popular ON CACHE (popular)",
    "INDEX_TOP_RATED": "CREATE INDEX  IF NOT EXISTS idx_top_rated ON CACHE (top_rated)",
    "INDEX_UPCOMING": "CREATE INDEX  IF NOT EXISTS idx_upcoming ON CACHE (upcoming)"
});


/**
 *Movie dao
 */
services.service('movieDao', function ($http, $q) {

    /**
     *
     * @returns {promise.promise|Function|*|jQuery.promise|d.promise|promise}
     */
    this.getCount = function () {
        var deferred = $q.defer();
        $http.get('/count').success(function (data) {
            deferred.resolve(data);
        }).error(function (data) {
            deferred.reject(data);
        });
        return deferred.promise;
    };

    /**
     *
     * @param category
     * @returns {promise.promise|Function|*|jQuery.promise|d.promise|promise}
     */
    this.getCountByCategory = function (category) {
        var deferred = $q.defer();
        $http.get('/count/category', {params: {category: category}}).success(function (data) {
            deferred.resolve(data);
        }).error(function (data) {
            deferred.reject(data);
        });
        return deferred.promise;
    };

    /**
     *
     * @param category
     * @param current
     * @param step
     * @returns {promise.promise|Function|*|jQuery.promise|d.promise|promise}
     */
    this.getMovieByCategory = function (category, current, step) {
        var deferred = $q.defer();
        $http.get('/getMovie', {
            params: {category: category, current: current, step: step}
        }).success(function (data) {
            deferred.resolve(data);
        }).error(function (data) {
            //deferred.reject(data);
        });
        return deferred.promise;
    };
});

/**
 * cache
 */
services.service('cache', function ($http, $q,QUERY) {
    /**
     *Checks maintenance webDb
     * @returns {number} if supported return 0, if not supported return 1;
     */
    this.isSupported = function () {
        if (window.openDatabase) {
            return 0;
        } else {
            return 1;
        }
    };

    /**
     * Drop table from CACHE.
     * @param table name.
     */
    this.invalidate = function (invalidateTable) {
        var webDbConn = openDatabase("CACHE", "1.0", "VIDEO", 50 * 1024 * 1024);
        var query = QUERY.INVALIDATE + invalidateTable;
        webDbConn.transaction(function (tx) {
            tx.executeSql(query);
        });
    };

    /**
     * Create table CACHE
     * @returns {string}
     */
    this.init = function () {
        //Create the database the parameters are 1. the database name 2.version number 3. a description 4. the size of the database (in bytes) 1024 x 1024 = 1MB
        var webDbConn = openDatabase("CACHE", "1.0", "VIDEO", 50 * 1024 * 1024);
        if (window.openDatabase) {
            //create the CACHE table using SQL for the database using a transaction
            webDbConn.transaction(function (t) {
                t.executeSql(QUERY.CREATE);
            }, onError);
            webDbConn.transaction(function (t) {
                t.executeSql(QUERY.INDEX_NOW_PLAYING);
            }, onError);
            webDbConn.transaction(function (t) {
                t.executeSql(QUERY.INDEX_POPULAR);
            }, onError);
            webDbConn.transaction(function (t) {
                t.executeSql(QUERY.INDEX_TOP_RATED);
            }, onError);
            webDbConn.transaction(function (t) {
                t.executeSql(QUERY.INDEX_UPCOMING);
            }, onError);
        } else {
            return "WebSQL is not supported by your browser!";
        }
    };

    /**
     * Insert
     * @param items.
     */
    this.insert = function (items) {
        var dbConn = openDatabase("CACHE", "1.0", "VIDEO", 50 * 1024 * 1024);
        dbConn.transaction(function (tx) {
            for (var i = 0; i < items.length; i++) {
                tx.executeSql(QUERY.INSERT, [items[i].id, items[i].title,items[i].overview,items[i].release_date,items[i].poster_path,items[i].vote_average,items[i].key,items[i].popular,items[i].upcoming,items[i].top_rated,items[i].now_playing],
                    onSuccessExecuteSql,
                    onError);
            }
        });
    };

    /**
     * Select
     * @param category
     * @param start
     * @param end
     * @returns {*}
     */
    this.select = function (category, start, end) {

        switch (category) {
            case "popular" :
                return selectByRange("popular",start, end);
                break;
            case "upcoming":
                return selectByRange("upcoming",start, end);
                break;
            case "now-playing":
                return selectByRange("now_playing",start, end);
                break;
            case "top-rated":
                return selectByRange("top_rated",start, end);
                break;
            default :
                break;
        }
    };

    /**
     * Select
     * @param cat category
     * @param start from index
     * @param end to index
     * @returns {promise.promise|Function|*|jQuery.promise|d.promise|promise}
     */
    function selectByRange(cat,start,end) {
        var deferred = $q.defer();
        var dbConn = openDatabase("CACHE", "1.0", "VIDEO", 50 * 1024 * 1024);
        var query = "SELECT * FROM CACHE WHERE " + cat + " = 'true' order by release_date desc, title ASC" + " LIMIT " + start + "," + end;
        dbConn.transaction(function (tx) {
            tx.executeSql(query, [], function (tx, results) {
                var items = [];
                for (var i = 0; i < results.rows.length; i++) {
                    items.push(results.rows.item(i))
                }
                deferred.resolve(items);
            }, function (err) {
                deferred.reject(err);
            });
        });
        return deferred.promise;
    }

    /**
     *On success
     * @param tx
     * @param results
     */
    function onSuccessExecuteSql(tx, results) {
        //console.log(tx);
        //console.log(tx);
        //console.log(results);
    }

    /**
     *On error information
     * @param err
     */
    function onError(err) {
        console.log(err);
    }
});



