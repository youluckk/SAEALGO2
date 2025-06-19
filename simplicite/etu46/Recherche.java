package iut.sae.algo.simplicite.etu46;

public class Recherche{
    
    /**
     * Le mot peut être trouvé dans 8 directions différentes :
     * - Horizontalement 
     * - Verticalement 
     * - Diagonalement 
     * 
     * @param botteDeFoin La grille de texte dans laquelle rechercher, est représentée comme une chaîne
     * @param aiguille Le mot à rechercher dans la grille
     * @return Le nombre total d'occurrences trouvées, ou -1 en cas d'erreur
     */
    public static int chercheMot(String botteDeFoin, String aiguille){
        // Vérification des cas d'erreur : paramètres null ou aiguille vide
        if(botteDeFoin == null || aiguille == null || aiguille.isEmpty()){
            return -1; // Retourne -1 pour indiquer une erreur
        }

        // Cas special : si la grille est vide, retourner 0
        if(botteDeFoin.isEmpty()){
            return 0;
        }

        // Conversion de la chaîne en tableau de lignes en utilisant le séparateur de ligne
        String[] lignes = botteDeFoin.split("\n");
        // Calcul de la hauteur de la grille
        int hauteur = lignes.length;

        // Calcul de la largeur de la première ligne de la grille 
        int largeur = lignes[0].length();

        // Vérification que toutes les lignes ont la même longueur 
        for(String ligne : lignes) {
            if (ligne.length() != largeur){
                return -1; // Grille irréguliere, retourne une erreur
            }
        }

        // Création de la grille de caractères à partir du tableau de lignes
        char[][] grille = new char[hauteur][largeur];
        // Remplissage de la grille caractères par caractères
        for(int i = 0; i < hauteur; i++) {
            grille[i] = lignes[i].toCharArray(); // Conversion de chaque ligne en tableau de caractères
        }

        // Initialisation du compteur d'occurrences trouvés
        int compteur = 0;

        // Cas spécial : mot d'un seul caractère pour éviter le comptage multiple
        if(aiguille.length() == 1){
            char c = aiguille.charAt(0); // Récupération du caractère unique
            // Parcours de toute la grille
            for (int i = 0; i < hauteur; i++){
                for (int j = 0; j < largeur; j++){
                    // Vérification si le caractère correspond
                    if (grille[i][j] == c) {
                        compteur++; // Incrémentation du compteur
                    }
                }
            }
            return compteur; // Retour du nombre d'occurrences
        }

        // Création de la version inversé du mot pour optimiser les recherches
        String aiguilleInverse = new StringBuilder(aiguille).reverse().toString();
        
        // Parcours de chaque position de la grille comme point de départ potentiel
        for(int i = 0; i < hauteur; i++) {
            for(int j = 0; j < largeur; j++){
                // Recherche horizontale normale 
                compteur += chercherHorizontal(grille, aiguille, i, j);
                
                // Recherche horizontale inverse seulement si le mot n'est pas un palindrome
                if (!aiguille.equals(aiguilleInverse)) {
                    compteur += chercherHorizontalInverse(grille, aiguille, i, j);
                }
                
                // Recherche verticale normale
                compteur += chercherVertical(grille, aiguille, i, j);
                
                // Recherche verticale inverse seulement si le mot n'est pas un palindrome
                if (!aiguille.equals(aiguilleInverse)) {
                    compteur += chercherVerticalInverse(grille, aiguille, i, j);
                }
                
                // Recherche diagonale principale 
                compteur += chercherDiagonalePrincipale(grille, aiguille, i, j);
                
                // Recherche diagonale principale inverse seulement si le mot n'est pas un palindrome
                if (!aiguille.equals(aiguilleInverse)) {
                    compteur += chercherDiagonalePrincipaleInverse(grille, aiguille, i, j);
                }
                
                // Recherche diagonale secondaire
                compteur += chercherDiagonaleSecondaire(grille, aiguille, i, j);
                
                // Recherche diagonale secondaire inverse seulement si le mot n'est pas un palindrome
                if (!aiguille.equals(aiguilleInverse)) {
                    compteur += chercherDiagonaleSecondaireInverse(grille, aiguille, i, j);
                }
            }
        }

        return compteur; // Retour du nombre total d'occurrences trouvés
    }

    /**
     * Recherche un mot horizontalement de gauche a droite a partir d'une position donnée.
     * 
     * @param grille La grille de caractéres dans laquelle effectuer la recherche
     * @param mot Le mot a rechercher
     * @param ligne L'indice de la ligne de départ
     * @param colonne L'indice de la colonne de départ
     * @return 1 si le mot est trouver, 0 sinon
     */
    private static int chercherHorizontal(char[][] grille, String mot, int ligne, int colonne){
        // Vérification que le mot peut tenir horizontalement depuis cette position
        if(colonne + mot.length() > grille[0].length) return 0;
        
        // Vérification caractères par caractères
        for(int k = 0; k < mot.length(); k++) {
            // Comparaison du caractère de la grille avec celui du mot
            if(grille[ligne][colonne + k] != mot.charAt(k)) return 0;
        }
        return 1; // Mot trouver
    }

    /**
     * Recherche un mot horizontalement de droite a gauche a partir d'une position donnée.
     * 
     * @param grille La grille de caractére dans laquelle effectuer la recherche
     * @param mot Le mot à rechercher
     * @param ligne L'indice de la ligne de départ
     * @param colonne L'indice de la colonne de départ
     * @return 1 si le mot est trouver, 0 sinon
     */
    private static int chercherHorizontalInverse(char[][] grille, String mot, int ligne, int colonne){
        // Vérification que le mot peut tenir horizontalement vers la gauche depuis cette position
        if(colonne - mot.length() + 1 < 0) return 0;
        
        // Vérification caractére par caractére en allant vers la gauche
        for(int k = 0; k < mot.length(); k++) {
            // Comparaison du caractére de la grille avec celui du mot
            if(grille[ligne][colonne - k] != mot.charAt(k)) return 0;
        }
        return 1; // Mot trouvé
    }

    /**
     * Recherche un mot verticalement de haut en bas a partir d'une position donnée.
     * 
     * @param grille La grille de caractére dans laquelle effectuer la recherche
     * @param mot Le mot à rechercher
     * @param ligne L'indice de la ligne de départ
     * @param colonne L'indice de la colonne de départ
     * @return 1 si le mot est trouver, 0 sinon
     */
    private static int chercherVertical(char[][] grille, String mot, int ligne, int colonne){
        // Vérification que le mot peut tenir verticalement depuis cette position
        if(ligne + mot.length() > grille.length) return 0;
        
        // Vérification caractére par caractére en descendant
        for(int k = 0; k < mot.length(); k++) {
            // Comparaison du caractère de la grille avec celui du mot
            if(grille[ligne + k][colonne] != mot.charAt(k)) return 0;
        }
        return 1; // Mot trouver
    }

    /**
     * Recherche un mot verticalement de bas en haut a partir d'une position donnée.
     * 
     * @param grille La grille de caractére dans laquelle effectuer la recherche
     * @param mot Le mot a rechercher
     * @param ligne L'indice de la ligne de départ
     * @param colonne L'indice de la colonne de départ
     * @return 1 si le mot est trouver, 0 sinon
     */
    private static int chercherVerticalInverse(char[][] grille, String mot, int ligne, int colonne){
        // Vérification que le mot peut tenir verticalement vers le haut depuis cette position
        if(ligne - mot.length() + 1 < 0) return 0;
        
        // Vérification caractére par caractére en remontant
        for(int k = 0; k < mot.length(); k++) {
            // Comparaison du caractère de la grille avec celui du mot
            if(grille[ligne - k][colonne] != mot.charAt(k)) return 0;
        }
        return 1; // Mot trouver
    }

    /**
     * Recherche un mot en diagonale principale a partir d'une position donnée.
     * 
     * @param grille La grille de caractére dans laquelle effectuer la recherche
     * @param mot Le mot a rechercher
     * @param ligne L'indice de la ligne de départ
     * @param colonne L'indice de la colonne de départ
     * @return 1 si le mot est trouver, 0 sinon
     */
    private static int chercherDiagonalePrincipale(char[][] grille, String mot, int ligne, int colonne){
        // Vérification que le mot peut tenir en diagonale depuis cette position
        if(ligne + mot.length() > grille.length || colonne + mot.length() > grille[0].length) return 0;
        
        // Vérification caractére par caractére en diagonale
        for(int k = 0; k < mot.length(); k++) {
            // Comparaison du caractére de la grille avec celui du mot
            if(grille[ligne + k][colonne + k] != mot.charAt(k)) return 0;
        }
        return 1; // Mot trouver
    }

    /**
     * Recherche un mot en diagonale principale inverse a partir d'une position donnée.
     * 
     * @param grille La grille de caractére dans laquelle effectuer la recherche
     * @param mot Le mot à rechercher
     * @param ligne L'indice de la ligne de départ
     * @param colonne L'indice de la colonne de départ
     * @return 1 si le mot est trouver, 0 sinon
     */
    private static int chercherDiagonalePrincipaleInverse(char[][] grille, String mot, int ligne, int colonne){
        // Vérification que le mot peut tenir en diagonale inverse depuis cette position
        if(ligne - mot.length() + 1 < 0 || colonne - mot.length() + 1 < 0) return 0;
        
        // Vérification caractére par caractére en diagonale inverse
        for(int k = 0; k < mot.length(); k++) {
            // Comparaison du caractére de la grille avec celui du mot
            if(grille[ligne - k][colonne - k] != mot.charAt(k)) return 0;
        }
        return 1; // Mot trouver
    }

    /**
     * Recherche un mot en diagonale secondaire a partir d'une position donnée.
     * 
     * @param grille La grille de caractére dans laquelle effectuer la recherche
     * @param mot Le mot à rechercher
     * @param ligne L'indice de la ligne de départ
     * @param colonne L'indice de la colonne de départ
     * @return 1 si le mot est trouver, 0 sinon
     */
    private static int chercherDiagonaleSecondaire(char[][] grille, String mot, int ligne, int colonne){
        // Vérification que le mot peut tenir en diagonale secondaire depuis cette position
        if(ligne + mot.length() > grille.length || colonne - mot.length() + 1 < 0) return 0;
        
        // Vérification caractére par caractére en diagonale secondaire
        for(int k = 0; k < mot.length(); k++) {
            // Comparaison du caractére de la grille avec celui du mot
            if(grille[ligne + k][colonne - k] != mot.charAt(k)) return 0;
        }
        return 1; // Mot trouver
    }

    /**
     * Recherche un mot en diagonale secondaire inverse a partir d'une position donnée.
     * 
     * @param grille La grille de caractére dans laquelle effectuer la recherche
     * @param mot Le mot à rechercher
     * @param ligne L'indice de la ligne de départ
     * @param colonne L'indice de la colonne de départ
     * @return 1 si le mot est trouver, 0 sinon
     */
    private static int chercherDiagonaleSecondaireInverse(char[][] grille, String mot, int ligne, int colonne){
        // Vérification que le mot peut tenir en diagonale secondaire inverse depuis cette position
        if(ligne - mot.length() + 1 < 0 || colonne + mot.length() > grille[0].length) return 0;
        
        // Vérification caractére par caractére en diagonale secondaire inverse
        for(int k = 0; k < mot.length(); k++) {
            // Comparaison du caractére de la grille avec celui du mot
            if(grille[ligne - k][colonne + k] != mot.charAt(k)) return 0;
        }
        return 1; // Mot trouver
    }
}
