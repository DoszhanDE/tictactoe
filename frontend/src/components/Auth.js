import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const Auth = () => {
  const navigate = useNavigate();
  const [username, setUsername] = useState("");

  const auth = async (e) => {
    const formData = new FormData();
    formData.append("username", username);

    axios({
      method: 'post',
      url: 'http://localhost:8080/user/auth',
      data: formData
    }).then((response) => {
      localStorage.setItem('token', response.data.token);
      localStorage.setItem('userId', response.data.id);
      navigate('/challenges')
    }, (error) => {
      console.log(error);
    });

  }

  return (
    <>
      <input
        type="text"
        placeholder="Enter username"
        value={ username }
        onChange={(e) => setUsername(e.target.value) }
      />
      <button
        onClick={ auth }
        >Login
      </button>
    </>
  );
}

export default Auth;
