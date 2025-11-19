import "../styles/CenterPanel.css";
import { pitchResultToKorean } from "../utils/PitchResultMapper";

export const CenterPanel = ({ gameMode, gameId, scoreBoard, pitchResult }) => {

  const getKoreanResult = () => {
    if (!pitchResult || !pitchResult.pitchResult) return "";

    const { type, detail } = pitchResult.pitchResult;

    const typeKo = pitchResultToKorean[type] ?? type;
    const detailKo = detail ? (pitchResultToKorean[detail] ?? detail) : null;

    return detailKo ? `${typeKo} - ${detailKo}` : typeKo;
  };

  return (
    <div className="center-panel">
      {/* 모드 배지 */}
      <div className="center-mode-badge">
        {gameMode === "PITCHER" ? "투수 모드" : "타자 모드"}
      </div>

      {/* 점수판 */}
      <div className="center-score-box">
        <div className="center-score-row">
          <span className="center-score-label">HOME</span>
          <span className="center-score-value">{scoreBoard?.myScore ?? 0}</span>
        </div>

        <div className="center-score-row">
          <span className="center-score-label">AWAY</span>
          <span className="center-score-value">
            {scoreBoard?.computerScore ?? 0}
          </span>
        </div>

        <div className="center-divider"></div>

        <div className="center-score-row">
          <span className="center-score-label">Game ID</span>
          <span className="center-score-value">{gameId}</span>
        </div>
      </div>

      {/* 투구 결과 */}
      <div className="center-result-box">
        <div className="center-result-text">{getKoreanResult()}</div>
      </div>
    </div>
  );
};
