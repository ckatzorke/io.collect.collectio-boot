var webpack = require('webpack');
var HtmlWebpackPlugin = require('html-webpack-plugin');

var paths = {
    src: 'src/main/frontend',
    dist: __dirname + '/target/generated-frontend/static'
};

module.exports = {
    // misc configuration
    devtool: 'source-map', // disable source maps or switch to 'eval-source-maps' for faster builds
    debug: false,

    // entry configuration
    entry: {
        'vendor': ['./src/main/frontend/app/vendor.ts'],
        'main': ['./src/main/frontend/app/main.ts']
    },

    // output bundle configuration
    output: {
        path: paths.dist,
        filename: 'bundles/[name].bundle.js',
        sourceMapFilename: 'bundles/[name].bundle.map'
    },

    resolve: {
        extensions: ['', '.ts', '.js', '.scss']
    },

    // module loader configuration
    module: {
        loaders: [
            // support for .html as raw text
            {test: /\.html$/, loader: 'raw'},
            // Support for SASS styles files.
            {test: /\.scss$/, loader: 'raw!sass'},
            // Support for web fonts.
            {test: /\.(woff|eot)$/, loader: 'url?limit=10000'},
            // Support for inline images.
            {test: /\.(png|jpg)$/, loader: 'url?limit=10000'},
            // Support for .ts files.
            {test: /\.ts$/, loader: 'ts', exclude: [/\.(spec|e2e)\.ts$/, /node_modules/]}
        ]
    },

    plugins: [
        new webpack.optimize.CommonsChunkPlugin({name: 'vendor', minChunks: Infinity}),
        new HtmlWebpackPlugin({
            template: paths.src + '/index.html'
        })
        // include uglify in production
    ],

    // our webpack dev server config
    devServer: {
        colors: true,
        historyApiFallback: true,
        contentBase: paths.src
    }

};