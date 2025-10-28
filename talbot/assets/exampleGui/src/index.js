// Render the top-level React component

//React 18 support
import React from 'react';
import App from './App.js';
import { createRoot } from 'react-dom/client';
import 'bootstrap/dist/css/bootstrap.min.css';

const container = document.getElementById('react-root');
const root = createRoot(container); // createRoot(container!) if you use TypeScript
root.render(<App />);

// Enable Hot Module Replacement (This is to enable hot reload in react)
if (module.hot) {
    module.hot.accept();
}
