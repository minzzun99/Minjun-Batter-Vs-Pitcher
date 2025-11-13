import { useState } from 'react';
import { PitchResponse } from '../types/game';
import { ZoneSelector } from './ZoneSelector';
import { ScoreBoard } from './ScoreBoard';

interface GameBoardProps {
  gameId: string;
  gameMode: string;
  onPitch: (zoneNumber: number) => Promise<void>;
  pitchResult: PitchResponse | null;
}

export const GameBoard = ({ gameId, gameMode, onPitch, pitchResult }: GameBoardProps) => {
  const [selectedZone, setSelectedZone] = useState<number | null>(null);
  const [isLoading, setIsLoading] = useState(false);

  const handleZoneClick = async (zone: number) => {
    setSelectedZone(zone);
    setIsLoading(true);
    try {
      await onPitch(zone);
    } finally {
      setIsLoading(false);
      setSelectedZone(null);
    }
  };

  return (
    <div style={styles.container}>
      <h2 style={styles.title}>
        {gameMode === 'PITCHER' ? '🎯 투수 모드' : '🏏 타자 모드'}
      </h2>
      
      <div style={styles.gameId}>Game ID: {gameId}</div>

      {pitchResult && (
        <ScoreBoard
          count={pitchResult.count}
          runners={pitchResult.runners}
          scoreBoard={pitchResult.scoreBoard}
          pitchResult={pitchResult.pitchResult}
        />
      )}

      <ZoneSelector
        onZoneClick={handleZoneClick}
        selectedZone={selectedZone}
        disabled={isLoading || (pitchResult?.gameOver || false)}
      />

      {pitchResult?.gameOver && (
        <div style={styles.gameOver}>
          <h2>🎉 게임 종료!</h2>
          <div style={styles.finalScore}>
            {pitchResult.scoreBoard.myScore} : {pitchResult.scoreBoard.computerScore}
          </div>
        </div>
      )}
    </div>
  );
};

const styles = {
  container: {
    display: 'flex',
    flexDirection: 'column' as const,
    alignItems: 'center',
    padding: '20px',
    minHeight: '100vh',
    backgroundColor: '#f5f5f5',
  },
  title: {
    fontSize: '32px',
    fontWeight: 'bold',
    marginBottom: '10px',
  },
  gameId: {
    fontSize: '12px',
    color: '#999',
    marginBottom: '20px',
  },
  gameOver: {
    marginTop: '40px',
    padding: '30px',
    backgroundColor: 'white',
    borderRadius: '12px',
    boxShadow: '0 4px 6px rgba(0,0,0,0.1)',
    textAlign: 'center' as const,
  },
  finalScore: {
    fontSize: '48px',
    fontWeight: 'bold',
    color: '#007bff',
    marginTop: '20px',
  },
};