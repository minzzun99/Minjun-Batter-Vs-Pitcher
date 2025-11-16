import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { useState } from "react";
import { GameStart } from "./components/GameStart";
import { GameBoard } from "./components/GameBoard";
import { gameApi } from "./services/gameApi";
import type { GameMode, PitchResponse } from "./types/game";
import AdminPage from "./admin/AdminPage";

function App() {
  const [gameStarted, setGameStarted] = useState(false);
  const [gameId, setGameId] = useState("");
  const [gameMode, setGameMode] = useState<GameMode | null>(null);
  const [pitchResult, setPitchResult] = useState<PitchResponse | null>(null);

  // 시즌 기록
  const seasonAvg = 0.000;
  const seasonEra = 0.00;

  // mode + playerId를 받아서 게임 시작
  const handleGameStart = async (mode: GameMode, playerId: number) => {
    try {
      const response = await gameApi.startGame(mode, playerId);

      setGameId(response.gameId);
      setGameMode(response.gameMode);
      setGameStarted(true);

      // 초기 상태
      setPitchResult({
        pitchResult: { type: "READY", detail: null },
        count: { strike: 0, out: 0 },
        runners: { first: false, second: false, third: false },
        scoreBoard: response.scoreBoard,
        isGameOver: false,
      });
    } catch (e) {
      console.error(e);
      alert("게임 시작 실패! 서버를 확인하세요.");
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
    <Router>
      <Routes>
        {/* 메인 게임 페이지 */}
        <Route
          path="/"
          element={
            !gameStarted ? (
              <GameStart
                onGameStart={handleGameStart}
                seasonAvg={seasonAvg}
                seasonEra={seasonEra}
              />
            ) : (
              <GameBoard
                gameId={gameId}
                gameMode={gameMode!}
                pitchResult={pitchResult}
                onPitch={handlePitch}
                onRestart={handleRestart}
              />
            )
          }
        />

        {/* 관리자 페이지 */}
        <Route path="/admin/players" element={<AdminPage />} />
      </Routes>
    </Router>
  );
}

export default App;
