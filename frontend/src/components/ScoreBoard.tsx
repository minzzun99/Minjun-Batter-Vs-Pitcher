import type {
  Count,
  BaseRunner,
  ScoreBoard as ScoreBoardType,
  PitchResult,
} from "../types/game";
import "../styles/ScoreBoard.css";
import { pitchResultToKorean } from "../utils/PitchResultMapper";

interface ScoreBoardProps {
  count: Count;
  runners: BaseRunner;
  scoreBoard: ScoreBoardType;
  pitchResult: PitchResult;
}

export const ScoreBoard = ({
  count,
  runners,
  scoreBoard,
  pitchResult,
}: ScoreBoardProps) => {
  return (
    <div className="sb-container">
      {/* 투구 결과 */}
      <div className="sb-result">
        <div className="sb-section-title">투구 결과</div>
        <div className="sb-result-value">
          {pitchResultToKorean[pitchResult.type] ?? pitchResult.type}
          {pitchResult.detail &&
            ` - ${
              pitchResultToKorean[pitchResult.detail] ?? pitchResult.detail
            }`}
        </div>
      </div>

      {/* 점수판 */}
      <div className="sb-score">
        <div className="sb-score-box">
          <div className="sb-score-label">나</div>
          <div className="sb-score-value">{scoreBoard.myScore}</div>
        </div>

        <div className="sb-score-divider">:</div>

        <div className="sb-score-box">
          <div className="sb-score-label">컴퓨터</div>
          <div className="sb-score-value">{scoreBoard.computerScore}</div>
        </div>
      </div>

      {/* 카운트 */}
      <div className="sb-count">
        <div className="sb-count-item">
          <span className="sb-count-label">S</span>
          <span className="sb-count-value">{count.strike}</span>
        </div>

        <div className="sb-count-item">
          <span className="sb-count-label">O</span>
          <span className="sb-count-value">{count.out}</span>
        </div>
      </div>

      {/* 주자 베이스 */}
      <div className="sb-base">
        <div className="sb-section-title">주자</div>

        <div className="sb-base-diamond">
          {/* 2루 */}
          <div className="sb-base-2">
            <div
              className={`sb-runner ${
                runners.second ? "runner-on" : "runner-off"
              }`}
            >
              <span>2루</span>
            </div>
          </div>

          <div className="sb-base-row">
            {/* 3루 */}
            <div className="sb-base-3">
              <div
                className={`sb-runner ${
                  runners.third ? "runner-on" : "runner-off"
                }`}
              >
                <span>3루</span>
              </div>
            </div>

            {/* 1루 */}
            <div className="sb-base-1">
              <div
                className={`sb-runner ${
                  runners.first ? "runner-on" : "runner-off"
                }`}
              >
                <span>1루</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
