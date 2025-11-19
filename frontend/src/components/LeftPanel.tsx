import "../styles/LeftPanel.css";

interface LeftPanelProps {
    count: { strike: number; out: number } | null;
    hitterInfo: {
        avg: string;
        slg: string;
        ops: string;
    } | null;
    onQuit?: () => void;
    isGameOver?: boolean;
}

export const LeftPanel = ({
                              count,
                              hitterInfo,
                              onQuit,
                              isGameOver = false
}: LeftPanelProps) => {
    const safeInfo = hitterInfo ?? {avg: "0.000", slg: "0.000", ops: "0.000"};

    const handleQuit = () => {
        if (confirm("정말 경기를 중단하시겠습니까?")) {
            onQuit?.();
        }
    };

    return (
        <div className="left-panel">
            {!isGameOver && onQuit && (
                <button className="lp-quit-button" onClick={handleQuit}>
                    경기 중단
                </button>
            )}

            {/* 카운트 (Out / Strike) */}
            <div className="lp-count-container">
                <div className="lp-count-box out">
                    <p className="lp-count-label">O</p>
                    <p className="lp-count-value">{count?.out ?? 0}</p>
                </div>

                <div className="lp-count-box strike">
                    <p className="lp-count-label">S</p>
                    <p className="lp-count-value">{count?.strike ?? 0}</p>
                </div>
            </div>

            {/* 타자 정보 */}
            <h3 className="lp-title">상대 타자 정보</h3>

            <div className="lp-info-row">
                <span>타율 AVG</span>
                <span className="lp-value">{safeInfo.avg}</span>
            </div>

            <div className="lp-info-row">
                <span>장타율 SLG</span>
                <span className="lp-value">{safeInfo.slg}</span>
            </div>

            <div className="lp-info-row">
                <span>OPS</span>
                <span className="lp-value highlight">{safeInfo.ops}</span>
            </div>
        </div>
    );
};
