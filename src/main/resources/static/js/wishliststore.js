'use strict';

var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

var API_ENDPOINT = '/rest/api/wishlist';

var WishlistStore = (function () {
  function WishlistStore() {
    _classCallCheck(this, WishlistStore);
  }

  _createClass(WishlistStore, [{
    key: 'add',
    value: function add(wishlistentry) {
      var post = new Promise(function (resolve, reject) {
        $.ajax(API_ENDPOINT, {
          method: 'POST',
          data: JSON.stringify(wishlistentry),
          success: function success() {
            resolve(getAll());
          },
          dataType: 'json',
          contentType: 'application/json'
        });
      });
      return post;
    }
  }, {
    key: 'getAll',
    value: function getAll() {
      var all = new Promise(function (resolve, reject) {
        $.get(API_ENDPOINT, function (data) {
          if (data._embedded) {
            resolve(data._embedded.wishlistEntries);
          } else {
            resolve([]);
          }
        });
      });
      return all;
    }
  }]);

  return WishlistStore;
})();
//# sourceMappingURL=wishliststore.js.map
