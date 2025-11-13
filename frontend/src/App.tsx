import { useState } from "react";
import { GameStart } from "./components/GameStart";
import { GameBoard } from "./components/GameBoard";
import { gameApi } from "./services/gameApi";
import type { GameMode, PitchResponse } from "./types/game";

function App() {
  const [gameStarted, setGameStarted] = useState(false);
  const [gameId, setGameId] = useState("");
  const [gameMode, setGameMode] = useState<GameMode | null>(null);
  const [pitchResult, setPitchResult] = useState<PitchResponse | null>(null);

  const handleGameStart = async (mode: GameMode) => {
    try {
      const response = await gameApi.startGame(mode);

      setGameId(response.gameId);
      setGameMode(response.gameMode);
      setGameStarted(true);

      // 게임 시작 시 기본 상태 설정
      setPitchResult({
        pitchResult: { type: "READY", detail: null },
        count: { strike: 0, out: 0 },
        runners: { first: false, second: false, third: false },
        scoreBoard: response.scoreBoard,
        isGameOver: false,
      });
    } catch (e) {
      alert("게임 시작 실패! 백엔드 서버 실행 상태를 확인하세요.");
    }
  };

  const handlePitch = async (zoneNumber: number) => {
    if (!gameId) return;

    try {
      const result = await gameApi.pitch(gameId, zoneNumber);
      setPitchResult(result);
    } catch (e) {
      alert("투구 처리 실패!");
    }
  };

  const handleRestart = () => {
    setGameStarted(false);
    setGameId("");
    setGameMode(null);
    setPitchResult(null);
  };

  return (
    <>
      {!gameStarted ? (
        <GameStart onGameStart={handleGameStart} />
      ) : (
        <GameBoard
          gameId={gameId}
          gameMode={gameMode!}
          pitchResult={pitchResult}
          onPitch={handlePitch}
          onRestart={handleRestart}
        />
      )}
    </>
  );
}

export default App;
