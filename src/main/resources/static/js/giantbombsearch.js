'use strict';

var GiantBombSearch = React.createClass({
  displayName: 'GiantBombSearch',

  getInitialState: function getInitialState() {
    return {
      searchText: ""
    };
  },
  handleSearch: function handleSearch(event) {
    event.preventDefault();
    var searchText = React.findDOMNode(this.refs.gbSearchText).value.trim();
    console.log(searchText);
    this.setState({ searchText: searchText });
  },
  render: function render() {
    var showingResultsFor = this.state.searchText ? 'Showing results for "' + this.state.searchText + '"' : '';
    return React.createElement(
      'form',
      { className: 'form-inline' },
      React.createElement(
        'div',
        { className: 'form-group' },
        React.createElement(
          'label',
          { htmlFor: 'gbSearchText' },
          'GiantBomb Searchreload?'
        ),
        ' ',
        React.createElement('input', { ref: 'gbSearchText', type: 'text', className: 'form-control', id: 'gbSearchText', placeholder: 'Enter searchtext...' })
      ),
      ' ',
      React.createElement(
        'button',
        { type: 'submit', className: 'btn btn-default', onClick: this.handleSearch },
        React.createElement('img', { src: './img/logo-gb.png', width: '30px' }),
        'Search'
      ),
      React.createElement(
        'p',
        { className: 'help-block' },
        'Search uses ',
        React.createElement(
          'a',
          { href: 'http://www.giantbomb.com', target: '_blank' },
          'GiantBomb'
        ),
        ' data.'
      ),
      showingResultsFor
    );
  }
});

React.render(React.createElement(GiantBombSearch, null), document.getElementById('giantbombsearch'));
//# sourceMappingURL=giantbombsearch.js.map
