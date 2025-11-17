import "../styles/playerTable.css";

export default function PlayerTable({ players, onEdit, onDelete }) {
  return (
    <div className="playertable-wrapper">
      <table className="playertable">
        <thead>
          <tr>
            <th>ID</th>
            <th>이름</th>
            <th>타수</th>
            <th>1루타</th>
            <th>2루타</th>
            <th>3루타</th>
            <th>홈런</th>
            <th>타율</th>
            <th>수정 / 삭제</th>
          </tr>
        </thead>

        <tbody>
          {players.map((player) => (
            <tr key={player.id}>
              <td>{player.id}</td>
              <td>{player.name}</td>
              <td>{player.totalAtBats}</td>
              
              <td>{player.singles}</td>
              <td>{player.doubles}</td>
              <td>{player.triples}</td>
              <td>{player.homeRuns}</td>

              <td>{player.battingAverage.toFixed(3)}</td>

              <td className="playertable-actions">
                <button
                  onClick={() => onEdit(player)}
                  className="playertable-btn edit"
                >
                  수정
                </button>

                <button
                  onClick={() => onDelete(player.id)}
                  className="playertable-btn delete"
                >
                  삭제
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
