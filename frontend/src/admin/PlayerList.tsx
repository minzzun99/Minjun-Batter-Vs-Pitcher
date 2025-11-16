export function PlayerList({ players, onEdit, onDelete }: any) {
  return (
    <div className="mt-6 p-4 bg-white rounded-xl shadow-md border">
      <h2 className="text-lg font-bold mb-2">현재 선수 목록</h2>

      <table className="w-full text-sm border-collapse">
        <thead>
          <tr className="bg-gray-100 border-b">
            <th className="p-2">이름</th>
            <th className="p-2">타수</th>
            <th className="p-2">타율</th>
            <th className="p-2">액션</th>
          </tr>
        </thead>
        <tbody>
          {players.map((p: any) => (
            <tr key={p.id} className="border-b">
              <td className="p-2">{p.name}</td>
              <td className="p-2">{p.totalAtBats}</td>
              <td className="p-2">{p.battingAverage.toFixed(3)}</td>
              <td className="p-2 flex gap-2">
                <button
                  className="px-3 py-1 bg-yellow-500 text-white rounded"
                  onClick={() => onEdit(p)}
                >
                  수정
                </button>
                <button
                  className="px-3 py-1 bg-red-600 text-white rounded"
                  onClick={() => onDelete(p.id)}
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
