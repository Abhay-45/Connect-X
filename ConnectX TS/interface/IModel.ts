import { GameSettings } from "../util/GameSettings";

export interface Imodel{
    CONCEDE_MOVE: number;
    GAME_STATUS_WIN_1: number;
    GAME_STATUS_WIN_2: number;
    GAME_STATUS_TIE: number;

    MIN_ROWS: number;
    MAX_ROWS: number;
    MIN_COLS: number;
    MAX_COLS: number;

    initNewGame(settings: GameSettings): void;

    isMoveValid(move: number): boolean;

    makeMove(move: number): void;

    getGameStatus(): number;

    getActivePlayer(): number;

    getPieceIn(row: number, col: number): number;



}