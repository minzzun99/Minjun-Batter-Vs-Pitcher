import { useEffect, useState } from "react";
import PlayerTable from "../admin/PlayerTable";
import PlayerForm from "../admin/PlayerForm";
import "../styles/admin.css";

export default function AdminPlayersPage() {
  const [players, setPlayers] = useState([]);
  const [editingPlayer, setEditingPlayer] = useState(null);
  const [message, setMessage] = useState("");
  const [showForm, setShowForm] = useState(false);

  useEffect(() => {
    if (!message) return;
    const timer = setTimeout(() => setMessage(""), 2000);
    return () => clearTimeout(timer);
  }, [message]);

  const fetchPlayers = async () => {
    try {
      const res = await fetch("/api/admin/players");
      if (!res.ok) throw new Error("Failed to fetch players");
      const data = await res.json();
      setPlayers(data);
    } catch (err) {
      setMessage("선수 목록을 불러오지 못했습니다.");
    }
  };

  useEffect(() => {
    fetchPlayers();
  }, []);

  const handleCreate = async (player) => {
    try {
      const res = await fetch("/api/admin/players", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(player),
      });
      if (!res.ok) throw new Error("Create failed");

      setMessage("선수 추가 완료!");
      fetchPlayers();
      setShowForm(false); // 🔥 폼 닫기
      return true;
    } catch {
      alert("선수 추가 실패");
      return false;
    }
  };

  const handleUpdate = async (id, player) => {
    try {
      const res = await fetch(`/api/admin/players/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(player),
      });

      if (!res.ok) throw new Error("Update failed");

      setMessage("선수 정보 수정 완료!");
      setEditingPlayer(null);
      setShowForm(false);
      fetchPlayers();
      return true;
    } catch {
      alert("수정 실패");
      return false;
    }
  };

  const handleDelete = async (id) => {
    if (!confirm("정말 삭제하시겠습니까?")) return;

    try {
      const res = await fetch(`/api/admin/players/${id}`, { method: "DELETE" });
      if (!res.ok) throw new Error("Delete failed");

      setMessage("🗑️ 선수 삭제 완료!");
      fetchPlayers();
    } catch {
      alert("삭제 실패");
    }
  };

  return (
    <div className="admin-container">
      <h1 className="admin-title">⚾ 관리자 - 선수 관리</h1>

      {message && <div className="admin-message">{message}</div>}

      <button
        className="admin-btn-primary"
        onClick={() => {
          setShowForm((prev) => !prev);
          setEditingPlayer(null);
        }}
      >
        {showForm ? "닫기" : "선수 추가"}
      </button>

      {showForm && (
        <div className="admin-form-wrapper">
          <PlayerForm
            onSubmit={
              editingPlayer
                ? (data) => handleUpdate(editingPlayer.id, data)
                : handleCreate
            }
            onCancel={() => {
              setEditingPlayer(null);
              setShowForm(false);
            }}
            initialData={editingPlayer}
          />
        </div>
      )}

      {/* 선수 목록 */}
      <PlayerTable
        players={players}
        onEdit={(p) => {
          setEditingPlayer(p);
          setShowForm(true);
        }}
        onDelete={handleDelete}
      />
    </div>
  );
}
