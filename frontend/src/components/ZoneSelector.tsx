import "../styles/ZoneSelector.css";
import type {ZoneInfoDto} from "../types/game";

interface ZoneSelectorProps {
    onZoneClick: (zone: number) => void;
    selectedZone: number | null;
    disabled: boolean;
    zoneInfo?: ZoneInfoDto | null;
    gameMode?: string;
}

export const ZoneSelector = ({
                                 onZoneClick,
                                 selectedZone,
                                 disabled,
                                 zoneInfo,
                             }: ZoneSelectorProps) => {
    const zones = [1, 2, 3, 4, 5, 6, 7, 8, 9];

    const getZoneClassName = (zone: number) => {
        let className = "zone-btn";

        if (selectedZone === zone) {
            className += " selected";
        }

        if (zoneInfo) {
            if (zoneInfo.hotZone === zone) {
                className += " hot-zone";
            }
            if (zoneInfo.coldZone === zone) {
                className += " cold-zone";
            }
        }

        if (disabled) {
            className += " disabled";
        }

        return className;
    };

    const renderBall = (zone: number) => {
        if (zoneInfo && zoneInfo.pitchZone === zone) {
            return <div className="baseball">⚾</div>;
        }
        return null;
    };

    return (
        <div className="zone-container">
            <div className="zone-title">스트라이크 존</div>

            <div className="zone-grid">
                {zones.map((zone) => (
                    <button
                        key={zone}
                        className={getZoneClassName(zone)}
                        onClick={() => !disabled && onZoneClick(zone)}
                        disabled={disabled}
                    >
                        <span className="zone-number">{zone}</span>
                        {renderBall(zone)}
                    </button>
                ))}
            </div>
        </div>
    );
};
