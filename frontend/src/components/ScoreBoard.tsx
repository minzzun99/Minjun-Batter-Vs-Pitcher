import { Count, BaseRunner, ScoreBoard as ScoreBoardType, PitchResult } from '../types/game';

interface ScoreBoardProps {
  count: Count;
  runners: BaseRunner;
  scoreBoard: ScoreBoardType;
  pitchResult: PitchResult;
}

export const ScoreBoard = ({ count, runners, scoreBoard, pitchResult }: ScoreBoardProps) => {
  return (
    <div style={styles.container}>
      {/* 투구 결과 */}
      <div style={styles.resultSection}>
        <div style={styles.resultTitle}>투구 결과</div>
        <div style={styles.resultValue}>
          {pitchResult.type}
          {pitchResult.detail && ` - ${pitchResult.detail}`}
        </div>
      </div>

      {/* 점수판 */}
      <div style={styles.scoreSection}>
        <div style={styles.scoreBox}>
          <div style={styles.scoreLabel}>나</div>
          <div style={styles.scoreValue}>{scoreBoard.myScore}</div>
        </div>
        <div style={styles.scoreDivider}>:</div>
        <div style={styles.scoreBox}>
          <div style={styles.scoreLabel}>컴퓨터</div>
          <div style={styles.scoreValue}>{scoreBoard.computerScore}</div>
        </div>
      </div>

      {/* 카운트 */}
      <div style={styles.countSection}>
        <div style={styles.countItem}>
          <span style={styles.countLabel}>S</span>
          <span style={styles.countValue}>{count.strike}</span>
        </div>
        <div style={styles.countItem}>
          <span style={styles.countLabel}>O</span>
          <span style={styles.countValue}>{count.out}</span>
        </div>
      </div>

      {/* 주자 */}
      <div style={styles.baseSection}>
        <div style={styles.baseTitle}>주자</div>
        <div style={styles.baseDiamond}>
          <div style={styles.base2}>
            <div style={runners.second ? styles.runnerOn : styles.runnerOff}>2루</div>
          </div>
          <div style={styles.baseRow}>
            <div style={styles.base3}>
              <div style={runners.third ? styles.runnerOn : styles.runnerOff}>3루</div>
            </div>
            <div style={styles.base1}>
              <div style={runners.first ? styles.runnerOn : styles.runnerOff}>1루</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

const styles = {
  container: {
    width: '100%',
    maxWidth: '600px',
    backgroundColor: 'white',
    borderRadius: '12px',
    padding: '20px',
    marginBottom: '30px',
    boxShadow: '0 2px 8px rgba(0,0,0,0.1)',
  },
  resultSection: {
    textAlign: 'center' as const,
    padding: '15px',
    backgroundColor: '#f8f9fa',
    borderRadius: '8px',
    marginBottom: '20px',
  },
  resultTitle: {
    fontSize: '14px',
    color: '#666',
    marginBottom: '5px',
  },
  resultValue: {
    fontSize: '24px',
    fontWeight: 'bold',
    color: '#007bff',
  },
  scoreSection: {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    gap: '20px',
    marginBottom: '20px',
  },
  scoreBox: {
    textAlign: 'center' as const,
  },
  scoreLabel: {
    fontSize: '16px',
    color: '#666',
    marginBottom: '5px',
  },
  scoreValue: {
    fontSize: '48px',
    fontWeight: 'bold',
    color: '#333',
  },
  scoreDivider: {
    fontSize: '48px',
    fontWeight: 'bold',
    color: '#999',
  },
  countSection: {
    display: 'flex',
    justifyContent: 'center',
    gap: '30px',
    marginBottom: '20px',
    padding: '15px',
    backgroundColor: '#f8f9fa',
    borderRadius: '8px',
  },
  countItem: {
    display: 'flex',
    alignItems: 'center',
    gap: '10px',
  },
  countLabel: {
    fontSize: '18px',
    fontWeight: 'bold',
    color: '#666',
  },
  countValue: {
    fontSize: '32px',
    fontWeight: 'bold',
    color: '#333',
  },
  baseSection: {
    textAlign: 'center' as const,
  },
  baseTitle: {
    fontSize: '16px',
    color: '#666',
    marginBottom: '15px',
  },
  baseDiamond: {
    display: 'flex',
    flexDirection: 'column' as const,
    alignItems: 'center',
    gap: '10px',
  },
  baseRow: {
    display: 'flex',
    gap: '60px',
  },
  base1: {},
  base2: {},
  base3: {},
  runnerOn: {
    width: '60px',
    height: '60px',
    backgroundColor: '#007bff',
    color: 'white',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    borderRadius: '8px',
    fontWeight: 'bold',
    transform: 'rotate(45deg)',
  },
  runnerOff: {
    width: '60px',
    height: '60px',
    backgroundColor: '#e9ecef',
    color: '#999',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    borderRadius: '8px',
    transform: 'rotate(45deg)',
  },
};