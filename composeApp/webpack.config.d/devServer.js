// SPA fallback so deep links like /work, /info, /project/koog
// serve the same index.html and let the wasm router handle routing.
config.devServer = config.devServer || {};
config.devServer.historyApiFallback = true;
config.devServer.hot = true;
config.devServer.liveReload = true;
config.devServer.watchFiles = ["src/**/*"];
