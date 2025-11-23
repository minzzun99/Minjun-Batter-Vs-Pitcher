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

            {/* 아웃, 스트라이크 카운트*/}
            <div className="lp-count-container">
                <div className="lp-count-box out">
                    <p className="lp-count-label out">OUT</p>
                    <p className="lp-count-value">{count?.out ?? 0}</p>
                </div>

                <div className="lp-count-box strike">
                    <p className="lp-count-label">STRIKE</p>
                    <p className="lp-count-value">{count?.strike ?? 0}</p>
                </div>
            </div>

            {/* 타자 정보 - 박스 형태 */}
            <div className="lp-section">
                <h3 className="lp-section-title">상대 타자 정보</h3>

                <div className="lp-stats-grid">
                    <div className="lp-stat-box">
                        <div className="lp-stat-label">타율 AVG</div>
                        <div className="lp-stat-value">{safeInfo.avg}</div>
                    </div>

                    <div className="lp-stat-box">
                        <div className="lp-stat-label">장타율 SLG</div>
                        <div className="lp-stat-value">{safeInfo.slg}</div>
                    </div>

                    <div className="lp-stat-box">
                        <div className="lp-stat-label">OPS</div>
                        <div className="lp-stat-value highlight">{safeInfo.ops}</div>
                    </div>
                </div>
            </div>
        </div>
    );
};
