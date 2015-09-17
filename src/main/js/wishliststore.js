'use strict';
const API_ENDPOINT = '/rest/api/wishlist';

class WishlistStore {
  constructor() {

  }

  add(wishlistentry) {
    var post = new Promise((resolve, reject) => {
      $.ajax(API_ENDPOINT, {
        method: 'POST',
        data: JSON.stringify(wishlistentry),
        success: function() {
          resolve(getAll());
        },
        dataType: 'json',
        contentType: 'application/json'
      });
    });
    return post;
  }

  getAll() {
    var all = new Promise((resolve, reject) => {
      $.get(API_ENDPOINT, data => {
        if(data._embedded){
          resolve(data._embedded.wishlistEntries);
        } else {
          resolve([]);
        }
      });
    });
    return all;
  }
}
