import { useState, useEffect } from "react";
import type { PitchResponse, GameStatistics } from "../types/game";
import { ZoneSelector } from "./ZoneSelector";
import { ScoreBoard } from "./ScoreBoard";
import "../styles/GameBoard.css";

interface GameBoardProps {
  gameId: string;
  gameMode: string;
  onPitch: (zoneNumber: number) => Promise<void>;
  pitchResult: PitchResponse | null;
  onGoHome: () => void;
  onRetry: () => void;
}

export const GameBoard = ({
  gameId,
  gameMode,
  onPitch,
  pitchResult,
  onGoHome,
  onRetry,
}: GameBoardProps) => {
  const [selectedZone, setSelectedZone] = useState<number | null>(null);
  const [isLoading, setIsLoading] = useState(false);
  const [showZoneInfo, setShowZoneInfo] = useState(false);
  const [gameStats, setGameStats] = useState<GameStatistics | null>(null);

  // pitchResult 업데이트 감지
  useEffect(() => {
    if (pitchResult?.zoneInfoDto && isLoading) {
      setShowZoneInfo(true);

      const timer = setTimeout(() => {
        setShowZoneInfo(false);
        setSelectedZone(null);
      }, 2500);

      return () => clearTimeout(timer);
    }
  }, [pitchResult, isLoading]);

  useEffect(() => {
    if (pitchResult?.isGameOver && !gameStats) {
      fetchGameResult();
    }
  }, [pitchResult?.isGameOver]);

  const fetchGameResult = async () => {
    try {
      const response = await fetch(`/api/game/${gameId}/result`);
      const data = await response.json();

      console.log("Game Result:", data);
      setGameStats(data.gameStatistics);
    } catch (error) {
      console.error("Failed to fetch game result:", error);
    }
  };

  const handleZoneClick = async (zone: number) => {
    setSelectedZone(zone);
    setIsLoading(true);
    setShowZoneInfo(false);

    try {
      await onPitch(zone);
    } finally {
      setTimeout(() => {
        setIsLoading(false);
      }, 2600);
    }
  };

  const isGameOver = pitchResult?.isGameOver ?? false;

  const getResultMessage = () => {
    if (!pitchResult) return "";

    const my = pitchResult.scoreBoard.myScore;
    const com = pitchResult.scoreBoard.computerScore;

    if (my > com) return "승리!";
    if (my < com) return "패배..";
    return "무승부";
  };

  return (
    <div className="gameboard-container stadium">
      <div className="stadium-inner gameboard-inner">
        {/* HEADER */}
        <div className="gameboard-header">
          <h2 className="gameboard-title">
            {gameMode === "PITCHER" ? "🎯 투수 모드" : "🏏 타자 모드"}
          </h2>

          <div className="gameboard-id">Game ID: {gameId}</div>
        </div>

        {/* SCOREBOARD */}
        {pitchResult && (
          <ScoreBoard
            count={pitchResult.count}
            runners={pitchResult.runners}
            scoreBoard={pitchResult.scoreBoard}
            pitchResult={pitchResult.pitchResult}
          />
        )}

        {/* ZONE SELECTOR */}
        <ZoneSelector
          onZoneClick={handleZoneClick}
          selectedZone={selectedZone}
          disabled={isLoading || isGameOver}
          zoneInfo={showZoneInfo ? pitchResult?.zoneInfoDto : null}
          gameMode={gameMode}
        />
      </div>

      {/* GAME OVER MODAL */}
      {isGameOver && (
        <div className="game-over-modal-overlay">
          <div className="game-over-modal">
            <h1>게임 결과</h1>

            <div className="game-over-score">
              {pitchResult!.scoreBoard.myScore} :{" "}
              {pitchResult!.scoreBoard.computerScore}
            </div>

            <div className="game-over-result">{getResultMessage()}</div>

            {/* 경기 통계 */}
            {gameStats ? (
              <div className="game-stats-section">
                <h2 className="game-stats-title">경기 기록</h2>

                <div className="game-stats-category">
                  <h3>안타 통계</h3>
                  <div className="game-stats-grid">
                    <div className="stat-box">
                      <span className="stat-label">1루타</span>
                      <span className="stat-value">{gameStats.singles}</span>
                    </div>
                    <div className="stat-box">
                      <span className="stat-label">2루타</span>
                      <span className="stat-value">{gameStats.doubles}</span>
                    </div>
                    <div className="stat-box">
                      <span className="stat-label">3루타</span>
                      <span className="stat-value">{gameStats.triples}</span>
                    </div>
                    <div className="stat-box">
                      <span className="stat-label">홈런</span>
                      <span className="stat-value">{gameStats.homeRuns}</span>
                    </div>
                  </div>
                </div>

                <div className="game-stats-category">
                  <h3>아웃 통계</h3>
                  <div className="outs-grid">
                    <div className="stat-box">
                      <span className="stat-label">삼진</span>
                      <span className="stat-value">{gameStats.strikeOuts}</span>
                    </div>
                    <div className="stat-box">
                      <span className="stat-label">땅볼</span>
                      <span className="stat-value">{gameStats.groundOuts}</span>
                    </div>
                    <div className="stat-box">
                      <span className="stat-label">플라이</span>
                      <span className="stat-value">{gameStats.flyOuts}</span>
                    </div>
                  </div>
                </div>

                <div className="game-stats-summary">
                  <div className="stat-highlight">
                    <span className="stat-label">타율</span>
                    <span className="stat-value-big">
                      {gameStats.battingAverage.toFixed(3)}
                    </span>
                  </div>
                  <div className="stat-highlight">
                    <span className="stat-label">총 안타</span>
                    <span className="stat-value-big">
                      {gameStats.totalHits}
                    </span>
                  </div>
                  <div className="stat-highlight">
                    <span className="stat-label">총 타수</span>
                    <span className="stat-value-big">
                      {gameStats.totalAtBats}
                    </span>
                  </div>
                </div>
              </div>
            ) : (
              <div className="loading-stats">통계 불러오는 중...</div>
            )}

            <div className="game-over-buttons">
              <button className="game-over-btn retry" onClick={onRetry}>
                다시 하기
              </button>

              <button className="game-over-btn home" onClick={onGoHome}>
                새 게임
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};
