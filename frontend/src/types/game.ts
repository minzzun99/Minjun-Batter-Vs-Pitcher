// 게임 모드
export enum GameMode {
  PITCHER = "PITCHER",
  BATTER = "BATTER",
}

// 공통 타입들
export interface PitchResult {
  type: string;
  detail: string | null;
}

export interface Count {
  strike: number;
  out: number;
}

export interface BaseRunner {
  first: boolean;
  second: boolean;
  third: boolean;
}

// 점수판
export interface ScoreBoard {
  score: number;
  myScore: number;
  computerScore: number;
}

// 게임 시작 요청 / 응답
export interface GameStartRequest {
  gameMode: GameMode;
  playerId: number;
}

export interface GameStartResponse {
  gameId: string;
  gameMode: GameMode;
  scoreBoard: ScoreBoard;
}

// 존 정보
export interface ZoneInfoDto {
  selectedZone: number;
  pitchZone: number;
  hotZone: number;
  coldZone: number;
}

// 투구 요청 / 응답
export interface PitchRequest {
  zoneNumber: number;
}

export interface PitchResponse {
  pitchResult: PitchResult;
  count: Count;
  runners: BaseRunner;
  scoreBoard: ScoreBoard;
  isGameOver: boolean;
  zoneInfoDto?: ZoneInfoDto;
  gameStatistics?: GameStatistics;
}

// 게임 중 상태 요청
export interface GameStatusResponse {
  gameMode: GameMode;
  count: Count;
  baseRunners: BaseRunner;
  scoreBoard: ScoreBoard;
  gameOver: boolean;
}


// 게임 결과
export interface GameResultResponse {
  result: string;
  scoreBoard: ScoreBoard;
}

export interface GameStatistics {
  singles: number;
  doubles: number;
  triples: number;
  homeRuns: number;
  strikeOuts: number;
  groundOuts: number;
  flyOuts: number;
  totalHits: number;
  totalOuts: number;
  totalAtBats: number;
  battingAverage: number;
}