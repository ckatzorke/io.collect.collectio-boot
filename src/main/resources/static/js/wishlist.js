"use strict";

var Wishlist = React.createClass({
  displayName: "Wishlist",

  getInitialState: function getInitialState() {
    return {
      wishlistEntries: [{
        title: "Bloodsucking Bastards",
        info: "Imagine if Office Space got invaded by From Dusk Till Dawn",
        links: ["http://www.imdb.com/title/tt3487994/", "http://www.amazon.com/", "https://www.themoviedb.org/movie/317981-bloodsucking-bastards?language=en"],
        added: new Date(),
        prio: true
      }]
    };
  },
  handleAdd: function handleAdd(entry) {
    console.log(entry);
    var newEntries = this.state.wishlistEntries.concat([entry]);
    this.setState({ wishlistEntries: newEntries });
  },
  render: function render() {
    return React.createElement(
      "div",
      { className: "main row" },
      React.createElement(
        "div",
        { className: "col-md-4" },
        React.createElement(WishlistForm, { onWishlistEntrySubmit: this.handleAdd })
      ),
      React.createElement(
        "div",
        { className: "col-md-8" },
        React.createElement(WishlistEntries, { entries: this.state.wishlistEntries })
      )
    );
  }
});

var WishlistForm = React.createClass({
  displayName: "WishlistForm",

  handleSubmit: function handleSubmit(e) {
    e.preventDefault();
    var title = React.findDOMNode(this.refs.title).value.trim();
    var info = React.findDOMNode(this.refs.info).value.trim();
    var link = React.findDOMNode(this.refs.link).value.trim();
    var links = null;
    if (link.length > 0) {
      links = link.split(" ");
    }
    var prio = React.findDOMNode(this.refs.prio).checked;
    this.props.onWishlistEntrySubmit({ title: title, info: info, links: links, prio: prio, added: new Date() });
    React.findDOMNode(this.refs.title).value = '';
    React.findDOMNode(this.refs.info).value = '';
    React.findDOMNode(this.refs.link).value = '';
    React.findDOMNode(this.refs.prio).checked = false;
    return;
  },
  render: function render() {
    return React.createElement(
      "div",
      { className: "wishlistform" },
      React.createElement(
        "form",
        { onSubmit: this.handleSubmit },
        React.createElement(
          "div",
          { className: "form-group" },
          React.createElement(
            "label",
            { htmlFor: "inputTitle" },
            "Title"
          ),
          React.createElement("input", { type: "text", className: "form-control", id: "inputTitle", ref: "title", placeholder: "Title" })
        ),
        React.createElement(
          "div",
          { className: "form-group" },
          React.createElement(
            "label",
            { htmlFor: "inputInfo" },
            "Info"
          ),
          React.createElement("textarea", { className: "form-control", id: "inputInfo", ref: "info", placeholder: "Info" })
        ),
        React.createElement(
          "div",
          { className: "form-group" },
          React.createElement(
            "label",
            { htmlFor: "inputLink0" },
            "Link"
          ),
          React.createElement("input", { type: "text", className: "form-control", ref: "link", id: "inputLink0", placeholder: "Link" })
        ),
        React.createElement(
          "div",
          { className: "checkbox" },
          React.createElement(
            "label",
            null,
            React.createElement(
              "input",
              { type: "checkbox", ref: "prio", id: "prio" },
              " High Prio"
            )
          )
        ),
        React.createElement(
          "button",
          { type: "submit", className: "btn btn-default" },
          "Add"
        )
      )
    );
  }
});

var WishlistEntries = React.createClass({
  displayName: "WishlistEntries",

  render: function render() {
    var entryNodes = "No entries yet, use form to add some...";
    if (this.props.entries && this.props.entries.length > 0) {
      entryNodes = this.props.entries.map(function (entry) {
        var dateAdded = entry.added.toISOString().slice(0, 10);
        return React.createElement(
          "div",
          null,
          React.createElement(
            "a",
            { href: "#", className: "list-group-item", "data-toggle": "collapse", "data-target": "#sm", "data-parent": "#menu" },
            entry.title,
            " ",
            React.createElement(
              "small",
              null,
              "(added ",
              dateAdded,
              ")"
            ),
            entry.prio ? React.createElement("span", { className: "glyphicon glyphicon-heart pull-right" }) : ''
          )
        );
      });
    }
    return React.createElement(
      "div",
      { className: "panel list-group" },
      entryNodes
    );
  }
});

React.render(React.createElement(Wishlist, null), document.getElementById('wishlist'));
//# sourceMappingURL=wishlist.js.map
