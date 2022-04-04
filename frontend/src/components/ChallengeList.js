import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const ChallengeList = () => {
  const navigate = useNavigate();
  const [challenges, setChallenges] = useState([]);
  const [isInviterFirst, setIsInviterFirst] = useState(false);

  const loadChallenges = async () => {
    axios({
      method: 'get',
      url: 'http://localhost:8080/challenge/all'
    }).then((response) => {
      setChallenges(response.data);
    }, (error) => {
      console.log(error);
    });
  }
  useEffect(() => {
    loadChallenges();
  }, []);

  const joinChallenge = (challengeId) => {
    const formData = new FormData();
    formData.append("challengeId", challengeId);

    axios({
      method: 'post',
      url: 'http://localhost:8080/challenge/join',
      data: formData
    }).then((response) => {
      navigate('/challenge/' + challengeId)
    }, (error) => {
      console.log(error);
    });
  }

  const createChallenge = () => {
    const formData = new FormData();
    formData.append("isInviterFirst", isInviterFirst);

    axios({
      method: 'post',
      url: 'http://localhost:8080/challenge/create',
      data: formData
    }).then((response) => {
      navigate('/challenge/' + response.data.id)
    }, (error) => {
      console.log(error);
    });
  }

  return (
    <>
      <ul>
        { challenges.map(function(challenge) {
           return (<li key={ challenge.id }>
              Created: { challenge.created }<br />
              Inviter: { challenge.inviter.username }<br />
              isInviterFirst: { challenge.isInviterFirst ? 'Yes' : 'No' }<br />
              <button onClick={ () => joinChallenge(challenge.id) }>Join challenge</button><br /><br />
            </li>);
         })
        }
      </ul>
      <input
        type="checkbox"
        checked={ isInviterFirst }
        onChange={ () => setIsInviterFirst(!isInviterFirst) }
      />
      isInviterFirst<br />
      <button onClick={ () => createChallenge() }>Create challenge</button>
    </>
  );
}

export default ChallengeList;
