package iut.sae.algo.efficacite;

public class Recherche {

    /**
     * Recherche le nombre d'occurrences du mot aiguille dans une grille chaîne multiligne botteDeFoin,
     * en tenant compte de ses inverses, sauf si le mot est un palindrome 
     *
     * @param botteDeFoin, la grille sous forme de chaîne de caractère multiligne, séparateur '\n', sans retour à la ligne final
     * @param aiguille, le mot à rechercher dans la grille
     * @return le nombre d'occurrences du mot (et de son inverse si ce n’est pas un palindrome), ou -1 en cas d'erreur, ou 0 si aucun mot n’est trouvé
     */
    public static int chercheMot(String botteDeFoin, String aiguille) {
        char[][] matrice;
        char[] aiguilleChar;
        int cpt = 0;
        boolean rechercheDoubleSens;


        // Vérification des paramètres
        if(aiguille == null || botteDeFoin == null || aiguille.equals("")){
            return -1;
        }

        // Vérification de la forme de la matrice
        if(!estBotteValide(botteDeFoin)){
            return -1;
        }

        matrice = botte2matrice(botteDeFoin);
        aiguilleChar = aiguille.toCharArray();
        rechercheDoubleSens = !estAiguillePalindrome(aiguille);
        

        //Si l'aiguille est simple, on ne compte qu'une fois le caractère recherché
        cpt+=rechercheHorizontale(matrice, aiguilleChar, rechercheDoubleSens);
        if(!estAiguilleSimple(aiguille)){
            cpt+=rechercheVerticale(matrice, aiguilleChar, rechercheDoubleSens);
            cpt+=recherchePremiereDiagonale(matrice, aiguilleChar, rechercheDoubleSens);
            cpt+=rechercheSecondeDiagonale(matrice, aiguilleChar, rechercheDoubleSens);
        }

        return cpt;
    }


    /**
     * Convertit une chaîne multiligne en une matrice de caractères.
     *
     * @param botte la chaîne contenant la grille, chaque ligne étant séparée par '\n'
     * @return une matrice de caractères correspondant à la grille
     */
    private static char[][] botte2matrice(String botte){
        String[] lignes = botte.split("\n");
        int nbLignes = lignes.length;
        int NbCols = lignes[0].length();
        char[][] matrice = new char[nbLignes][NbCols];

        for (int i = 0; i < nbLignes; i++) {
            matrice[i] = lignes[i].toCharArray();
        }

        return matrice;
    }


    /**
     * Vérifie si l'aiguille est constituée d'un seul caractère.
     *
     * @param aiguille le mot à tester
     * @return true si l'aiguille a une longueur de 1, false sinon
     */
    private static boolean estAiguilleSimple(String aiguille){
        return aiguille.length()==1;
    }


    /**
     * Vérifie si l'aiguille est un palindrome.
     *
     * @param aiguille le mot à tester
     * @return true si le mot est un palindrome, false sinon
     */
    private static boolean estAiguillePalindrome(String aiguille){
        String aiguilleInversee = new StringBuilder(aiguille).reverse().toString();
        return aiguilleInversee.equals(aiguille);
    }


    /**
     * Vérifie que toutes les lignes de la grille ont la même longueur.
     *
     * @param botte la grille sous forme de chaîne de caractères multiligne
     * @return true si la grille est valide, false sinon
     */
    private static boolean estBotteValide(String botte){
        String[] lignes = botte.split("\n");
        int longueurPremiereLigne = lignes[0].length();

        for(String ligne : lignes) {
            if(ligne.length() != longueurPremiereLigne){
                return false;
            }
        }
        return true;
    }


    /**
     * Recherche le nombre d'occurrences d'un mot dans une ligne, en tenant compte du sens inverse si demandé.
     * Le comptage autorise les chevauchements.
     *
     * @param ligne la ligne dans laquelle rechercher
     * @param aiguille le mot à rechercher sous forme de tableau de caractères
     * @param doubleSens true pour rechercher aussi dans le sens inverse, false sinon
     * @return le nombre d'occurrences trouvées dans la ligne
     */
    private static int chercheMotEnLigne(char[] ligne, char[] aiguille, boolean doubleSens){
        int cpt = 0;
        int longueur = ligne.length;
        int tailleAiguille = aiguille.length;
        boolean estValide = true;

        // Recherche de gauche à droite
        for (int i = 0; i <= longueur - tailleAiguille; i++) {
            estValide = true;
            for(int j = 0; j < tailleAiguille && estValide; j++) {
                estValide = ligne[i + j] == aiguille[j];
            }
            if (estValide) {
                cpt++;
            }
        }

        // Recherche de droite à gauche si doubleSens est vrai
        if(doubleSens){
            for (int i = 0; i <= longueur - tailleAiguille; i++) {
                estValide = true;
                for (int j = 0; j < tailleAiguille && estValide; j++) {
                    estValide = ligne[i + j] == aiguille[tailleAiguille - 1 - j];
                }
                if (estValide) {
                    cpt++;
                }
            }
        }

        return cpt;
    }


    /**
     * Recherche le mot horizontalement dans chaque ligne de la matrice.
     *
     * @param matrice la matrice représentant la grille
     * @param aiguille le mot à rechercher
     * @param doubleSens true pour inclure la recherche droite à gauche si applicable
     * @return le nombre total d'occurrences trouvées horizontalement
     */
    private static int rechercheHorizontale(char[][] matrice, char[] aiguille, boolean doubleSens){
        int cpt = 0;
        for(char[] ligne : matrice){
            cpt += chercheMotEnLigne(ligne, aiguille, doubleSens);
        }

        return cpt;
    }


    /**
     * Recherche le mot verticalement dans la matrice.
     *
     * @param matrice la matrice représentant la grille
     * @param aiguille le mot à rechercher
     * @param doubleSens true pour inclure la recherche du bas vers le haut si applicable
     * @return le nombre total d'occurrences trouvées verticalement
     */
    private static int rechercheVerticale(char[][] matrice, char[] aiguille, boolean doubleSens){
        char[] ligne = new char[matrice.length];
        int cpt = 0;
        for(int y = 0; y<matrice[0].length; y++){
            for(int x = 0; x<matrice.length; x++){
                ligne[x] = matrice[x][y];
            }
            cpt += chercheMotEnLigne(ligne, aiguille, doubleSens);
        }

        return cpt;
    }


    /**
     * Recherche le mot dans toutes les diagonales de haut-gauche vers bas-droite.
     *
     * @param matrice la matrice représentant la grille
     * @param aiguille le mot à rechercher
     * @param doubleSens true pour inclure les diagonales dans les deux sens
     * @return le nombre total d'occurrences trouvées dans cette diagonale
     */
    private static int recherchePremiereDiagonale(char[][] matrice, char[] aiguille, boolean doubleSens){
        int lignes = matrice.length;
        int colonnes = matrice[0].length;
        int cpt = 0;
        
        // Diagonales commençant sur la première colonne
        for(int i = 0; i < lignes; i++) {
            int x = i;
            int y = 0;
            int taille = Math.min(lignes - i, colonnes);
            char[] diag = new char[taille];

            for (int k = 0; k < taille; k++) {
                diag[k] = matrice[x + k][y + k];
            }

            cpt += chercheMotEnLigne(diag, aiguille, doubleSens);
        }

        // Diagonales commençant sur la première ligne (sauf coin supérieur gauche)
        for(int j = 1; j < colonnes; j++) {
            int x = 0;
            int y = j;
            int taille = Math.min(lignes, colonnes - j);
            char[] diag = new char[taille];

            for(int k = 0; k < taille; k++) {
                diag[k] = matrice[x + k][y + k];
            }

            cpt += chercheMotEnLigne(diag, aiguille, doubleSens);
        }

        return cpt;
    }


    /**
     * Recherche le mot dans toutes les diagonales de bas-gauche vers haut-droite.
     *
     * @param matrice la matrice représentant la grille
     * @param aiguille le mot à rechercher
     * @param doubleSens true pour inclure les diagonales dans les deux sens
     * @return le nombre total d'occurrences trouvées dans cette diagonale
     */
    private static int rechercheSecondeDiagonale(char[][] matrice, char[] aiguille, boolean doubleSens){
        int lignes = matrice.length;
        int colonnes = matrice[0].length;
        int cpt = 0;

        // Diagonales commençant sur la dernière ligne
        for (int j = 0; j < colonnes; j++) {
            int x = lignes - 1;
            int y = j;
            int taille = Math.min(lignes, colonnes - j);
            char[] diag = new char[taille];

            for (int k = 0; k < taille; k++) {
                diag[k] = matrice[x - k][y + k];
            }

            cpt += chercheMotEnLigne(diag, aiguille, doubleSens);
        }

        // Diagonales commençant sur la première colonne (sauf coin bas-gauche)
        for (int i = lignes - 2; i >= 0; i--) {
            int x = i;
            int y = 0;
            int taille = Math.min(i + 1, colonnes);
            char[] diag = new char[taille];

            for (int k = 0; k < taille; k++) {
                diag[k] = matrice[x - k][y + k];
            }

            cpt += chercheMotEnLigne(diag, aiguille, doubleSens);
        }

        return cpt;
    }
}
