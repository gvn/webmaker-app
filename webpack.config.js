var getPages = require('./npm_tasks/get-pages');
var path = require('path');
var fs = require('fs');
var webmakerCorePath = fs.realpathSync(path.resolve(
  __dirname,
  'node_modules/webmaker-core'
));

// Prep all entry points
var entry = {};
getPages().forEach(function (page) {
  entry[page] = './node_modules/webmaker-core/pages/' + page + '/' + page + '.jsx';
});

module.exports = {
  entry: entry,
  devtool: 'source-map', //not good for ff

  // http://webpack.github.io/docs/troubleshooting.html#npm-linked-modules-doesn-t-find-their-dependencies
  resolve: { fallback: path.join(__dirname, "node_modules") },
  resolveLoader: { fallback: path.join(__dirname, "node_modules") },

  output: {
    path: __dirname + '/app/src/main/assets/www/js',
    filename: '[name].bundle.js'
  },
  module: {
    loaders: [
      {
        test: /\.js$/,
        loaders:  ['babel-loader'],
        include: webmakerCorePath
      },
      {
        test: /\.jsx$/,
        loaders:  ['babel-loader', 'jsx-loader'],
        include: webmakerCorePath
      },
      {
        test: /\.json$/,
        loader: 'json-loader',
        include: [webmakerCorePath,  path.resolve(__dirname, 'node_modules')]
      }
    ]
  }
};
