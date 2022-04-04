/* eslint-env browser */

import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import {
  Auth,
  ChallengeList,
  Game
} from './components';
import axios from 'axios';
import './index.css';

axios.interceptors.request.use(
  config => {
    const token = localStorage.getItem('token');
    if (token) {
    	config.headers.authorization = `Bearer ${token}`;
    }
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);

ReactDOM.render(
	<Router>
    <Routes>
      <Route path="/" element={<Auth />} />
      <Route path="/challenges" element={<ChallengeList />} />
      <Route path="/challenge/:id" element={<Game />} />
    </Routes>
  </Router>,
  document.getElementById("root")
);
