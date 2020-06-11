import java.util.*;

public class BattleShips {
    public static int numRows = 10;
    public static int numCols = 10;
    public static int playerShips;
    public static int computerShips;
    public static String[][] grid = new String[numRows][numCols];
    public static int[][] missedGuesses = new int[numRows][numCols];
    public static int cheat;

    public static void main(String[] args){
        System.out.println("**** Welcome to Battle Ships game ****");
        System.out.println("Right now, sea is empty\n");

        //Step 1 – Create the ocean map
        createOceanMap();

        //Step 2 – Deploy player’s ships
        deployPlayerShips();

        //Step 3 - Deploy computer's ships
        deployComputerShips();

        //Step 4 Battle
        do {
            Battle();
        }while(BattleShips.playerShips != 0 && BattleShips.computerShips != 0);

        //Step 5 - Game over
        gameOver();
    }

    public static void createOceanMap(){
        //First section of Ocean Map
        System.out.print("\t");
        for(int i = 0; i < numCols; i++)
            System.out.print(i + "\t");
        System.out.println();
        System.out.print("\t");
        for(int i = 0; i < numCols; i++)
            System.out.print("_" + "\t");
        System.out.println();

        //Middle section of Ocean Map
        for(int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = "\t";
                if (j == 0)
                    System.out.print(i + "|" + grid[i][j]);
                else if (j == grid[i].length - 1)
                    System.out.print(grid[i][j]+"\t" + "|" + i);
                else
                    System.out.print(grid[i][j]);
            }
            System.out.println();
        }

        //Last section of Ocean Map
        System.out.print("\t");
        for(int i = 0; i < numCols; i++)
            System.out.print("_" + "\t");
        System.out.println();
        System.out.print("\t");
        for(int i = 0; i < numCols; i++)
            System.out.print(i + "\t");
        System.out.println();
    }

    public static void deployPlayerShips(){
        Scanner input = new Scanner(System.in);

        System.out.println("\nDeploy your ships:");
        //Deploying five ships for player
        BattleShips.playerShips = 5;
        for (int i = 1; i <= BattleShips.playerShips; ) {
            System.out.print("Enter X coordinate for your " + i + " ship: ");
            int y = input.nextInt();
            System.out.print("Enter Y coordinate for your " + i + " ship: ");
            int x = input.nextInt();

            if((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && (grid[x][y] == "\t"))
            {
                grid[x][y] =   "\t@";
                i++;
            }
            else if((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && grid[x][y] == "@\t")
                System.out.println("You can't place two or more ships on the same location");
            else if((x < 0 || x >= numRows) || (y < 0 || y >= numCols))
                System.out.println("You can't place ships outside the " + numRows + " by " + numCols + " grid");
        }
        printOceanMap();
    }

    public static void deployComputerShips(){
        System.out.println("\nComputer is deploying ships");
        //Deploying five ships for computer
        BattleShips.computerShips = 5;
        for (int i = 1; i <= BattleShips.computerShips; ) {
            int x = (int)(Math.random() * 10);
            int y = (int)(Math.random() * 10);

            if((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && (grid[x][y] == "\t"))
            {
                grid[x][y] =   "\tx";
                System.out.println(i + ". ship DEPLOYED");
                i++;
            }
        }
        printOceanMap();
    }

    public static void Battle(){
        playerTurn();
        computerTurn();

        printOceanMap();

        System.out.println();
        System.out.println("Your ships: " + BattleShips.playerShips + " | Computer ships: " + BattleShips.computerShips);
        System.out.println();
    }

    public static void playerTurn(){
        System.out.println("\nYOUR TURN");
        int x = -1, y = -1;
        do {
            Scanner input = new Scanner(System.in);
            System.out.print("Enter X coordinate: ");
            y = input.nextInt();
            System.out.print("Enter Y coordinate: ");
            x = input.nextInt();

            if ((x >= 0 && x < numRows) && (y >= 0 && y < numCols)) //valid guess
            {
                if (grid[x][y] == "\tx") //if computer ship is already there; computer loses ship
                {
                    System.out.println("Boom! You sunk the ship!");
                    grid[x][y] = "\t!"; //Hit mark
                    --BattleShips.computerShips;
                }
                else if (grid[x][y] == "\t@") {
                    System.out.println("Oh no, you sunk your own ship :(");
                    grid[x][y] = "\tx";
                    --BattleShips.playerShips;
                    ++BattleShips.computerShips;
                }
                else if (grid[x][y] == "\t") {
                    System.out.println("Sorry, you missed");
                    grid[x][y] = "\t-";
                }
            }
            else if ((x < 0 || x >= numRows) || (y < 0 || y >= numCols))  //invalid guess
                System.out.println("You can't place ships outside the " + numRows + " by " + numCols + " grid");
            if(y<0) {
                cheat = y;
                System.out.println("Spies have been sent out to scout enemie ships!");
            }
        }while((x < 0 || x >= numRows) || (y < 0 || y >= numCols));  //keep re-prompting till valid guess
    }

    public static void computerTurn(){
        System.out.println("\nCOMPUTER'S TURN");
        //Guess co-ordinates

        int x = -1, y = -1;
        do {
            x = (int) (Math.random() * 10);
            y = (int) (Math.random() * 10);
        } while(missedGuesses[x][y]==1);
        if ((x >= 0 && x < numRows) && (y >= 0 && y < numCols)) //valid guess
        {
            if (grid[x][y] == "\t@") //if player ship is already there; player loses ship
            {
                System.out.println("The Computer sunk one of your ships!");
                grid[x][y] = "\tx";
                --BattleShips.playerShips;
                ++BattleShips.computerShips;
            }
            else if (grid[x][y] == "\tx") {
                System.out.println("The Computer sunk one of its own ships");
                grid[x][y] = "\t!";
            }
            else if (grid[x][y] == "\t") {
                System.out.println("Computer missed");
                //Saving missed guesses for computer
                missedGuesses[x][y] = 1;
                }
            }
    }

    public static void gameOver(){
        System.out.println("Your ships: " + BattleShips.playerShips + " | Computer ships: " + BattleShips.computerShips);
        if(BattleShips.playerShips > 0 && BattleShips.computerShips <= 0)
            System.out.println("Hooray! You won the battle :)");
        else
            System.out.println("Sorry, you lost the battle");
        System.out.println();
    }

    public static void printOceanMap(){
        System.out.println();
        //First section of Ocean Map
        System.out.print("\t");
        for(int i = 0; i < numCols; i++)
            System.out.print(i + "\t");
        System.out.println();
        System.out.print("\t");
        for(int i = 0; i < numCols; i++)
            System.out.print("_" + "\t");
        System.out.println();

        //Middle section of Ocean Map
        for(int x = 0; x < grid.length; x++) {
            System.out.print(x + "|");

            for (int y = 0; y < grid[x].length; y++){
                if(grid[x][y]!= "\tx")
                    System.out.print(grid[x][y]);
                else if(grid[x][y]== "\tx" && cheat<0) {
                    System.out.print(grid[x][y]);
                    cheat++;
                }
                else
                    System.out.print("\t");
            }

            System.out.println("\t|" + x);
        }

        //Last section of Ocean Map
        System.out.print("\t");
        for(int i = 0; i < numCols; i++)
            System.out.print("_" + "\t");
        System.out.println();
        System.out.print("\t");
        for(int i = 0; i < numCols; i++)
            System.out.print(i + "\t");
        System.out.println();
    }
}