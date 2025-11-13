interface ZoneSelectorProps {
  onZoneClick: (zone: number) => void;
  selectedZone: number | null;
  disabled: boolean;
}

export const ZoneSelector = ({ onZoneClick, selectedZone, disabled }: ZoneSelectorProps) => {
  const zones = [1, 2, 3, 4, 5, 6, 7, 8, 9];

  return (
    <div style={styles.container}>
      <div style={styles.title}>⚾ 스트라이크 존</div>
      <div style={styles.grid}>
        {zones.map((zone) => (
          <button
            key={zone}
            style={{
              ...styles.zoneButton,
              ...(selectedZone === zone ? styles.selected : {}),
              ...(disabled ? styles.disabled : {}),
            }}
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

const styles = {
  container: {
    textAlign: 'center' as const,
  },
  title: {
    fontSize: '20px',
    fontWeight: 'bold',
    marginBottom: '20px',
  },
  grid: {
    display: 'grid',
    gridTemplateColumns: 'repeat(3, 1fr)',
    gap: '10px',
    maxWidth: '400px',
    margin: '0 auto',
  },
  zoneButton: {
    width: '120px',
    height: '120px',
    fontSize: '32px',
    fontWeight: 'bold',
    border: '3px solid #ddd',
    borderRadius: '12px',
    backgroundColor: 'white',
    cursor: 'pointer',
    transition: 'all 0.3s',
  },
  selected: {
    backgroundColor: '#007bff',
    color: 'white',
    borderColor: '#0056b3',
    transform: 'scale(0.95)',
  },
  disabled: {
    opacity: 0.5,
    cursor: 'not-allowed',
  },
};