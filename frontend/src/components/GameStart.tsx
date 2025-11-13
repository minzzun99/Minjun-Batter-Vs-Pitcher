import { useState } from 'react';
import { GameMode } from '../types/game';

interface GameStartProps {
  onGameStart: (mode: GameMode) => void;
}

export const GameStart = ({ onGameStart }: GameStartProps) => {
  const [selectedMode, setSelectedMode] = useState<GameMode | null>(null);

  const handleStart = () => {
    if (selectedMode) {
      onGameStart(selectedMode);
    }
  };

  return (
    <div style={styles.container}>
      <h1 style={styles.title}>타민준 VS 투민준</h1>
      <p style={styles.subtitle}>모드를 선택하세요</p>
      
      <div style={styles.modeContainer}>
        <button
          style={{
            ...styles.modeButton,
            ...(selectedMode === GameMode.PITCHER ? styles.selected : {})
          }}
          onClick={() => setSelectedMode(GameMode.PITCHER)}
        >
          <div style={styles.modeTitle}>🎯 투수 모드</div>
          <div style={styles.modeDesc}>존을 선택해서 투구하세요</div>
        </button>

        <button
          style={{
            ...styles.modeButton,
            ...(selectedMode === GameMode.BATTER ? styles.selected : {})
          }}
          onClick={() => setSelectedMode(GameMode.BATTER)}
        >
          <div style={styles.modeTitle}>🏏 타자 모드</div>
          <div style={styles.modeDesc}>핫존을 선택해서 타격하세요</div>
        </button>
      </div>

      <button
        style={{
          ...styles.startButton,
          ...(selectedMode ? {} : styles.disabled)
        }}
        onClick={handleStart}
        disabled={!selectedMode}
      >
        게임 시작
      </button>
    </div>
  );
};

const styles = {
  container: {
    display: 'flex',
    flexDirection: 'column' as const,
    alignItems: 'center',
    justifyContent: 'center',
    minHeight: '100vh',
    backgroundColor: '#f5f5f5',
    padding: '20px',
  },
  title: {
    fontSize: '48px',
    fontWeight: 'bold',
    color: '#333',
    marginBottom: '10px',
  },
  subtitle: {
    fontSize: '20px',
    color: '#666',
    marginBottom: '40px',
  },
  modeContainer: {
    display: 'flex',
    gap: '20px',
    marginBottom: '40px',
  },
  modeButton: {
    width: '250px',
    padding: '30px',
    border: '3px solid #ddd',
    borderRadius: '12px',
    backgroundColor: 'white',
    cursor: 'pointer',
    transition: 'all 0.3s',
  },
  selected: {
    borderColor: '#007bff',
    backgroundColor: '#e7f3ff',
    transform: 'scale(1.05)',
  },
  modeTitle: {
    fontSize: '24px',
    fontWeight: 'bold',
    marginBottom: '10px',
  },
  modeDesc: {
    fontSize: '14px',
    color: '#666',
  },
  startButton: {
    padding: '15px 60px',
    fontSize: '20px',
    fontWeight: 'bold',
    color: 'white',
    backgroundColor: '#007bff',
    border: 'none',
    borderRadius: '8px',
    cursor: 'pointer',
    transition: 'all 0.3s',
  },
  disabled: {
    backgroundColor: '#ccc',
    cursor: 'not-allowed',
  },
};