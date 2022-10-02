const path = require("path");
const dist = path.resolve("/Users/bitpogo/projects/antibytes/20220928-berlindroid-rust/build/js/node_modules/bigint_arithmetic")
const wasm = path.join(dist, "bigint_arithmetic_bg.wasm")

config.files.push({
    pattern: wasm,
    served: true,
    watched: false,
    included: false,
    nocache: false,
});

config.proxies["/bigint_arithmetic_bg.wasm"] = `/absolute${wasm}`
