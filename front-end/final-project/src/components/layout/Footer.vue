<template>
    <footer class="footer">
      <p>&copy; 2024 finterest. All rights reserved.</p>
      <ul class="footer-links">
        <li><router-link to="/privacy">Privacy Policy</router-link></li>
        <li><router-link to="/terms">Terms of Service</router-link></li>
      </ul>
    </footer>
  </template>
  
  <script>
  export default {
    name: 'Footer',
  };
  </script>
  
  <style scoped>
  .footer {
    background-color: #333;
    color: white;
    padding: 20px;
    text-align: center;
    position: absolute;
    bottom: 0;
    width: 100%;
  }
  
  .footer-links {
    list-style: none;
    margin: 10px 0 0;
    padding: 0;
    display: flex;
    justify-content: center;
  }
  
  .footer-links li {
    margin: 0 10px;
  }
  
  .footer-links a {
    color: white;
    text-decoration: none;
  }
  
  .footer-links a:hover {
    text-decoration: underline;
  }
  </style>
  