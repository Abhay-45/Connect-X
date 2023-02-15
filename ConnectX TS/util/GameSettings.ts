export class GameSettings{
    public nrRows: number
    public nrCols: number
    public minStreakLength: number

    // public GameSettings(){
    //     this.nrRows = 6;
    //     this.nrCols = 7;
    //     this.minStreakLength = 4;
    // }

    public GameSettings(rows:number, cols:number, streakLength:number){
        this.nrRows = rows
        this.nrCols = cols
        this.minStreakLength = streakLength
    }
}