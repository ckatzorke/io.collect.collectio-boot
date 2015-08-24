var GiantBombSearch = React.createClass({
  getInitialState: function(){
    return {
      searchText: ""
    };
  },
  handleSearch: function(event) {
    event.preventDefault();
    var searchText = React.findDOMNode(this.refs.gbSearchText).value.trim();
    console.log(searchText);
    this.setState({searchText: searchText});
  },
  render: function() {
    var showingResultsFor = this.state.searchText?'Showing results for "' + this.state.searchText + '"':'';
    return (
      <form className="form-inline">
        <div className="form-group">
          <label htmlFor="gbSearchText">GiantBomb Searchreload?</label>&nbsp;
          <input ref="gbSearchText" type="text" className="form-control" id="gbSearchText" placeholder="Enter searchtext..."></input>
        </div>
        &nbsp;<button type="submit" className="btn btn-default" onClick={this.handleSearch}><img src="./img/logo-gb.png" width="30px"></img>Search</button>
        <p className="help-block">Search uses <a href="http://www.giantbomb.com" target="_blank">GiantBomb</a> data.</p>
        {showingResultsFor}
      </form>
    );
  }
});

React.render(
  <GiantBombSearch />,
  document.getElementById('giantbombsearch')
);
