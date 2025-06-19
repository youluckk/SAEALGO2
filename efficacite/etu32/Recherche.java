package iut.sae.algo.efficacite.etu32;

public class Recherche {

    public static int chercheMot(String botteDeFoin, String aiguille) {
        // Vérifications de base
        if (botteDeFoin == null || aiguille == null || aiguille.length() == 0) return -1;
        if (botteDeFoin.length() == 0) return 0;

        // Cas spécial : aiguille d'un seul caractère (plus rapide à traiter)
        if (aiguille.length() == 1) {
            char c = aiguille.charAt(0);
            int count = 0;
            for (int i = 0; i < botteDeFoin.length(); i++) {
                char g = botteDeFoin.charAt(i);
                if (g != '\n' && g == c) count++;
            }
            return count;
        }

        int hauteur = 1, largeur = -1, longueurActuelle = 0;

        // Déterminer largeur et hauteur de la grille (en s'assurant qu'elle est rectangulaire)
        for (int i = 0; i < botteDeFoin.length(); i++) {
            char ch = botteDeFoin.charAt(i);
            if (ch == '\n') {
                if (largeur == -1) largeur = longueurActuelle;
                else if (longueurActuelle != largeur) return -1;
                longueurActuelle = 0;
                hauteur++;
            } else {
                longueurActuelle++;
            }
        }
        // Vérification de la dernière ligne
        if (largeur == -1) largeur = longueurActuelle;
        else if (longueurActuelle != largeur) return -1;

        int total = 0;
        boolean estPalindrome = estPalindrome(aiguille);
        int len = aiguille.length();

        // Déplacements dans les 8 directions possibles
        int[] dx = {1, -1, 0,  0,  1, -1,  1, -1};
        int[] dy = {0,  0, 1, -1,  1, -1, -1,  1};

        // Parcours de chaque cellule de la botte de foin
        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++) {
                for (int dir = 0; dir < 8; dir++) {
                    // Si l’aiguille est un palindrome, on évite les directions inverses (double comptage)
                    if (estPalindrome && dir % 2 == 1) continue;

                    int cx = x, cy = y, i = 0;
                    while (i < len) {
                        // Sortie des limites
                        if (cx < 0 || cx >= largeur || cy < 0 || cy >= hauteur) break;

                        // Calcul de l’index dans la chaîne (en tenant compte des \n)
                        int index = cy * (largeur + 1) + cx;

                        // Comparaison caractère par caractère
                        if (index >= botteDeFoin.length() || botteDeFoin.charAt(index) != aiguille.charAt(i)) break;

                        cx += dx[dir];
                        cy += dy[dir];
                        i++;
                    }

                    // Si tous les caractères correspondent
                    if (i == len) total++;
                }
            }
        }

        return total;
    }

    // Vérifie si l’aiguille est un palindrome
    private static boolean estPalindrome(String s) {
        int n = s.length();
        for (int i = 0; i < n / 2; i++) {
            if (s.charAt(i) != s.charAt(n - 1 - i)) return false;
        }
        return true;
    }
}
