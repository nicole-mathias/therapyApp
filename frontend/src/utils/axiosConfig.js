import axios from 'axios';

// Configure axios to use the backend server
const API_BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080';
axios.defaults.baseURL = API_BASE_URL;

// Add request interceptor for debugging
axios.interceptors.request.use(
  (config) => {
    console.log('Making request to:', config.url);
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Add response interceptor for debugging
axios.interceptors.response.use(
  (response) => {
    console.log('Response received:', response.status, response.data);
    return response;
  },
  (error) => {
    console.error('Axios error:', error.response?.status, error.response?.data);
    return Promise.reject(error);
  }
);

export default axios; 