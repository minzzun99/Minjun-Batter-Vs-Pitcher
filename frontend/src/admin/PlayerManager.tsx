import { useEffect, useState } from 'react';
import PlayerForm from './PlayerForm';
import { PlayerList } from './PlayerList';

export default function PlayerManager() {
  const [players, setPlayers] = useState([]);
  const [editing, setEditing] = useState(null);
  const [message, setMessage] = useState("");

  const fetchPlayers = async () => {
    const res = await fetch("/api/admin/players");
    setPlayers(await res.json());
  };

  useEffect(() => {
    fetchPlayers();
  }, []);

  const handleSubmit = async (data: any) => {
    try {
      if (editing) {
        await fetch(`/api/admin/players/${editing.id}`, {
          method: "PUT",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(data),
        });
        setMessage("선수 정보 수정 완료!");
      } else {
        await fetch("/api/admin/players", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(data),
        });
        setMessage("선수 추가 완료!");
      }

      setEditing(null);
      fetchPlayers();

      setTimeout(() => setMessage(""), 2000);
      return true;

    } catch (err) {
      alert("저장 실패!");
      return false;
    }
  };

  const handleDelete = async (id: number) => {
    if (!confirm("정말 삭제할까요?")) return;

    await fetch(`/api/admin/players/${id}`, { method: "DELETE" });
    fetchPlayers();
  };

  return (
    <div className="max-w-3xl mx-auto mt-6 space-y-6">
      {message && (
        <div className="p-3 bg-green-100 text-green-800 text-center rounded">
          {message}
        </div>
      )}

      <PlayerForm
        onSubmit={handleSubmit}
        onCancel={() => setEditing(null)}
        initialData={editing}
      />

      <PlayerList
        players={players}
        onEdit={setEditing}
        onDelete={handleDelete}
      />
    </div>
  );
}
