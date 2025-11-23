import "../styles/CenterPanel.css";
import {pitchResultToKorean} from "../utils/PitchResultMapper";
import type {PitchResponse, ScoreBoard} from "../types/game";

interface CenterPanelProps {
    gameMode: string;
    scoreBoard: ScoreBoard | undefined;
    pitchResult: PitchResponse | null;
    playerName?: string;
}

export const CenterPanel = ({
                                gameMode, scoreBoard, pitchResult, playerName
                            }: CenterPanelProps) => {

    const getKoreanResult = () => {
        if (!pitchResult || !pitchResult.pitchResult) return "준비";

        const {type, detail} = pitchResult.pitchResult;

        const typeKo = pitchResultToKorean[type] ?? type;
        const detailKo = detail ? (pitchResultToKorean[detail] ?? detail) : null;

        return detailKo ? `${typeKo} - ${detailKo}` : typeKo;
    };

    return (
        <div className="center-panel">
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
                    <span className="center-score-label">타자</span>
                    <span className="center-score-value">{playerName || "알 수 없음"}</span>
                </div>
            </div>

            {/* 투구 결과 */}
            <div className="center-result-box">
                {getKoreanResult()}
            </div>
        </div>
    );
};
