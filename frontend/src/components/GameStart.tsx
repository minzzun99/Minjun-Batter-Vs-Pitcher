import { useState, useEffect } from "react";
import { GameMode } from "../types/game";
import { Link } from "react-router-dom";
import "../styles/GameStart.css";

interface PlayerDto {
  id: number;
  name: string;
  totalAtBats: number;
  singles: number;
  doubles: number;
  triples: number;
  homeRuns: number;
  battingAverage: number;
}

interface GameStartProps {
  onGameStart: (
      mode: GameMode,
      playerId: number,
      playerData: { name: string; avg: number; slg: number; ops: number }
  ) => void;
  seasonAvg: number;
  seasonEra: number;
}

export const GameStart = ({ onGameStart }: GameStartProps) => {
  const [selectedMode, setSelectedMode] = useState<GameMode | null>(null);
  const [players, setPlayers] = useState<PlayerDto[]>([]);
  const [selectedPlayer, setSelectedPlayer] = useState<PlayerDto | null>(null);

  useEffect(() => {
    fetch("/api/game/players")
        .then((res) => res.json())
        .then((data) => setPlayers(data))
        .catch(() => alert("선수 목록 불러오기 실패"));
  }, []);

  // 계산 함수 -------------------------------
  const calcTotalHits = (p: PlayerDto) =>
      p.singles + p.doubles + p.triples + p.homeRuns;

  const calcTotalBases = (p: PlayerDto) =>
      p.singles * 1 + p.doubles * 2 + p.triples * 3 + p.homeRuns * 4;

  const calcAVG = (p: PlayerDto) =>
      p.totalAtBats > 0 ? (calcTotalHits(p) / p.totalAtBats) : 0;

  const calcSLG = (p: PlayerDto) =>
      p.totalAtBats > 0 ? (calcTotalBases(p) / p.totalAtBats) : 0;

  const calcOPS = (p: PlayerDto) => {
    const avg = calcAVG(p);
    const slg = calcSLG(p);
    return avg + slg;
  };

  // 기본값
  const defaultStats = {
    avg: "0.000",
    slg: "0.000",
    ops: "0.000",
  };

  const stats = selectedPlayer
      ? {
        avg: calcAVG(selectedPlayer).toFixed(3),
        slg: calcSLG(selectedPlayer).toFixed(3),
        ops: calcOPS(selectedPlayer).toFixed(3),
      }
      : defaultStats;

  // 게임 시작
  const handleStart = () => {
    if (!selectedMode || !selectedPlayer) {
      alert("모드와 선수를 선택해주세요!");
      return;
    }

    // ✅ 계산된 값 사용!
    onGameStart(
        selectedMode,
        selectedPlayer.id,
        {
          name: selectedPlayer.name,
          avg: calcAVG(selectedPlayer),
          slg: calcSLG(selectedPlayer),
          ops: calcOPS(selectedPlayer)
        }
    );
  };

  return (
      <div className="gs-container stadium">
        {/* 관리자 버튼 */}
        <Link to="/admin/players" className="admin-btn">
          <span className="admin-label">⚙</span>
        </Link>

        {/* 히어로 */}
        <div className="stadium-inner gs-hero-bg">
          <div className="gs-hero-content">
            <h1 className="gs-title">실제 기록 기반 미니 야구게임</h1>
            <p className="gs-subtitle">당신의 기록으로 야구게임을 즐겨보세요!</p>

            {/* 모드 선택 */}
            <div className="gs-mode-container">
              <button
                  className={`gs-mode-btn ${
                      selectedMode === GameMode.BATTER ? "selected" : ""
                  }`}
                  onClick={() => setSelectedMode(GameMode.BATTER)}
              >
                타자 모드
              </button>

              <button
                  className={`gs-mode-btn ${
                      selectedMode === GameMode.PITCHER ? "selected" : ""
                  }`}
                  onClick={() => setSelectedMode(GameMode.PITCHER)}
              >
                투수 모드
              </button>
            </div>
          </div>
        </div>

        {/* 선수 선택 + 기본값 표시 */}
        {selectedMode && (
            <div className="player-select-section">

              {/* 선수 선택 */}
              <div className="player-select-wrapper">
                <label className="player-label">선수 선택</label>

                <select
                    className="player-select"
                    value={selectedPlayer?.id ?? ""}
                    onChange={(e) => {
                      const id = Number(e.target.value);
                      const player = players.find((p) => p.id === id) || null;
                      setSelectedPlayer(player);
                    }}
                >
                  <option value="" disabled>
                    {players.length === 0
                        ? "등록된 선수가 없습니다"
                        : "선수를 선택하세요"}
                  </option>

                  {players.map((p) => (
                      <option key={p.id} value={p.id}>
                        {p.name}
                      </option>
                  ))}
                </select>
              </div>

              {/* 선택 전에도 0.000 기본값 보여줌 */}
              <div className="player-stats-grid">
                <div className="player-stat-box">
                  <div className="player-stat-label">타율 AVG</div>
                  <div className="player-stat-value">{stats.avg}</div>
                </div>

                <div className="player-stat-box">
                  <div className="player-stat-label">장타율 SLG</div>
                  <div className="player-stat-value">{stats.slg}</div>
                </div>

                <div className="player-stat-box">
                  <div className="player-stat-label">OPS</div>
                  <div className="player-stat-value">{stats.ops}</div>
                </div>
              </div>
            </div>
        )}

        {/* 게임 시작 버튼 */}
        <div className="gs-start-container">
          <button
              className={`gs-start-btn ${
                  selectedMode && selectedPlayer ? "" : "disabled"
              }`}
              onClick={handleStart}
              disabled={!selectedMode || !selectedPlayer}
          >
            게임 시작
          </button>
        </div>
      </div>
  );
};