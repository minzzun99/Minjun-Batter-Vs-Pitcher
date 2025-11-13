import "../styles/ZoneSelector.css";

interface ZoneSelectorProps {
  onZoneClick: (zone: number) => void;
  selectedZone: number | null;
  disabled: boolean;
}

export const ZoneSelector = ({
  onZoneClick,
  selectedZone,
  disabled,
}: ZoneSelectorProps) => {
  const zones = [1, 2, 3, 4, 5, 6, 7, 8, 9];

  return (
    <div className="zone-container">
      <div className="zone-title">⚾ 스트라이크 존</div>

      <div className="zone-grid">
        {zones.map((zone) => (
          <button
            key={zone}
            className={`zone-btn 
              ${selectedZone === zone ? "selected" : ""} 
              ${disabled ? "disabled" : ""}`}
            onClick={() => !disabled && onZoneClick(zone)}
            disabled={disabled}
          >
            {zone}
          </button>
        ))}
      </div>
    </div>
  );
};