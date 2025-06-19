package iut.sae.algo.efficacite.etu44;

public class Recherche{
    public static int chercheMot(String botteDeFoin, String aiguille) {
        if (!estValide(botteDeFoin, aiguille)){
            return -1;
        }

        if (aiguille.length() == 1){
            int compteur = 0;
            char c = aiguille.charAt(0);
            for (int i = 0;i < botteDeFoin.length();i++){
                if (botteDeFoin.charAt(i) == c){
                    compteur++;
                }
            }
            return compteur;
        }

        int compteur = 0;
        boolean estPal = estPalindrome(aiguille);
        String aiguilleInverse = inverser(aiguille);

        int nbLignes = nbLignes(botteDeFoin);
        int nbColonnes = nbColonnes(botteDeFoin);
        char[][] tableau = creerTableau(botteDeFoin, nbLignes, nbColonnes);

        compteur += chercheDirection(tableau, aiguille, 0, 1);  // Horizontal G->D
        if (!estPal) compteur += chercheDirection(tableau, aiguilleInverse, 0, 1);// Horizontal D->G

        compteur += chercheDirection(tableau, aiguille, 1, 0);  // Vertical H->B
        if (!estPal) compteur += chercheDirection(tableau, aiguilleInverse, 1, 0);// Vertical B->H

        compteur += chercheDirection(tableau, aiguille, 1, 1);  // Diagonale HG->BD
        if (!estPal) compteur += chercheDirection(tableau, aiguilleInverse, 1, 1);// Diagonale BD->HG

        compteur += chercheDirection(tableau, aiguille, 1, -1); // Diagonale HD->BG
        if (!estPal) compteur += chercheDirection(tableau, aiguilleInverse, 1, -1);// Diagonale BG->HD

        return compteur;
    }

    /**
     * Cherche l'aiguille dans le tableau dans la direction donnée.
     * @param tableau Le tableau de caractères dans lequel chercher.
     * @param aiguille La chaîne de caractères à chercher.
     * @param dirx La direction en x (1 pour bas, 0 pour horizontal, -1 pour haut).
     * @param diry La direction en y (1 pour droite, 0 pour vertical, -1 pour gauche).
     * @return Le nombre de fois que l'aiguille est trouvée dans la direction donnée.
     */
    private static int chercheDirection(char[][] tableau, String aiguille, int dirx, int diry){
        int compteur = 0;
        int nbLignes = tableau.length;
        int nbColonnes = tableau[0].length;
        int len = aiguille.length();

        for (int i = 0;i < nbLignes;i++){
            for (int j = 0;j < nbColonnes;j++){
                int xFin = i + (len - 1) * dirx;
                int yFin = j + (len - 1) * diry;
                if (xFin >= 0 && xFin < nbLignes && yFin >= 0 && yFin < nbColonnes){
                    boolean trouve = true;
                    for (int k = 0;k < len;k++){
                        if (tableau[i + k * dirx][j + k * diry] != aiguille.charAt(k)){
                            trouve = false;
                            break;
                        }
                    }
                    if (trouve){
                        compteur++;
                    }
                }
            }
        }

        return compteur;
    }

    /**
     * Vérifie si la botte de foin et l'aiguille sont valides.
     * @param botteDeFoin La botte de foin dans laquelle chercher.
     * @param aiguille L'aiguille à chercher dans la botte de foin.
     * @return true si la botte de foin et l'aiguille sont valides, false sinon.
     */
    public static boolean estValide(String botteDeFoin, String aiguille){
        if (botteDeFoin == null || aiguille == null || (botteDeFoin.length() > 0 && botteDeFoin.length() < aiguille.length())){
            return false;
        }
        int nbColonnes = -1;
        int c = 0;
        while (c < botteDeFoin.length()){
            int ligneLongueur = 0;
            while (c < botteDeFoin.length() && botteDeFoin.charAt(c) != '\n'){
                ligneLongueur++;
                c++;
            }
            if (nbColonnes == -1){
                nbColonnes = ligneLongueur;
            }
            else if (ligneLongueur != nbColonnes){
                return false;
            }
            c++;
        }
        return true;
    }

    /**
     * Vérifie si le mot est un palindrome.
     * @param mot le mot à vérifier
     * @return true si le mot est un palindrome, false sinon
     */
    public static boolean estPalindrome(String mot){
        if (mot == null || mot.length() < 2){
            return false;
        }
        int debut = 0, fin = mot.length() - 1;
        while (debut < fin){
            if (mot.charAt(debut++) != mot.charAt(fin--)){
                return false;
            }
        }
        return true;
    }

    /**
     * Inverse une chaîne de caractères.
     * @param s la chaîne de caractères à inverser
     * @return la chaîne de caractères inversée
     */
    private static String inverser(String s){
        String resultat = "";
        for (int i = s.length() - 1;i >= 0;i--){
            resultat += s.charAt(i);
        }
        return resultat;
    }

    /**
     * Compte le nombre de lignes dans le texte.
     * @param texte le texte dans lequel compter les lignes
     * @return le nombre de lignes dans le texte
     */
    private static int nbLignes(String texte){
        int c = 1;
        for (int i = 0;i < texte.length();i++){
            if (texte.charAt(i) == '\n') c++;
        }
        return c;
    }

    /**
     * Compte le nombre de colonnes dans la première ligne du texte.
     * @param texte le texte dans lequel compter les colonnes
     * @return le nombre de colonnes dans la première ligne du texte
     */
    private static int nbColonnes(String texte){
        int c = 0;
        while (c < texte.length() && texte.charAt(c) != '\n'){
            c++;
        }
        return c;
    }

    /**
     * Crée un tableau de caractères à partir du texte.
     * @param texte le texte à convertir en tableau
     * @param nbLignes le nombre de lignes dans le texte
     * @param nbColonnes le nombre de colonnes dans le texte
     * @return un tableau de caractères représentant le texte
     */
    private static char[][] creerTableau(String texte, int nbLignes, int nbColonnes){
        char[][] tableau = new char[nbLignes][nbColonnes];
        int ligne = 0, col = 0;
        for (int i = 0;i < texte.length();i++){
            char c = texte.charAt(i);
            if (c == '\n'){
                ligne++;
                col = 0;
            }
            else{
                tableau[ligne][col++] = c;
            }
        }
        return tableau;
    }
}
