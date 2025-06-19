package iut.sae.algo.sobriete.etu1;

public class Recherche {
    public static int chercheMot(String botteDeFoin, String aiguille) {
        if (botteDeFoin == null || aiguille == null || aiguille.length() == 0) return -1;

        String[] lignes = botteDeFoin.split("\n");
        int lignesCount = lignes.length;
        int colonnes = lignes[0].length();

        for (int i = 0; i < lignesCount; i++) {
            if (lignes[i].length() != colonnes) return -1;
        }

        char[][] grille = new char[lignesCount][colonnes];
        for (int i = 0; i < lignesCount; i++) {
            for (int j = 0; j < colonnes; j++) {
                grille[i][j] = lignes[i].charAt(j);
            }
        }

        if (aiguille.length() == 1) {
            char c = aiguille.charAt(0);
            int nb = 0;
            for (int i = 0; i < lignesCount; i++) {
                for (int j = 0; j < colonnes; j++) {
                    if (grille[i][j] == c) nb++;
                }
            }
            return nb;
        }

        boolean palin = true;
        for (int i = 0; i < aiguille.length() / 2; i++) {
            if (aiguille.charAt(i) != aiguille.charAt(aiguille.length() - 1 - i)) {
                palin = false;
                break;
            }
        }

        int[][] dirs = palin ?
            new int[][]{{0,1},{1,0},{1,1},{1,-1}} :
            new int[][]{{0,1},{0,-1},{1,0},{-1,0},{1,1},{-1,-1},{1,-1},{-1,1}};

        int total = 0;

        for (int i = 0; i < lignesCount; i++) {
            for (int j = 0; j < colonnes; j++) {
                for (int d = 0; d < dirs.length; d++) {
                    int dx = dirs[d][0], dy = dirs[d][1];
                    int x = i, y = j;
                    int k;
                    for (k = 0; k < aiguille.length(); k++) {
                        int nx = x + k * dx;
                        int ny = y + k * dy;
                        if (nx < 0 || ny < 0 || nx >= lignesCount || ny >= colonnes) break;
                        if (grille[nx][ny] != aiguille.charAt(k)) break;
                    }
                    if (k == aiguille.length()) total++;
                }
            }
        }

        return total;
    }
}
