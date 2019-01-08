public class TicTacToeLogic {

    private int playerTurn = 0;

    private void changeTurn(){
        playerTurn = playerTurn==0?1:0;
    }

    public String OnFieldClick(int fieldId, int playerId) {
        if(playerId == playerTurn){
            if(playerId == 0){
                changeTurn();
                return "x";
            } else if (playerId == 1) {
                changeTurn();
                return "o";
            }
        }
        return null;
    }

    public int getTurnId(){
        return playerTurn;
    }

}
