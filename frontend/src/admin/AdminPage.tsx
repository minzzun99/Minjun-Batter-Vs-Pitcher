import {useEffect, useState} from "react";
import {Link} from "react-router-dom";
import PlayerTable from "../admin/PlayerTable";
import PlayerForm from "../admin/PlayerForm";
import type {Player, PlayerData} from "../types/game";
import { adminApi } from "../services/adminApi";
import "../styles/Admin.css";

export default function AdminPlayersPage() {
    const [players, setPlayers] = useState<Player[]>([]);
    const [editingPlayer, setEditingPlayer] = useState<Player | null>(null);
    const [message, setMessage] = useState("");
    const [showForm, setShowForm] = useState(false);

    useEffect(() => {
        if (!message) return;
        const timer = setTimeout(() => setMessage(""), 2000);
        return () => clearTimeout(timer);
    }, [message]);

    const fetchPlayers = async () => {
        try {
            const data = await adminApi.getPlayers();
            setPlayers(data);
        } catch (error) {
            console.error(error);
            setMessage("선수 목록을 불러오지 못했습니다.");
        }
    };

    useEffect(() => {
        const loadData = async () => {
            await fetchPlayers();
        };
        void loadData();
    }, []);

    const handleCreate = async (player: PlayerData) => {
        try {
            await adminApi.createPlayer(player);
            setMessage("선수 추가 완료");
            await fetchPlayers();
            setShowForm(false);
            return true;
        } catch (error) {
            console.error(error);
            alert("선수 추가 실패");
            return false;
        }
    };

    const handleUpdate = async (id: number, player: PlayerData) => {
        try {
            console.log("수정 데이터:", player);
            await adminApi.updatePlayer(id, player);
            setMessage("선수 정보 수정 완료!");
            setEditingPlayer(null);
            setShowForm(false);
            await fetchPlayers();
            return true;
        } catch (error) {
            console.error(error);
            alert("기록 수정 실패");
            return false;
        }
    };

    const handleDelete = async (id: number) => {
        if (!confirm("정말 삭제하시겠습니까?")) return;

        try {
            await adminApi.deletePlayer(id);
            setMessage("선수 삭제 완료");
            await fetchPlayers();
        } catch (error) {
            console.error(error);
            alert("선수 삭제 실패");
        }
    };

    return (
        <div className="admin-container">

            <Link to="/" className="admin-back-btn">← 메인으로</Link>

            <h1 className="admin-title">관리자 - 선수 관리</h1>

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
                        key={editingPlayer?.id || 'new'}
                        onSubmit={
                            editingPlayer
                                ? (data) => handleUpdate(editingPlayer.id, data)
                                : handleCreate
                        }
                        onCancel={() => {
                            setEditingPlayer(null);
                            setShowForm(false);
                        }}
                        initialData={editingPlayer ?? undefined}
                    />
                </div>
            )}

            <PlayerTable
                players={players}
                onEdit={(player: Player) => {
                    setEditingPlayer(player);
                    setShowForm(true);
                }}
                onDelete={handleDelete}
            />
        </div>
    );
}
