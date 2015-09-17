"use strict";

var store = new WishlistStore();

var Wishlist = React.createClass({
  displayName: "Wishlist",

  getInitialState: function getInitialState() {
    return {
      wishlistEntries: []
    };
  },
  componentDidMount: function componentDidMount() {
    var _this = this;
    store.getAll().then(function (data) {
      if (data !== undefined) {
        _this.setState({ wishlistEntries: data });
      }
    });
  },
  handleAdd: function handleAdd(entry) {
    console.log(entry);
    var _this = this;
    store.add(entry).then(function (data) {
      _this.setState({ wishlistEntries: newEntries });
    });
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
    var typeElement = React.findDOMNode(this.refs.type);
    var type = typeElement.options[typeElement.selectedIndex].value;
    var title = React.findDOMNode(this.refs.title).value.trim();
    var info = React.findDOMNode(this.refs.info).value.trim();
    var link = React.findDOMNode(this.refs.link).value.trim();
    var links = null;
    if (link.length > 0) {
      links = link.split(" ");
    }
    var prio = React.findDOMNode(this.refs.prio).checked;
    this.props.onWishlistEntrySubmit({ type: type, title: title, info: info, links: links, prio: prio, added: new Date() });
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
            "select",
            { className: "form-control", id: "type", ref: "type" },
            React.createElement(
              "option",
              null,
              "Movie"
            ),
            React.createElement(
              "option",
              null,
              "Game"
            ),
            React.createElement(
              "option",
              null,
              "Comic"
            )
          )
        ),
        React.createElement(
          "div",
          { className: "form-group" },
          React.createElement("input", { type: "text", className: "form-control", id: "inputTitle", ref: "title", placeholder: "Title" })
        ),
        React.createElement(
          "div",
          { className: "form-group" },
          React.createElement("textarea", { className: "form-control", id: "inputInfo", ref: "info", placeholder: "Info" })
        ),
        React.createElement(
          "div",
          { className: "form-group" },
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

  remove: function remove(url) {
    console.log("DELETE".url);
  },
  handleRemove: function handleRemove(ele, index) {
    console.log(ele, index);
  },
  render: function render() {
    var entryNodes = "No entries yet, use form to add some...";
    if (this.props.entries && this.props.entries.length > 0) {
      entryNodes = this.props.entries.map(function (entry) {
        var dateAdded = new Date(entry.added).toISOString().slice(0, 10);
        var boundClickRemove = this.handleRemove.bind(this, i);
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
            entry.prio ? React.createElement("span", { className: "glyphicon glyphicon-heart pull-right" }) : '',
            React.createElement("span", { onClick: boundClickRemove, className: "glyphicon glyphicon-remove pull-right" })
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
