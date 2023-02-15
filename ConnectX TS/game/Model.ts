import { Imodel } from "../interface/IModel";
import { GameSettings } from "../util/GameSettings";

export class Model implements Imodel{
    CONCEDE_MOVE: -1
    GAME_STATUS_TIE: 3
    GAME_STATUS_WIN_1: 1
    GAME_STATUS_WIN_2: 2

    MIN_ROWS: 3
    MAX_ROWS: 10
    MIN_COLS: 3
    MAX_COLS: 10

    private setting: GameSettings

    initNewGame(): void {
        
    }

    isMoveValid(move: number): boolean {
        return false
    }
    
    makeMove(): void {
        
    }

    getGameStatus(): number {
        return 0
    }

    getActivePlayer(): number {
        return 0
    }

    getPieceIn(): number {
        return 0
    }
    
    constructor(){
        
    }
}