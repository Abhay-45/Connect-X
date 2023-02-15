import { Imodel } from "./IModel";

export interface Iplayer{
    prepareForGameStart(model: Imodel, playerId: number): void;
    chooseMove(): number;
}