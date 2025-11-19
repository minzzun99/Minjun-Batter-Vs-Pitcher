import "../styles/RightPanel.css";

export const RightPanel = ({ runners }) => {
  return (
    <div className="right-panel">
      <h3 className="right-panel-title">주자 상황</h3>

      <div className="base-diamond">
        {/* 2루 */}
        <div className={`base-second ${runners?.second ? "runner-on" : "runner-off"}`}>
          <span className="base-label">2루</span>
        </div>

        {/* 3루 */}
        <div className={`base-third ${runners?.third ? "runner-on" : "runner-off"}`}>
          <span className="base-label">3루</span>
        </div>

        {/* 1루 */}
        <div className={`base-first ${runners?.first ? "runner-on" : "runner-off"}`}>
          <span className="base-label">1루</span>
        </div>

        {/* 홈 */}
        <div className="base-home"></div>
      </div>

      <div className="zone-legend">
        <div className="legend-item">
          <div className="legend-color hot"></div>
          <span>핫존 (안타 확률 높음)</span>
        </div>
        <div className="legend-item">
          <div className="legend-color cold"></div>
          <span>콜드존 (안타 확률 낮음)</span>
        </div>
        <div className="legend-item">
          <div className="legend-color ball">⚾</div>
          <span>실제 공 위치</span>
        </div>
      </div>
    </div>
  );
};
