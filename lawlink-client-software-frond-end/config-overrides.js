// config-overrides.js
const webpack = require('webpack');

module.exports = function override(config) {
  config.resolve.fallback = {
    ...config.resolve.fallback,
    global: require.resolve('global'),
  };
  config.plugins = (config.plugins || []).concat([
    new webpack.ProvidePlugin({
      global: 'global',
    }),
  ]);
  return config;
};
