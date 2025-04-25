import java.util.*;

public class TicTacToe {
    // Constants
    private static final char EMPTY = ' ';          // Empty cell representation
    private static final int SIZE = 3;              // Size of the board (3x3)
    private static final char[][] board = new char[SIZE][SIZE]; // The game board

    // Player and game state variables
    private static char userSymbol, computerSymbol; // Symbols for user and computer
    private static boolean userTurn;                // True if it's the user's turn

    // Utility classes
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();

    public static void main(String[] args) {
        // Display game intro
        System.out.println("=========================================");
        System.out.println("🎮 Welcome to 3x3 Tic Tac Toe game - Java");
        System.out.println("=========================================");
        System.out.println("- You will play against the computer.");
        System.out.println("- When it's your turn, enter row and column numbers (0 to 2).");
        System.out.println("  For example, typing '1 2' marks the middle-right cell.");
        System.out.println("Let’s begin!\n");

        initBoard();     // Initialize board with empty spaces
        chooseSymbol();  // Let user pick 'X' or 'O'
        chooseStarter(); // Let user decide who starts

        printBoard();    // Show the empty board

        // Game loop
        while (true) {
            if (userTurn) {
                userMove(); // Get and apply user move
                if (checkWinner(userSymbol)) {
                    printBoard();
                    System.out.println("🎉 You win!");
                    break;
                }
            } else {
                computerMove(); // Let computer make a move
                if (checkWinner(computerSymbol)) {
                    printBoard();
                    System.out.println("😞 Computer wins!");
                    break;
                }
            }

            printBoard(); // Display updated board

            if (isBoardFull()) {
                System.out.println("It's a tie!");
                break;
            }

            userTurn = !userTurn; // Switch turns
        }

        scanner.close(); // Close scanner resource
    }

    // Fills the board with empty spaces
    private static void initBoard() {
        for (int i = 0; i < SIZE; i++)
            Arrays.fill(board[i], EMPTY);
    }

    // Lets the user choose between 'X' or 'O'
    private static void chooseSymbol() {
        while (true) {
            System.out.print("Choose your symbol (X or O): ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.equals("X") || input.equals("O")) {
                userSymbol = input.charAt(0);
                computerSymbol = (userSymbol == 'X') ? 'O' : 'X';
                break;
            }
            System.out.println("Invalid choice. Please enter X or O.");
        }
    }

    // Lets the user decide who starts first
    private static void chooseStarter() {
        while (true) {
            System.out.print("Who starts? (user/computer): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("user")) {
                userTurn = true;
                break;
            } else if (input.equals("computer")) {
                userTurn = false;
                break;
            }
            System.out.println("Invalid input. Please type 'user' or 'computer'.");
        }
    }

    // Prints the current game board
    private static void printBoard() {
        System.out.println("-------------");
        for (int i = 0; i < SIZE; i++) {
            System.out.print("| ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println("\n-------------");
        }
    }

    // Handles user input and updates the board
    private static void userMove() {
        int row, col;
        while (true) {
            System.out.print("Enter your move (row and column: 0 1 2): ");
            
            // Get row input
            if (scanner.hasNextInt()) {
                row = scanner.nextInt();
            } else {
                System.out.println("Invalid input. Please enter numbers.");
                scanner.next(); // Clear invalid input
                continue;
            }

            // Get column input
            if (scanner.hasNextInt()) {
                col = scanner.nextInt();
            } else {
                System.out.println("Invalid input. Please enter numbers.");
                scanner.next(); // Clear invalid input
                continue;
            }

            // Check if the move is valid
            if (isValidMove(row, col)) {
                board[row][col] = userSymbol;
                break;
            } else {
                System.out.println("Invalid move. Try again.");
            }
        }
    }

    // Computer randomly selects a valid empty cell
    private static void computerMove() {
        System.out.println("Computer's turn...");
        int row, col;
        do {
            row = random.nextInt(SIZE);
            col = random.nextInt(SIZE);
        } while (!isValidMove(row, col));

        board[row][col] = computerSymbol;
    }

    // Checks whether a move is valid (in bounds and cell is empty)
    private static boolean isValidMove(int row, int col) {
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE && board[row][col] == EMPTY;
    }

    // Checks if the board is completely filled
    private static boolean isBoardFull() {
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (board[i][j] == EMPTY)
                    return false;
        return true;
    }

    // Checks if a player has won
    private static boolean checkWinner(char player) {
        // Check all rows and columns
        for (int i = 0; i < SIZE; i++)
            if ((board[i][0] == player && board[i][1] == player && board[i][2] == player) || // Row
                (board[0][i] == player && board[1][i] == player && board[2][i] == player))   // Column
                return true;

        // Check diagonals
        return (board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
               (board[0][2] == player && board[1][1] == player && board[2][0] == player);
    }
}
