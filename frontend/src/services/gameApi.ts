import { api } from './api';
import type{ 
  GameMode, 
  PitchResponse, 
  GameStatusResponse, 
  GameResultResponse 
} from '../types/game';

export const gameApi = {
  // 게임 시작
  startGame: async (mode: GameMode, playerId: number) => {
  const res = await fetch("/api/game/start", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      gameMode: mode,
      playerId: playerId,
    }),
  });

  if (!res.ok) throw new Error("게임 시작 실패!");
  return res.json();
},

  // 투구
  pitch: async (gameId: string, zoneNumber: number): Promise<PitchResponse> => {
    const response = await api.post(`/game/${gameId}/pitch`, { zoneNumber });
    return response.data;
  },

  // 상태 조회
  getStatus: async (gameId: string): Promise<GameStatusResponse> => {
    const response = await api.get(`/game/${gameId}/status`);
    return response.data;
  },

  // 결과 조회
  getResult: async (gameId: string): Promise<GameResultResponse> => {
    const response = await api.get(`/game/${gameId}/result`);
    return response.data;
  }
};