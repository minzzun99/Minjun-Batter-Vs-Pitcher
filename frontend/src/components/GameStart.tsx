import { useState } from "react";
import { GameMode } from "../types/game";
import "../styles/GameStart.css";

interface GameStartProps {
  onGameStart: (mode: GameMode) => void;
}

export const GameStart = ({ onGameStart }: GameStartProps) => {
  const [selectedMode, setSelectedMode] = useState<GameMode | null>(null);

  const handleStart = () => {
    if (selectedMode) onGameStart(selectedMode);
  };

  return (
    <div className="gamestart-container stadium">
      <div className="stadium-inner">
        <div className="gamestart-content">
          <h1 className="gamestart-title">타자 강민준 VS 투수 강민준</h1>
          <p className="gamestart-subtitle">모드를 선택하세요</p>

          <div className="gamestart-mode-container">
            <button
              className={`gamestart-mode-btn ${
                selectedMode === GameMode.PITCHER ? "selected" : ""
              }`}
              onClick={() => setSelectedMode(GameMode.PITCHER)}
            >
              <div className="mode-title">🎯 투수 모드</div>
              <div className="mode-desc">존을 선택해서 투구하세요</div>
            </button>

            <button
              className={`gamestart-mode-btn ${
                selectedMode === GameMode.BATTER ? "selected" : ""
              }`}
              onClick={() => setSelectedMode(GameMode.BATTER)}
            >
              <div className="mode-title">🏏 타자 모드</div>
              <div className="mode-desc">핫존을 선택해서 타격하세요</div>
            </button>
          </div>

          <button
            className={`gamestart-start-btn ${selectedMode ? "" : "disabled"}`}
            onClick={handleStart}
            disabled={!selectedMode}
          >
            게임 시작
          </button>
        </div>
      </div>
    </div>
  );
};