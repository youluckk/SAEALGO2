package iut.sae.algo.sobriete.etu55;

/**
 * La classe recherche fournit des méthodes utilitaires permettant
 * de recherche un mot dans une matrice de caractères représentée sous forme
 * de chaîne de caractères
 * 
 * Les recherches sont effectuées dans toutes les directions (horizontalement, verticalement et diagonalement),
 * et prennent en compte les mots palindromes pour éviter les doublons
 * 
 * Fonctionnalités principales :
 * - Vérification de la validité d'une matrice
 * - Conversion d'une chaîne en matrice de caractères
 * - Lecture directionnelle dans une matrice
 * - Recherche de mot et comptage des occurences
 */
public class Recherche {
    /**
     * Fonction centrale qui orchestre la recherche d'un mot (aiguille) dans une matrice (botteDeFoin)
     * Valide d'abord les entrées, puis appelle les fonctions utilitaires pour transformer et rechercher
     * 
     * @param botteDeFoin la matrice sous forme de chaîne de caractères, lignes séparées par des étapes '\n'
     * @param aiguille le mot à trouver dns la matrice
     * @return le nombre d'occurences du mot dans la matrice (toutes directions),
     *         ou -1 si les paramètres sont invalides ou si la matrice n'est pas valide
     */
    public static int chercheMot(String botteDeFoin, String aiguille) {
        // Vérifie que les chaînes ne sont pas null
        if (botteDeFoin == null || aiguille == null) return -1;

        // Vérifie que la chaîne représente une matrice régulière (toutes les lignes de même longueur)
        if (!matriceValide(botteDeFoin)) return -1;

        // Retourne le nombre total d'occurences
        return compterOccurrences(convertirEnMatrice(botteDeFoin), aiguille);
    }

    /**
     * Vérifie si une chaîne de caractères peut représenter une matrice rectangulaire
     * Une matrice est dite valide si :
     * - toutes ses lignes ont le même nombre de caractères (hors caractères '\n')
     * - elle contient au moins  une ligne et une colonne
     * - ou elle est vide (0x0), ce qui est aussi considéré comme valide
     * 
     * @param matrice la chaîne représentant la matrice (chaque ligne se termine par '\n' sauf la dernière éventuellement)
     * @return true si la matrice est valide, false sinon
     */
    public static boolean matriceValide(String matrice) {
        // Cas particulier : une chaîne vide correspond à une matrice 0x0, considérée comme valide
        if (matrice.isEmpty()) {
            return true;
        }
        int lignes = 0; // Nombre total de lignes
        int colonneActuelle = 0; // Compte le nombre de carctère dans la ligne en cours
        int colonnes = -1; // Nombre de colonnes attendu (déterminé à la première ligne)
        
        for (int i = 0; i < matrice.length(); i++) {
            char c = matrice.charAt(i);
            if (c == '\n') {
                // Vérifier que sa longueur correspond a celle des autres lignes
                if (colonnes == -1) {
                    colonnes = colonneActuelle; // Première ligne, on fixe la taille de référence
                } else if (colonnes != colonneActuelle) {
                    return false; // Lignes de taille différentes => matrice non rectangulaire
                }
                colonneActuelle = 0; // Réinitialise le compteur pour la ligne suivante
                lignes++;
            } else {
                colonneActuelle++; // Caractère d'une ligne
            }
        }

        // dernière ligne si elle ne se termine pas par un '\n'
        if (colonneActuelle > 0) {
            if (colonnes == -1) {
                colonnes = colonneActuelle; // Cas où il n'y a qu'une seule ligne sans '\n'
            } else if (colonnes != colonneActuelle) {
                return false; // Taille incohérente avec les autres lignes
            }
            lignes++;
        }

        // Une matrice est valide si elle a au moins une ligne et une colonne
        return lignes > 0 && colonnes > 0;
    }

    /**
     * Convertit une chaîne de cractères représentant une matrice en une matrice de caractères (tableau 2D)
     * Chaque ligne est séparé par un caractère de saut de ligne '\n'
     * 
     * Exemple :
     * la chaîne de caractères "AAB\nCCD\nEEF" sera représentée par :
     * 
     * AAB
     * CCD
     * EEF
     * 
     * @param texte la chaine représentant la matrice, chaque ligne étant séparé par '\n'
     * @return une matrice de caractères à deux dimensions correspondant à la chaîne d'entrée
     * 
     * prec : matriceValide(texte) == true
     */
    public static char[][] convertirEnMatrice(String texte) {
        // Découpe la chaîne en ligne en utilisant le saut de ligne comme séparateur
        String[] lignes = texte.split("\n");

        // Création de la matrice avec le bon nombre de lignes et colonnes
        char[][] matrice = new char[lignes.length][lignes[0].length()];

        // Remplissage de la matrice ligne par ligne
        for (int i = 0; i < lignes.length; i++) {
            matrice[i] = lignes[i].toCharArray(); // Conversion directe de la ligne en tableau de caractères
        }

        return matrice; // Renvoie la matrice construite
    }

    /**
     * Détermine si un mot est un palindrome
     * Un palindrome est un mot qui se lit de la même manière de gauche à droite et de droite à gauche
     * 
     * Exemples :
     * "RADAR" => true
     * "KAYAK" => true
     * "HELLO" => false
     * 
     * @param mot le mot à tester
     * @return true si le mot est un palindrome, false sinon
     */
    public static boolean estUnPalindrome(String mot) {
        // Parcours du mot jusqu'à la moitié
        for (int i = 0; i < mot.length()/2; i++) {
            // Comprare le caractère de gauche avec son symétrique à droite
            if (mot.charAt(i) != mot.charAt(mot.length()-1 -i)) {
                return false; // Dès qu'une différence est trouvée, ce n'est pas un palindrome
            }
        }
        // Si aucun différence n'est trouvée, le mot est un palindrome
        return true;
    }

    /**
     * Compte le nombre d'occurences d'un mot (aiguille) dans une matrice de caractères
     * La recherche s'effectue dans toutes les directions possibles (8 directions autour de chaque case)
     * 
     * @param matrice la matrice composée de caractères
     * @param aiguille le mot à cherhcer
     * @return le nombre d'ocurrence du mot dans la matrice
     */
    public static int compterOccurrences(char[][] matrice, String aiguille) {
        int compteur = 0;
        int longueur = aiguille.length();
        int lignes = matrice.length;
        int colonnes = matrice[0].length;

        // Cas particulier pour une aiguille d'une seule lettre :
        // On compte simplement le nombre d'occurence de ce caractère dans la matrice
        if (longueur == 1) {
            for (char[] ligne : matrice) {
                for (char c : ligne) {
                    if (c == aiguille.charAt(0)) {
                        compteur++;
                    }
                }
            }
            return compteur;
        }

        // Déplacements possibles dans les 8 directions : 
        // Les 4 premières directions (droite, bas, diagonale bas-droite, diagonale bas-gauche) suffisent
        // pour rechercher les palindromes car lire un palindrome dans la direction opposée donne le même mot
        // Cela permet d'éviter de compter deux fois la même occurencedans les directions inverses
        // Les 4 directions restantes sont nécessaires pour traiter les mots non-palindromes
        int[] dx = {1, 0, 1, -1, -1, 0, 1, -1}; // Déplacements horizontaux
        int[] dy = {0, 1, 1, 1, 0, -1, -1, -1}; // Déplacements verticaux

        // Parcours de toute la matrice
        for (int y = 0; y < lignes; y++) { // Lignes
            for (int x = 0; x < colonnes; x++) { // Colonnes
                // Pour chaque direction possible
                for (int k = 0; k < (estUnPalindrome(aiguille) && longueur > 1 ? 4 : 8); k++) { // Pour les palindrome, on ne parcourt que 4 directions
                    // On calcul ici l'indice de la dernière lettre si on allait dans cette direction
                    int xFin = x + (longueur - 1) * dx[k];
                    int yFin = y + (longueur - 1) * dy[k];

                    // Si ce point final est en dehors de la matrice, inutile de tester
                    if (xFin < 0 || xFin >= colonnes || yFin < 0 || yFin >= lignes) continue;

                    if (motPresent(matrice, aiguille, x, y, dx[k], dy[k])) {
                        compteur++;
                    }
                }
            }
        }
        return compteur;
    }

    /**
     * Vérifie si un mot est présent dans la matrice à partir d'une position donnée et dans une direction donnée
     * 
     * @param matrice la matrice de caractères
     * @param aiguille le mot à chercher
     * @param x la position de départ abscisse (colonne)
     * @param y la position de départ en ordonnée (ligne)
     * @param dx le déplavement horizontal à chaque lettre (direction en x)
     * @param dy le déplacement vertical à chaque lettre (direction en y)
     * @return true si le mot est présent dans cette direction depuis cette position, false sinon
     */
    public static boolean motPresent(char[][] matrice, String aiguille, int x, int y, int dx, int dy) {
        int lignes = matrice.length;
        int colonnes = matrice[0].length;
        int longueur = aiguille.length();

        // Coordonnées du dernier caractère du dernier mot à lire
        int xFin = x + (longueur-1) * dx;
        int yFin = y + (longueur-1) * dy;

        // Vérifie que les coordonnées finales ne sortent pas de la matrice
        if (xFin < 0 || xFin >= colonnes || yFin < 0 || yFin >= lignes) {
            return false;
        }

        // Parcours du mot caractère par caractère dans la direction donnée
        for (int i = 0; i < longueur; i++) {
            int xi = x + i * dx; // Colonne courante
            int yi = y + i * dy; // Ligne courante
            if (matrice[yi][xi] != aiguille.charAt(i)) {
                return false; // Le mot n'est pas présent dans cette direction
            }
        }

        // Tous les caractères correspondent
        return true;
    }
}
