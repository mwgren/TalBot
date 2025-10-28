const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const { HotModuleReplacementPlugin } = require('webpack');

module.exports = {
    entry: __dirname + '/src/index.js',
    devtool: 'source-map',
    mode: 'development',
    output: {
        path: __dirname + '/dist',
        publicPath: '/dist/',
        filename: 'bundle.js',
        clean: true

    },
    devServer: {
            static: './dist',
            hot: true, // Enable hot module replacement
            port: 3000, // You can specify your port
        },
    plugins: [
            new HtmlWebpackPlugin({
                template: './index.html'
            }),
            new HotModuleReplacementPlugin() // This plugin is required for HMR to work
        ],
    module: {
        rules: [
          {
            test: /\.(js|jsx)$/, // Regex for JavaScript and JSX files
            exclude: /node_modules/, // Exclude node_modules directory
            use: {
              loader: 'babel-loader', // Use babel-loader for these files
              options: {
                presets: ['@babel/preset-env', '@babel/preset-react'] // Presets for modern JavaScript and React
              }
            }
          },
          {
            test: /\.css$/, // Regex for CSS files
            use: ['style-loader', 'css-loader'] // Use style-loader and css-loader for CSS files
          }
        ]
      },
};
