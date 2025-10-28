# Usage
Change log 7.5.2024 updated all npm scripts to latest webpack,eslint,babel with bootstrap-react support

* `npm run watch` to only watch for/recompile on changes.
* `npm run build` to generate a minified, production-ready build.
*
* `npm run start` to watch for changes and also start in parallell a dev server with hot reload.
* `npm run dev:server` to run a dev server with hot reload on port 3000 without needing to start furhat web console


* `npm run lint` to lint all files in the project
* `npm run lint:watch` to lint all files in the project and watch for changes

To lint single files
* `npx eslint src/App.js` to lint a file
* `npx eslint --fix src/App.js` to lint a file and fix a file
