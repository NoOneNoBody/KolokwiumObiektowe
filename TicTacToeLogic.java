public class TicTacToeLogic {

    private int playerTurn = 0;
    private int gameState = -1;
    private int[] fields = {-1,-1,-1,-1,-1,-1,-1,-1,-1};

    private void changeTurn(){
        playerTurn = playerTurn==0?1:0;
    }

    public String OnFieldClick(int fieldId, int playerId) {
        if(fields[fieldId] == -1 && playerId == playerTurn && gameState == -1){
            fields[fieldId] = playerId;
            changeTurn();
            if(playerId == 0){
                return "x";
            } else if (playerId == 1) {
                return "o";
            }
        }
        return null;
    }

    public int getGameState(){
        for(int j=0;j<3;++j){
            int current = fields[j*3];
            int i=1;
            for(; i < 3;++i){
                if(current != fields[j+i]) break;
            }
            if(i == 3 && current != -1){
                gameState = current;
                return current;
            }
        }
        for(int j=0;j<3;++j){
            int current = fields[j];
            int i=1;
            for(; i < 3;++i){
                if(current != fields[j+i*3]) break;
            }
            if(i == 3 && current != -1) {
                gameState = current;
                return current;
            }
        }
        for(int j=0;j<2;++j){
            int current = fields[j*2];
            int i=1;
            for(; i < 3;++i){
                if(current != fields[i*3+(j==0?i:-i)]) break;
            }
            if(i == 3 && current != -1) {
                gameState = current;
                return current;
            }
        }
        gameState = -1;
        return -1;
    }

    public int getTurnId(){
        return playerTurn;
    }

}
