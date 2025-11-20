import {useState, useEffect} from "react";
import type {PitchResponse, GameStatistics} from "../types/game";
import "../styles/GameBoard.css";

import {LeftPanel} from "./LeftPanel";
import {CenterPanel} from "./CenterPanel";
import {RightPanel} from "./RightPanel";
import {ZoneSelector} from "./ZoneSelector";

interface GameBoardProps {
    gameId: string;
    gameMode: string;
    onPitch: (zoneNumber: number) => Promise<void>;
    pitchResult: PitchResponse | null;
    onGoHome: () => void;
    onRetry: () => void;

    playerInfo: {
        avg: string;
        slg: string;
        ops: string;
    } | null;
    playerName?: string;
}

export const GameBoard = ({
                              gameId,
                              gameMode,
                              onPitch,
                              pitchResult,
                              onGoHome,
                              onRetry,
                              playerInfo,
                              playerName
                          }: GameBoardProps) => {
    const [selectedZone, setSelectedZone] = useState<number | null>(null);
    const [isLoading, setIsLoading] = useState(false);
    const [showZoneInfo, setShowZoneInfo] = useState(false);
    const [gameStats, setGameStats] = useState<GameStatistics | null>(null);

    const isGameOver = pitchResult?.isGameOver ?? false;

    // 새 게임 시작 → 통계 초기화
    useEffect(() => {
        if (pitchResult && !pitchResult.isGameOver) {
            setGameStats(null);
        }
    }, [pitchResult]);

    // 존 애니메이션 제어
    useEffect(() => {
        if (pitchResult?.zoneInfoDto && isLoading) {
            setShowZoneInfo(true);

            const timer = setTimeout(() => {
                setShowZoneInfo(false);
                setSelectedZone(null);
            }, 2500);

            return () => clearTimeout(timer);
        }
    }, [pitchResult, isLoading]);

    // 게임 종료 → 통계 불러오기
    useEffect(() => {
        if (pitchResult?.isGameOver && !gameStats) {
            fetchGameResult();
        }
    }, [pitchResult?.isGameOver]);

    const fetchGameResult = async () => {
        try {
            const response = await fetch(`/api/game/${gameId}/result`);
            const data = await response.json();
            setGameStats(data.gameStatistics);
        } catch (error) {
            console.error("Failed to fetch game result:", error);
        }
    };

    const handleQuitGame = async () => {
        try {
            await fetch(`/api/game/${gameId}`, {
                method: "DELETE",
            });
            onGoHome();
        } catch (error) {
            console.error("Failed to quit game:", error);
            // 실패해도 화면은 이동
            onGoHome();
        }
    };

    // 존 클릭 처리
    const handleZoneClick = async (zone: number) => {
        setSelectedZone(zone);
        setIsLoading(true);
        setShowZoneInfo(false);

        try {
            await onPitch(zone);
        } finally {
            setTimeout(() => setIsLoading(false), 2600);
        }
    };

    const getResultMessage = () => {
        if (!pitchResult) return "";
        const my = pitchResult.scoreBoard.myScore;
        const com = pitchResult.scoreBoard.computerScore;

        if (my > com) return "승리!";
        if (my < com) return "패배..";
        return "무승부";
    };

    const handleRetryClick = () => {
        setGameStats(null);
        onRetry();
    };

    return (
        <div className="gb-layout">

            <div className="gb-panels-row">
                <LeftPanel
                    count={pitchResult?.count}
                    hitterInfo={playerInfo ?? {avg: "0.000", slg: "0.000", ops: "0.000"}}
                    onQuit={handleQuitGame}
                    isGameOver={isGameOver}
                />

                <div className="gb-center-panel">
                    <CenterPanel
                        gameMode={gameMode}
                        gameId={gameId}
                        scoreBoard={pitchResult?.scoreBoard}
                        pitchResult={pitchResult}
                        playerName={playerName}
                    />

                    <div className="gb-zone-wrapper">
                        <ZoneSelector
                            onZoneClick={handleZoneClick}
                            selectedZone={selectedZone}
                            disabled={isLoading || isGameOver}
                            zoneInfo={showZoneInfo ? pitchResult?.zoneInfoDto : null}
                            gameMode={gameMode}
                        />
                    </div>
                </div>

                <RightPanel runners={pitchResult?.runners}/>
            </div>

            {/* 게임 종료 모달 */}
            {isGameOver && (
                <div className="gb-modal-overlay">
                    <div className="gb-modal">
                        <h1>게임 결과</h1>

                        <div className="gb-score">
                            {pitchResult?.scoreBoard.myScore} : {pitchResult?.scoreBoard.computerScore}
                        </div>

                        <div className="gb-result-text">{getResultMessage()}</div>

                        {/* 경기 통계 */}
                        {gameStats ? (
                            <div className="game-stats-section">
                                <h2 className="game-stats-title">경기 기록</h2>

                                {/* 안타 */}
                                <div className="game-stats-category">
                                    <h3>안타 통계</h3>
                                    <div className="game-stats-grid">
                                        <Stat label="1루타" value={gameStats.singles}/>
                                        <Stat label="2루타" value={gameStats.doubles}/>
                                        <Stat label="3루타" value={gameStats.triples}/>
                                        <Stat label="홈런" value={gameStats.homeRuns}/>
                                    </div>
                                </div>

                                {/* 아웃 */}
                                <div className="game-stats-category">
                                    <h3>아웃 통계</h3>
                                    <div className="outs-grid">
                                        <Stat label="삼진" value={gameStats.strikeOuts}/>
                                        <Stat label="땅볼" value={gameStats.groundOuts}/>
                                        <Stat label="플라이" value={gameStats.flyOuts}/>
                                    </div>
                                </div>

                                {/* 요약 */}
                                <div className="game-stats-summary">
                                    <Highlight label="타율" value={gameStats.battingAverage.toFixed(3)}/>
                                    <Highlight label="총 안타" value={gameStats.totalHits}/>
                                    <Highlight label="총 타수" value={gameStats.totalAtBats}/>
                                </div>
                            </div>
                        ) : (
                            <div className="loading-stats">통계 불러오는 중...</div>
                        )}

                        {/* 버튼 */}
                        <div className="game-over-buttons">
                            <button className="game-over-btn retry" onClick={handleRetryClick}>
                                다시 하기
                            </button>
                            <button className="game-over-btn home" onClick={onGoHome}>
                                새 게임
                            </button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};

// 작은 컴포넌트
const Stat = ({label, value}) => (
    <div className="stat-box">
        <span className="stat-label">{label}</span>
        <span className="stat-value">{value}</span>
    </div>
);

const Highlight = ({label, value}) => (
    <div className="stat-highlight">
        <span className="stat-label">{label}</span>
        <span className="stat-value-big">{value}</span>
    </div>
);
