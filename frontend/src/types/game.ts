export enum GameMode {
  PITCHER = 'PITCHER',
  BATTER = 'BATTER'
}

export interface GameStartRequest {
  gameMode: GameMode;
}

export interface GameStartResponse {
  gameId: string;
  gameMode: GameMode;
  scoreBoard: ScoreBoard;
}

export interface PitchRequest {
  zoneNumber: number;
}

export interface PitchResponse {
  pitchResult: PitchResult;
  count: Count;
  runners: BaseRunner;
  scoreBoard: ScoreBoard;
  gameOver: boolean;
}

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

export interface ScoreBoard {
  myScore: number;
  computerScore: number;
}

export interface GameStatusResponse {
  gameMode: GameMode;
  count: Count;
  baseRunners: BaseRunner;
  scoreBoard: ScoreBoard;
  gameOver: boolean;
}

export interface GameResultResponse {
  result: string;
  scoreBoard: ScoreBoard;
}