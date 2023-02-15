import { Imodel } from "../interface/IModel";
import { Iplayer } from "../interface/IPlayer";
import { Iview } from "../interface/IView";

import { Model } from "./Model";
import { TextView } from "./TextView";
import { GameSettings } from "../util/GameSettings";

class Controller{
    private readonly model: Imodel
    private readonly view: Iview
    private settings: GameSettings

    private player1: Iplayer
    private player2: Iplayer

    constructor(){
        this.model = new Model
        this.view = new TextView

        this.settings = new GameSettings
         
    }

    public startSession(): void {
        this.view.displayWelcomeMessage();
    
        do {
          switch (this.view.requestMenuSelection()) {
            case '1':
              //this.startNewGame();
              break;
            case '2':
              //this.resumeSavedGame();
              break;
            case '3':
              //this.changeGameSettings();
              break;
            case '4':
              //this.changePlayers();
              break;
            default:
              return;
          }
        } while (true);
    }

    // Starts a new game with an empty board.
    private startNewGame(): void {
    this.model.initNewGame(this.settings);
    // this.startMatchLoop();
  }

}