export interface Iview{
    displayWelcomeMessage(): void;
    displayChosenMove(): void;
    displayMoveRejectedMessage(): void;
    displayActivePlayer(): void;
    displayGameStatus(): void;
    displayBoard(): void;

    requestMenuSelection(): string;
   

}
