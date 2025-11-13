import { GameStart } from './components/GameStart';
import { GameMode } from './types/game';

function App() {
  const handleGameStart = (mode: GameMode) => {
    console.log('Game started with mode:', mode);
    // TODO: 게임 시작 로직
  };

  return (
    <div>
      <GameStart onGameStart={handleGameStart} />
    </div>
  );
}

export default App;