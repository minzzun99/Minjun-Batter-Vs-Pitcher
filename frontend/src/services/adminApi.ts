import { api } from './api';
import type { Player, PlayerData } from '../types/game';

export const adminApi = {
  // 선수 목록 조회
  getPlayers: async (): Promise<Player[]> => {
    const response = await api.get('/admin/players');
    return response.data;
  },

  // 선수 추가
  createPlayer: async (player: PlayerData): Promise<Player> => {
    const response = await api.post('/admin/players', player);
    return response.data;
  },

  // 선수 수정
  updatePlayer: async (id: number, player: PlayerData): Promise<Player> => {
    const response = await api.put(`/admin/players/${id}`, player);
    return response.data;
  },

  // 선수 삭제
  deletePlayer: async (id: number): Promise<void> => {
    await api.delete(`/admin/players/${id}`);
  }
};
