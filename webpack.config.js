module.exports = {
    entry: "./src/main/frontend/entry.js",
    output: {
        path: __dirname + "/target/generated-frontend",
        filename: "bundle.js"
    },
    module: {
        loaders: [
            { test: /\.css$/, loader: "style!css" }
        ]
    }
};