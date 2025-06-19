package iut.sae.algo.sobriete.etu46;

public class Recherche {

    // Tableau des 8 directions possibles : droite, gauche, bas, haut, et les 4 diagonales
    private static final int[][] DIRECTIONS ={
        {0, 1}, {0, -1}, {1, 0}, {-1, 0},
        {1, 1}, {-1, -1}, {1, -1}, {-1, 1}
    };

    public static int chercheMot(String botteDeFoin, String aiguille){
        // Vérification des paramètres d'entrée
        if (botteDeFoin == null || aiguille == null || aiguille.isEmpty()) {
            return -1;
        }
        // Si la grille est vide, retourner 0
        if (botteDeFoin.isEmpty()) {
            return 0;
        }

        // Initialisation des variables pour les dimensions de la grille
        int largeur = -1;
        int hauteur = 0;
        
        // Parcours de la chaîne pour déterminer les dimensions
        for(int i = 0, start = 0; i <= botteDeFoin.length(); i++){
            // Détection de fin de ligne ou fin de chaîne
            if(i == botteDeFoin.length() || botteDeFoin.charAt(i) == '\n'){
                int ligneLongueur = i - start; // Calcul de la longueur de la ligne courante
                if(ligneLongueur == 0 && i == botteDeFoin.length()) {
                    break; // Ignorer dernière ligne vide
                }
                // Première ligne définit la largeur
                if(largeur == -1) {
                    largeur = ligneLongueur; 
                }else if (ligneLongueur != largeur){ // Vérifier que toutes les lignes ont la même longueur
                    return -1; 
                }
                hauteur++; // Incrémenter le nombre de lignes
                start = i + 1; // Début de la ligne suivante
            }
        }

        // Vérifier que la grille n'est pas vide
        if(hauteur == 0 || largeur == 0) {
            return 0;
        }

        // Création du tableau 2D pour représenter la grille
        char[][] grille = new char[hauteur][largeur];
        int ligne = 0, colonne = 0; // Position courante dans la grille
        
        // Remplissage de la grille caractère par caractère
        for(int i = 0; i < botteDeFoin.length(); i++){
            char c = botteDeFoin.charAt(i);
            if(c == '\n') { // Passage à la ligne suivante
                ligne++;
                colonne = 0;
            }else{ // Ajout du caractère dans la grille
                grille[ligne][colonne++] = c;
            }
        }

        // Cas spécial : recherche d'un seul caractère
        if(aiguille.length() == 1){
            return compterCaractereUnique(grille, aiguille.charAt(0), hauteur, largeur);
        }

        // Cas général : recherche d'un mot de plusieurs caractères
        return rechercheUnifiee(grille, aiguille, hauteur, largeur);
    }

    private static int compterCaractereUnique(char[][] grille, char c, int h, int l){
        int compteur = 0; // Compteur d'occurrences
        // Parcours de toute la grille
        for(int i = 0; i < h; i++) {
            for(int j = 0; j < l; j++){
                if(grille[i][j] == c) {// Si le caractère correspond
                    compteur++; // Incrémenter le compteur
                }
            }
        }
        return compteur;
    }

    private static int rechercheUnifiee(char[][] grille, String aiguille, int h, int l){
        int compteur = 0; // Compteur d'occurrences trouvées
        boolean palindrome = estPalindrome(aiguille); // Vérifier si le mot est un palindrome

        // Parcours de chaque position de la grille
        for(int i = 0; i < h; i++){
            for(int j = 0; j < l; j++){
                // Test de chaque direction possible
                for(int d = 0; d < DIRECTIONS.length; d++){
                    // Optimisation palindrome : éviter les directions opposées
                    if (palindrome && d % 2 == 1) {
                        continue;
                    }
                    int[] dir = DIRECTIONS[d]; // Direction courante
                    // Rechercher le mot dans cette direction
                    if (chercherDansDirection(grille, aiguille, i, j, dir[0], dir[1], h, l)){
                        compteur++; // Incrémenter si trouvé
                    }
                }
            }
        }
        return compteur;
    }

    private static boolean estPalindrome(String mot){
        int len = mot.length(); // Longueur du mot
        // Comparer les caractères symétriques
        for(int i = 0; i < len / 2; i++){
            if (mot.charAt(i) != mot.charAt(len - 1 - i)){
                 return false; // Différence trouvée
            }
        }
        return true; // Le mot est un palindrome
    }

    private static boolean chercherDansDirection(char[][] grille, String mot, int li, int co,
                                                 int dL, int dC, int h, int l){
        int len = mot.length(); // Longueur du mot à chercher
        // Calcul de la position finale dans cette direction
        int finL = li + (len - 1) * dL;
        int finC = co + (len - 1) * dC;

        // Vérifier que le mot reste dans les limites de la grille
        if(finL < 0 || finL >= h || finC < 0 || finC >= l) {
            return false;
        }

        // Vérifier caractère par caractère
        for(int k = 0; k < len; k++) {
            int x = li + k * dL; // Position ligne du k-ème caractère
            int y = co + k * dC; // Position colonne du k-ème caractère
            if (grille[x][y] != mot.charAt(k)) {
                return false; // Caractère ne correspond pas
            }
        }
        return true; // Tous les caractères correspondent
    }
}
