import React, { useEffect, useState } from 'react';
import Board from './Board';
import axios from 'axios';
import { useParams } from 'react-router-dom';

const Game = () => {
  const { id } = useParams();
  const [challenge, setChallenge] = useState(null);
  const [isMyTurn, setIsMyTurn] = useState(null);
  const [steps, setSteps] = useState([]);
  const [squares, setSquares] = useState([]);
  const userId = localStorage.getItem('userId');

  const loadChallenge = () => {
    axios({
      method: 'get',
      url: 'http://localhost:8080/challenge/get?challengeId=' + id
    }).then((response) => {
      setChallenge(response.data);
    }, (error) => {
      console.log(error);
    });
  }

  const loadIsMyTurn = () => {
    axios({
      method: 'get',
      url: 'http://localhost:8080/step/isMyTurn?challengeId=' + id
    }).then((response) => {
      setIsMyTurn(response.data);
    }, (error) => {
      console.log(error);
    });
  }

  useEffect(() => {
    steps.map(step => {
      const letter = (step.user.id === userId) ? 'X' : 'O';
      squares[step.vertically * 3 + step.horizontally] = letter;
    });
  }, [steps]);

  const loadSteps = () => {
    axios({
      method: 'get',
      url: 'http://localhost:8080/step/allByChallenge?challengeId=' + id
    }).then((response) => {
      setSteps(response.data);
      loadIsMyTurn();
      loadChallenge();
    }, (error) => {
      console.log(error);
    });
  }
  
  useEffect(() => {
    const interval = setInterval(() => loadSteps(), 1000);
    return () => {
      clearInterval(interval);
    };
  }, []);

  const makeStep = (vertically, horizontally) => {
    const formData = new FormData();
    formData.append("challengeId", id);
    formData.append("horizontally", horizontally);
    formData.append("vertically", vertically);

    axios({
      method: 'post',
      url: 'http://localhost:8080/step/make',
      data: formData
    }).then((response) => {
      loadSteps();
    }, (error) => {
      console.log(error);
    });
  }

  const handleClick = (i) => {
    const vertically = Math.floor(i / 3);
    const horizontally = i % 3;
    
    makeStep(vertically, horizontally);
  }

  const { winner, winnerRow } = { winner: null, winnerRow: null };

  let status = '';
  if (challenge && challenge.winner) {
    status = `Winner ${challenge.winner.username}`;
  } else if (steps.length === 10) {
    status = 'Draw. No one won.';
  } else if (isMyTurn !== null) {
    status = isMyTurn ? 'Make a turn' : 'Wait opponent\'s turn';
  }


  return (
    <div className="game">
      <div className="game-board">
        <Board
          squares={ squares }
          winnerSquares={ winnerRow }
          onClick={ (i) => handleClick(i) }
        />
      </div>
      <div className="game-info">
        <div>{status}</div>
      </div>
    </div>
  );
    
}

export default Game;
