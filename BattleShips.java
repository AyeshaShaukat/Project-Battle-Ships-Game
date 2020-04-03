import java.util.ArrayList;
import java.util.Scanner;

class Main {
  public static void main(String[] args) {
    
    //creates multiple boards
    
    String[][][]userBoard = new String[3][10][10];
    String[][][]boardAI = new String[3][10][10];
    intFillBoard(userBoard,0);
    intFillBoard(boardAI,1);
    
    /*
    5 aircraft carrier  - a
    4 battleship        - b
    3 cruser            - c
    3 submarine         - s
    2 destroyer         - d
    */
    
    //creates history of moves made by user and the AI
    ArrayList<Move> moveHistAI= new ArrayList<Move>();
    ArrayList<String> usersShips= new ArrayList<String>();
    ArrayList<String> aiShips= new ArrayList<String>();
    shipLeftFill(usersShips);
    shipLeftFill(aiShips);
    Scanner enter = new Scanner(System.in);
    space(); 
    
    
    System.out.print("\n\n\t\u274CWelcome to battleship\u274C \n\t\tBy Michael F\n\n\n\n\n\tThis is your battleship board. Let's place your ships.\n\n");
    printBoard(userBoard,0);
    System.out.print("\n\nPRESS ENTER TO PLAY\n");
    String wait = enter.nextLine();
    
    //hide ships in hiddenAI board
    //hides user ships
    
    CompInputShips.autoFill(boardAI);
    UserInputShips.userFill(userBoard);
    int hit=-1;
    boolean gameWon=false;
    
    for(int r = 0;r<10;r++){
      for(int c = 0; c<10;c++){
        boardAI[2][r][c]=boardAI[0][r][c];
        userBoard[2][r][c]=userBoard[1][r][c];
      }
    }
    
    while(!gameWon){
      hit=UserGuess.UserInput(userBoard,boardAI,usersShips,aiShips,moveHistAI,hit);
      if(aiShips.size()==0){
        gameWon= true;
      }
      if(!gameWon){
        AI.aiMove(userBoard,boardAI,usersShips,aiShips,moveHistAI);
        if(usersShips.size()==0){
          gameWon= true;
        }
      }
    }
  }
  
  
  //Fills board with "-" for ships to be placed on 
  public static void intFillBoard(String[][][] grid, int e){
    for(int r = 0;r<10;r++){
      for(int c = 0; c<10;c++){
        grid [e][r][c] = "-";
      }
    }
  }
  
  //Prints coordinates on the side of the board
  public static void printBoard(String[][][] grid, int a){
    for(int r = 0;r<10;r++){
      System.out.print(10-r +"   ");
      if(r!=0){
        System.out.print(" ");
      }
      for(int c = 0; c<10;c++){
        System.out.print(grid[a][r][c] + " ");
      }
      System.out.print("\n");
    }
    System.out.print("\n     1 2 3 4 5 6 7 8 9 10");
  }
  
  //Method for spacing screen out 
  public static void space(){
    for (int i=1;i<=54;i++){
      System.out.print("\n");
    }
  }
  //fills ship list 
  public static void shipLeftFill(ArrayList<String> list){
    list.add("a");
    list.add("b");
    list.add("c");
    list.add("s");
    list.add("d");
  }
  
  //Returns coordinates of grid since indexes are +1
  public static int gTOaX(int graphX){
    return graphX-1;
  }
  public static int gTOaY(int graphY){
    return 10-graphY;
  }
}
