import java.util.*;

public class ActivityBingoGame {
    public static void main(String[] args) {
        displayBanner();
        System.out.println("\nWelcome to Bingo Game!");
        System.out.println("Get ready to play and win!");

        // Display menu
        displayMenu();
    }

    private static void displayBanner() {
        System.out.println(" \n");
        System.out.println("********************************************");
        System.out.println("*                                          *");
        System.out.println("*            BINGO GAME                    *");
        System.out.println("*                                          *");
        System.out.println("********************************************");
    }

    private static void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n1. PLAY BINGO!");
            System.out.println("2. EXIT");
            System.out.print("Enter your input: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number. Enter [1] to PLAY BINGO or [2] to EXIT.");
                System.out.print("Enter your input: ");
                scanner.next(); // Clear invalid input
            }
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    startBingoGame();
                    break;
                case 2:
                    System.out.println("Thanks for playing Bingo!");
                    break;
                default:
                    System.out.println("Invalid choice. Please Enter [1] to PLAY BINGO or [2] to EXIT.");
            }
        } while (choice != 2);
        scanner.close();
    }

    private static void startBingoGame() {
        // Define Bingo game parameters
        final String[] BINGO_LETTERS = { "B", "I", "N", "G", "O" };
        final int[] LETTER_RANGES = { 15, 15, 15, 15, 15 }; // Number of values per letter
        final int NUM_ROWS = 5;
        final int NUM_COLS = 5;
        final int NUM_CARDS = 4;
        final int TOTAL_NUMBERS = 75;

        // Generate Bingo cards
        String[][][] bingoCards = new String[NUM_CARDS][NUM_ROWS][NUM_COLS];
        for (int i = 0; i < NUM_CARDS; i++) {
            bingoCards[i] = generateBingoCard(LETTER_RANGES, NUM_ROWS, NUM_COLS);
            System.out.println("\nPlayer's Bingo Card " + (i + 1) + ":");
            displayBingoCard(bingoCards[i]);
        }

        // Start drawing numbers
        System.out.println("\nPress Enter to start drawing numbers...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine(); // Wait for user to press Enter

        Random random = new Random();
        Set<Integer> drawnNumbers = new HashSet<>();
        int drawnNumbersCount = 0;
        boolean bingoAchieved = false;

        // Draw numbers until all numbers are drawn or a bingo is achieved
        while (drawnNumbers.size() < TOTAL_NUMBERS && !bingoAchieved) {
            int drawnNumber;
            do {
                drawnNumber = random.nextInt(TOTAL_NUMBERS) + 1; // Generate a random number between 1 and 75
            } while (!drawnNumbers.add(drawnNumber)); // Ensure the number hasn't been drawn before
            drawnNumbersCount++;

            System.out.println("\n---------------------------------");
            System.out.println("|    Drawn Number: " + String.format("%2d", drawnNumber) + "    |");
            System.out.println("---------------------------------");

            // Check each card and cross out the drawn number if present
            for (int i = 0; i < NUM_CARDS; i++) {
                System.out.println("\nPlayer's Bingo Card " + (i + 1) + ":");
                crossOutNumber(bingoCards[i], drawnNumber);
                displayBingoCard(bingoCards[i]);

                if (checkForBingo(bingoCards[i])) {
                    bingoAchieved = true;
                    String bingoPattern = checkForBingoPattern(bingoCards[i]);
                    System.out.println("BINGO! Player's Bingo Card " + (i + 1) + " wins with " + bingoPattern + "!");
                    break;
                }
            }

            System.out.println("\nPress Enter to draw another number...");
            scanner.nextLine(); // Wait for user to press Enter
        }

        if (!bingoAchieved) {
            System.out.println("\nAll numbers drawn. No Bingo achieved.");
        }

        // Identify the pattern in each card after all numbers are drawn
        for (int i = 0; i < NUM_CARDS; i++) {
            System.out.println("\nPlayer's Bingo Card " + (i + 1) + ":");
            String bingoPattern = checkForBingoPattern(bingoCards[i]);
            System.out.println("Pattern in Player's Bingo Card " + (i + 1) + ": " + bingoPattern);
        }

        System.out.println("\nTotal numbers drawn: " + drawnNumbersCount);
    }

    // Generate a random Bingo card
    private static String[][] generateBingoCard(int[] letterRanges, int numRows, int numCols) {
        String[][] card = new String[numRows][numCols];

        for (int col = 0; col < numCols; col++) {
            int min = col * 15 + 1;
            int max = min + letterRanges[col] - 1;
            List<Integer> columnValues = new ArrayList<>();
            for (int i = min; i <= max; i++) {
                columnValues.add(i);
            }
            Collections.shuffle(columnValues);
            for (int row = 0; row < numRows; row++) {
                if (row == numRows / 2 && col == numCols / 2) {
                    card[row][col] = "NA"; // Mark the center cell as "NA"
                } else {
                    card[row][col] = String.format("%2d", columnValues.get(row));
                }
            }
        }
        return card;
    }

    // Display Bingo card
    private static void displayBingoCard(String[][] card) {
        System.out.println("|-----------------------------|");
        System.out.println("|  B  |  I  |  N  |  G  |  O  |");
        System.out.println("|-----------------------------|");
        for (int i = 0; i < card.length; i++) {
            for (int j = 0; j < card[i].length; j++) {
                if (j == 0) {
                    System.out.print("|");
                }
                String cellValue = card[i][j];
                if (cellValue.equals(" X ")) {
                    System.out.print("  X  |");
                } else if (cellValue.equals("NA")) {
                    System.out.print("  NA |"); // Display "NA" for the bonus number
                } else {
                    System.out.printf("  %2s |", cellValue);
                }
            }
            System.out.println("\n|-----------------------------|");
        }
    }

    // Cross out the drawn number on the card
    private static void crossOutNumber(String[][] card, int drawnNumber) {
        for (int row = 0; row < card.length; row++) {
            for (int col = 0; col < card[row].length; col++) {
                if (card[row][col].equals(String.valueOf(drawnNumber))) {
                    card[row][col] = " X ";
                }
            }
        }
    }

    // Check for a Bingo on the card
    private static boolean checkForBingo(String[][] card) {
        // Check rows
        for (int row = 0; row < card.length; row++) {
            if (card[row][0].equals(" X ") && card[row][1].equals(" X ") && card[row][2].equals(" X ")
                    && card[row][3].equals(" X ") && card[row][4].equals(" X ")) {
                return true;
            }
        }

        // Check columns
        for (int col = 0; col < card[0].length; col++) {
            if (card[0][col].equals(" X ") && card[1][col].equals(" X ") && card[2][col].equals(" X ")
                    && card[3][col].equals(" X ") && card[4][col].equals(" X ")) {
                return true;
            }
        }

        // Check diagonals
        if (card[0][0].equals(" X ") && card[1][1].equals(" X ") && card[2][2].equals(" X ")
                && card[3][3].equals(" X ") && card[4][4].equals(" X ")) {
            return true;
        }
        if (card[0][4].equals(" X ") && card[1][3].equals(" X ") && card[2][2].equals(" X ")
                && card[3][1].equals(" X ") && card[4][0].equals(" X ")) {
            return true;
        }

        return false;
    }

    // Check for the pattern of Bingo achieved on the card
    private static String checkForBingoPattern(String[][] card) {
        // Check straight horizontal
        for (int row = 0; row < card.length; row++) {
            if (card[row][0].equals(" X ") && card[row][1].equals(" X ") && card[row][2].equals(" X ")
                    && card[row][3].equals(" X ") && card[row][4].equals(" X ")) {
                return "Straight Horizontal";
            }
        }

        // Check straight vertical
        for (int col = 0; col < card[0].length; col++) {
            if (card[0][col].equals(" X ") && card[1][col].equals(" X ") && card[2][col].equals(" X ")
                    && card[3][col].equals(" X ") && card[4][col].equals(" X ")) {
                return "Straight Vertical";
            }
        }

        // Check diagonal top-left to bottom-right
        if (card[0][0].equals(" X ") && card[1][1].equals(" X ") && card[2][2].equals(" X ")
                && card[3][3].equals(" X ") && card[4][4].equals(" X ")) {
            return "Diagonal (Top-Left to Bottom-Right)";
        }

        // Check diagonal top-right to bottom-left
        if (card[0][4].equals(" X ") && card[1][3].equals(" X ") && card[2][2].equals(" X ")
                && card[3][1].equals(" X ") && card[4][0].equals(" X ")) {
            return "Diagonal (Top-Right to Bottom-Left)";
        }

        // Check 1 cross
        if (card[1][1].equals(" X ") && card[1][3].equals(" X ") && card[2][2].equals(" X ")
                && card[3][1].equals(" X ") && card[3][3].equals(" X ")) {
            return "1 Cross";
        }

        // Check blackout
        boolean blackout = true;
        for (int row = 0; row < card.length; row++) {
            for (int col = 0; col < card[row].length; col++) {
                if (!card[row][col].equals(" X ")) {
                    blackout = false;
                    break;
                }
            }
            if (!blackout) {
                break;
            }
        }
        if (blackout) {
            return "Blackout";
        }

        return "No Bingo Pattern";
    }
}
