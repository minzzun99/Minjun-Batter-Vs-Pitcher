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
    </div>
  );
};
