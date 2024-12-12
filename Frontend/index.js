const express = require('express');
const config = require('./config');
const app = express();

app.listen(config.port, () => {
  console.log(`Server running in ${config.env} mode on port ${config.port}`);
});
