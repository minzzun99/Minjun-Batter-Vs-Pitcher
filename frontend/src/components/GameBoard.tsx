import { useState, useEffect } from "react";
import type { PitchResponse } from "../types/game";
import { ZoneSelector } from "./ZoneSelector";
import { ScoreBoard } from "./ScoreBoard";
import "../styles/GameBoard.css";

interface GameBoardProps {
  gameId: string;
  gameMode: string;
  onPitch: (zoneNumber: number) => Promise<void>;
  pitchResult: PitchResponse | null;
  onRestart: () => void;
}

export const GameBoard = ({
  gameId,
  gameMode,
  onPitch,
  pitchResult,
  onRestart,
}: GameBoardProps) => {
  const [selectedZone, setSelectedZone] = useState<number | null>(null);
  const [isLoading, setIsLoading] = useState(false);
  const [showZoneInfo, setShowZoneInfo] = useState(false);

  // pitchResult 업데이트 감지
  useEffect(() => {
    if (pitchResult?.zoneInfoDto && isLoading) {
      setShowZoneInfo(true);
      
      // 2.5초 유지
      const timer = setTimeout(() => {
        setShowZoneInfo(false);
        setSelectedZone(null);
      }, 2500);
      
      return () => clearTimeout(timer);
    }
  }, [pitchResult, isLoading]);

  const handleZoneClick = async (zone: number) => {
    setSelectedZone(zone);
    setIsLoading(true);
    setShowZoneInfo(false);

    try {
      await onPitch(zone);
    } finally {
      // isLoading은 존 정보 표시 후에 끄기
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

    if (my > com) return "🏆 승리!";
    if (my < com) return "😢 패배...";
    return "🤝 무승부";
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
            <h2>🎉 게임 종료!</h2>

            <div className="game-over-score">
              {pitchResult!.scoreBoard.myScore} :{" "}
              {pitchResult!.scoreBoard.computerScore}
            </div>

            <div className="game-over-result">{getResultMessage()}</div>

            <button className="game-over-restart" onClick={onRestart}>
              새 게임 시작
            </button>
          </div>
        </div>
      )}
    </div>
  );
};