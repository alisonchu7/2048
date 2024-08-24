package game;

import java.util.ArrayList;

public class Board {
    private int[][] gameBoard;               // the game board array
    private ArrayList<BoardSpot> openSpaces; // the ArrayList of open spots: board cells without numbers.

    /**
     * Zero-argument Constructor: initializes a 4x4 game board.
     **/
    public Board() {
        gameBoard = new int[4][4];
        openSpaces = new ArrayList<>();
    }

    public Board ( int[][] board ) {
        gameBoard = new int[board.length][board[0].length];
        for ( int r = 0; r < gameBoard.length; r++ ) {
            for ( int c = 0; c < gameBoard[r].length; c++ ) {
                gameBoard[r][c] = board[r][c];
            }
        }
        openSpaces = new ArrayList<>();
    }
    public void updateOpenSpaces() {
        for ( int i = 0; i < gameBoard.length; i++ ) {
            for ( int j = 0; j < gameBoard[i].length; j++ ) {
                if (gameBoard[i][j]==0) {
                    openSpaces.add(new BoardSpot(i,j));
                } 
            }
        }
    }

    public void addRandomTile() {
        int randomBoardSpot = StdRandom.uniform(0,openSpaces.size());
        BoardSpot random = openSpaces.get(randomBoardSpot);
        int randomRow = random.getRow();
        int randomColumn = random.getCol();
        int tile;
        double chance = StdRandom.uniform(0.0,1.0);
        if (chance<0.1){
            tile = 4;
        } else {
            tile = 2;
        }

        gameBoard[randomRow][randomColumn] = tile;
    }

    public void swipeLeft() {
        int emptyRow;
        int emptyColumn;
        for ( int i = 0; i < gameBoard.length; i++ ) {
            for ( int j = 0; j < gameBoard[i].length; j++ ) {
                if (gameBoard[i][j]==0){
                    emptyColumn=j;
                    emptyRow=i;

                    for (int k = j; k<gameBoard[i].length;k++){
                        if (gameBoard[i][k]!=0) {
                            gameBoard[emptyRow][emptyColumn]=gameBoard[i][k];
                            gameBoard[i][k]=0;
                            break;
                        }

                    }
                }
            }
        }
    }

    public void mergeLeft() {
        for ( int i = 0; i < gameBoard.length; i++ ) {
            for ( int j = 0; j < gameBoard[i].length-1; j++ ) {
                if(gameBoard[i][j]==gameBoard[i][j+1]) {
                    gameBoard[i][j]=2*gameBoard[i][j];
                    gameBoard[i][j+1]=0;
                }
            }
        }
    }

    public void rotateBoard() {
        transpose();
        flipRows();
    }

    public void transpose() {
        int[][] tempArray = new int[4][4];
        for ( int i = 0; i < gameBoard.length; i++ ) {
            for ( int j = 0; j < gameBoard[i].length; j++ ) {
                tempArray[j][i]=gameBoard[i][j];
            }
        }

        for ( int i = 0; i < 4; i++ ) {
            for ( int j = 0; j < 4; j++ ) {
                gameBoard[i][j]=tempArray[i][j];
            }
        }


    }


    public void flipRows() {
        for(int i = 0; i<gameBoard.length; i++) {
            for (int j = 0; j<2;j++){
                int temp = gameBoard[i][j];
                gameBoard[i][j]=gameBoard[i][4-j-1];
                gameBoard[i][gameBoard[i].length-j-1]=temp;
            }
        }
    }

    public void makeMove(char letter) {
        if (letter=='U'){
            rotateBoard();
            rotateBoard();
            rotateBoard();
            swipeLeft();
            mergeLeft();
            swipeLeft();
            rotateBoard();
        }
        if (letter=='L'){
            swipeLeft();
            mergeLeft();
            swipeLeft();
        }
        if (letter=='R'){
            flipRows();
            swipeLeft();
            mergeLeft();
            swipeLeft();
            flipRows();
        }
        if (letter=='D'){
            rotateBoard();
            swipeLeft();
            mergeLeft();
            swipeLeft();
            rotateBoard();
            rotateBoard();
            rotateBoard();
        }
    }

    public boolean isGameLost() {
        return openSpaces.size() == 0;
    }

    public int showScore() {
        int score = 0;
        for ( int r = 0; r < gameBoard.length; r++ ) {
            for ( int c = 0; c < gameBoard[r].length; c++ ) {
                score += gameBoard[r][c];
            }
        }
        return score;
    }

    public void print() {
        for ( int r = 0; r < gameBoard.length; r++ ) {
            for ( int c = 0; c < gameBoard[r].length; c++ ) {
                String g = Integer.toString(gameBoard[r][c]);
                StdOut.print((g.equals("0")) ? "-" : g);
                for ( int o = 0; o < (5 - g.length()); o++ ) {
                    StdOut.print(" ");
                }
            }
            StdOut.println();
        }
    }

    public void printOpenSpaces() {
        for ( int r = 0; r < gameBoard.length; r++ ) {
            for ( int c = 0; c < gameBoard[r].length; c++ ) {
                String g = Integer.toString(gameBoard[r][c]);
                for ( BoardSpot bs : getOpenSpaces() ) {
                    if (r == bs.getRow() && c == bs.getCol()) {
                        g = "**";
                    }
                }
                StdOut.print((g.equals("0")) ? "-" : g);
                for ( int o = 0; o < (5 - g.length()); o++ ) {
                    StdOut.print(" ");
                }
            }
            StdOut.println();
        }
    }

    public Board(long seed) {
        StdRandom.setSeed(seed);
        gameBoard = new int[4][4];
    }

    public ArrayList<BoardSpot> getOpenSpaces() {
        return openSpaces;
    }

    public int[][] getBoard() {
        return gameBoard;
    }
}
