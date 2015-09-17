var store = new WishlistStore();

var Wishlist = React.createClass({
  getInitialState: function(){
    return {
      wishlistEntries: []
    };
  },
  componentDidMount: function(){
    var _this = this;
    store.getAll().then(data=>{
      if(data !== undefined){
        _this.setState({wishlistEntries: data});
      }
    });
  },
  handleAdd: function(entry) {
    console.log(entry);
    var _this = this;
    store.add(entry).then(data=>{
      _this.setState({wishlistEntries: newEntries});
    });
  },
  render: function() {
    return (
      <div className="main row">
        <div className="col-md-4">
          <WishlistForm onWishlistEntrySubmit={this.handleAdd} />
        </div>
        <div className="col-md-8">
          <WishlistEntries entries={this.state.wishlistEntries} />
        </div>
      </div>
    );
  }
});

var WishlistForm = React.createClass({
  handleSubmit: function(e) {
    e.preventDefault();
    var typeElement = React.findDOMNode(this.refs.type);
    var type = typeElement.options[typeElement.selectedIndex].value;
    var title = React.findDOMNode(this.refs.title).value.trim();
    var info = React.findDOMNode(this.refs.info).value.trim();
    var link = React.findDOMNode(this.refs.link).value.trim();
    var links = null;
    if(link.length > 0){
      links = link.split(" ");
    }
    var prio = React.findDOMNode(this.refs.prio).checked;
    this.props.onWishlistEntrySubmit({type: type, title: title, info: info, links: links, prio: prio, added: new Date()});
    React.findDOMNode(this.refs.title).value = '';
    React.findDOMNode(this.refs.info).value = '';
    React.findDOMNode(this.refs.link).value = '';
    React.findDOMNode(this.refs.prio).checked = false;
    return;
  },
  render: function(){
    return (
      <div className="wishlistform">
        <form onSubmit={this.handleSubmit}>
        <div className="form-group">
          <select className="form-control" id="type" ref="type">
            <option>Movie</option>
            <option>Game</option>
            <option>Comic</option>
          </select>
        </div>
          <div className="form-group">
            <input type="text" className="form-control" id="inputTitle" ref="title" placeholder="Title" />
          </div>
          <div className="form-group">
            <textarea className="form-control" id="inputInfo" ref="info" placeholder="Info" />
          </div>
          <div className="form-group">
            <input type="text" className="form-control" ref="link" id="inputLink0" placeholder="Link" />
          </div>
          <div className="checkbox">
            <label>
              <input type="checkbox" ref="prio" id="prio"> High Prio</input>
            </label>
          </div>
          <button type="submit" className="btn btn-default">Add</button>
        </form>
      </div>
    );
  }
});

var WishlistEntries = React.createClass({
  remove: function(url){
    console.log("DELETE". url)
  },
  handleRemove: function(ele, index) {
    console.log(ele, index);
  },
  render: function(){
    var entryNodes = "No entries yet, use form to add some...";
    if(this.props.entries && this.props.entries.length > 0){
      entryNodes = this.props.entries.map(function (entry) {
        var dateAdded = new Date(entry.added).toISOString().slice(0, 10);
        var boundClickRemove = this.handleRemove.bind(this, i);
        return (
          <div>
            <a href="#" className="list-group-item" data-toggle="collapse" data-target="#sm" data-parent="#menu">{entry.title} <small>(added {dateAdded})</small>{entry.prio?<span className="glyphicon glyphicon-heart pull-right"></span>:''}<span onClick={boundClickRemove} className="glyphicon glyphicon-remove pull-right"></span></a>
          </div>
        );
      });
    }
    return (
      <div className="panel list-group">
        {entryNodes}
      </div>
    );
  }
});

React.render(
  <Wishlist />,
  document.getElementById('wishlist')
);
