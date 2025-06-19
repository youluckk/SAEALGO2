package iut.sae.algo.simplicite.etu103;

/**
 * Implémente un algorithme de recherche de motif dans une grille de caractères.
 * Recherche dans 4 directions (droite, bas, 2 diagonales) et leurs inverses.
 */
public class Recherche {

    /**
     * Méthode principale de recherche de motif dans la grille
     */
    public static int chercheMot(String texteGrille, String motifRecherche) {
        // Validation des paramètres d'entrée
        if (texteGrille == null || motifRecherche == null || motifRecherche.isEmpty()) {
            return -1;
        }

        if (texteGrille.isEmpty()) {
            return 0;
        }

        String[] lignesGrille = texteGrille.split("\n");
        int largeurGrille = lignesGrille[0].length();

        // Vérification de la grille régulière
        for (String ligne : lignesGrille) {
            if (ligne.length() != largeurGrille) {
                return -1;
            }
        }

        // Recherche d'un seul caractère
        if (motifRecherche.length() == 1) {
            return compterOccurrences(lignesGrille, motifRecherche.charAt(0));
        }

        String motifInverse = new StringBuilder(motifRecherche).reverse().toString();
        int totalTrouves = 0;

        // Directions de recherche
        final int[][] directions = {
            {0, 1},  // Droite
            {1, 0},  // Bas
            {1, 1},  // Diagonale bas-droite
            {1, -1}  // Diagonale bas-gauche
        };

        // Parcours de la grille
        for (int y = 0; y < lignesGrille.length; y++) {
            for (int x = 0; x < largeurGrille; x++) {
                for (int[] dir : directions) {
                    totalTrouves += rechercherDirection(lignesGrille, y, x, motifRecherche, dir[0], dir[1]);
                    
                    if (!motifRecherche.equals(motifInverse)) {
                        totalTrouves += rechercherDirection(lignesGrille, y, x, motifInverse, dir[0], dir[1]);
                    }
                }
            }
        }

        return totalTrouves;
    }

    /**
     * Compte les occurrences d'un caractère dans la grille
     */
    private static int compterOccurrences(String[] grille, char caractere) {
        int compteur = 0;
        for (String ligne : grille) {
            for (int x = 0; x < ligne.length(); x++) {
                if (ligne.charAt(x) == caractere) {
                    compteur++;
                }
            }
        }
        return compteur;
    }

    /**
     * Recherche un motif dans une direction spécifique
     */
    private static int rechercherDirection(String[] grille, int y, int x, 
                                         String motif, int dy, int dx) {
        final int hauteur = grille.length;
        final int largeur = grille[0].length();
        final int longueur = motif.length();

        for (int i = 0; i < longueur; i++) {
            int cy = y + i * dy;
            int cx = x + i * dx;

            if (cy < 0 || cy >= hauteur || cx < 0 || cx >= largeur) {
                return 0;
            }

            if (grille[cy].charAt(cx) != motif.charAt(i)) {
                return 0;
            }
        }

        return 1;
    }
}
