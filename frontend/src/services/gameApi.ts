import {api} from './api';
import type {
    GameMode,
    PitchResponse,
    GameStatusResponse,
    GameResultResponse,
    Player
} from '../types/game';

export const gameApi = {
    // 게임용 선수 목록 조회
    getPlayers: async (): Promise<Player[]> => {
        const response = await api.get('/game/players');
        return response.data;
    },

    // 게임 시작
    startGame: async (mode: GameMode, playerId: number) => {
        const response = await api.post('/game/start', {
            gameMode: mode,
            playerId: playerId,
        });
        return response.data;
    },

    // 투구
    pitch: async (gameId: string, zoneNumber: number): Promise<PitchResponse> => {
        const response = await api.post(`/game/${gameId}/pitch`, {zoneNumber});
        return response.data;
    },

    // 상태 조회 (TODO: 추후 전적 기능에서 사용 예정)
    getStatus: async (gameId: string): Promise<GameStatusResponse> => {
        const response = await api.get(`/game/${gameId}/status`);
        return response.data;
    },

    // 결과 조회 (TODO: 추후 통계 기능에서 사용 예정)
    getResult: async (gameId: string): Promise<GameResultResponse> => {
        const response = await api.get(`/game/${gameId}/result`);
        return response.data;
    }
};
