package iut.sae.algo.efficacite.etu3;

public class Recherche {
    public static int chercheMot(String botteDeFoin, String aiguille) {
        // Handle null or empty inputs
        if (botteDeFoin == null || aiguille == null || aiguille.isEmpty()) {
            return -1;
        }

        // Split haystack into lines
        String[] lines = botteDeFoin.split("\n");
        if (lines.length == 0) {
            return aiguille.isEmpty() ? 0 : -1;
        }

        int rows = lines.length;
        int cols = lines[0].length();

        // Check for irregular matrix
        for (String line : lines) {
            if (line.length() != cols) {
                return -1;
            }
        }

        // Convert to 2D char array
        char[][] grid = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            grid[i] = lines[i].toCharArray();
        }

        // Determine if we need to check reverse directions
        boolean checkReverse = aiguille.length() > 1 && !isPalindrome(aiguille);
        int[][] directions = checkReverse ?
            new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}} :
            new int[][]{{0, 1}, {1, 0}, {1, 1}, {-1, 1}}; // Only forward directions for single char or palindrome

        int count = 0;
        int wordLen = aiguille.length();

        // Iterate over each cell
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Only check if first character matches
                if (grid[i][j] != aiguille.charAt(0)) {
                    continue;
                }

                // Check each direction
                for (int[] dir : directions) {
                    int dirX = dir[0];
                    int dirY = dir[1];

                    // Check if word fits in this direction
                    int endRow = i + (wordLen - 1) * dirX;
                    int endCol = j + (wordLen - 1) * dirY;
                    if (endRow < 0 || endRow >= rows || endCol < 0 || endCol >= cols) {
                        continue;
                    }

                    // Check the word in this direction
                    boolean found = true;
                    for (int k = 0, x = i, y = j; k < wordLen; k++, x += dirX, y += dirY) {
                        if (grid[x][y] != aiguille.charAt(k)) {
                            found = false;
                            break;
                        }
                    }
                    if (found) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    // Check if a string is a palindrome
    private static boolean isPalindrome(String str) {
        int left = 0, right = str.length() - 1;
        while (left < right) {
            if (str.charAt(left++) != str.charAt(right--)) {
                return false;
            }
        }
        return true;
    }
}