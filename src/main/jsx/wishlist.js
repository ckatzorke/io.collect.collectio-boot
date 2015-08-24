var Wishlist = React.createClass({
  getInitialState: function(){
    return {
      wishlistEntries: [
        {
          title: "Bloodsucking Bastards",
          info: "Imagine if Office Space got invaded by From Dusk Till Dawn",
          links: [
              "http://www.imdb.com/title/tt3487994/",
              "http://www.amazon.com/",
              "https://www.themoviedb.org/movie/317981-bloodsucking-bastards?language=en"
          ],
          added: new Date(),
          prio: true
        }
      ]
    };
  },
  handleAdd: function(entry) {
    console.log(entry);
    var newEntries = this.state.wishlistEntries.concat([entry]);
    this.setState({wishlistEntries: newEntries});
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
    var title = React.findDOMNode(this.refs.title).value.trim();
    var info = React.findDOMNode(this.refs.info).value.trim();
    var link = React.findDOMNode(this.refs.link).value.trim();
    var links = null;
    if(link.length > 0){
      links = link.split(" ");
    }
    var prio = React.findDOMNode(this.refs.prio).checked;
    this.props.onWishlistEntrySubmit({title: title, info: info, links: links, prio: prio, added: new Date()});
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
            <label htmlFor="inputTitle">Title</label>
            <input type="text" className="form-control" id="inputTitle" ref="title" placeholder="Title" />
          </div>
          <div className="form-group">
            <label htmlFor="inputInfo">Info</label>
            <textarea className="form-control" id="inputInfo" ref="info" placeholder="Info"></textarea>
          </div>
          <div className="form-group">
            <label htmlFor="inputLink0">Link</label>
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
  render: function(){
    var entryNodes = "No entries yet, use form to add some...";
    if(this.props.entries && this.props.entries.length > 0){
      entryNodes = this.props.entries.map(function (entry) {
        var dateAdded = entry.added.toISOString().slice(0, 10);
        return (
          <div>
            <a href="#" className="list-group-item" data-toggle="collapse" data-target="#sm" data-parent="#menu">{entry.title} <small>(added {dateAdded})</small>{entry.prio?<span className="glyphicon glyphicon-heart pull-right"></span>:''}</a>
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
