package iut.sae.algo.sobriete.etu84;

public class Recherche {

    public static int chercheMot(String matrice, String mot) {
        if (matrice == null || mot == null || mot.length() == 0)
            return -1;

        String[] lignes = decouperTexteEnLignes(matrice);
        int nombreLignes = lignes.length;
        if (nombreLignes == 0)
            return 0;
        int nombreColonnes = lignes[0].length();

        for (int i = 0; i < nombreLignes; i++) {
            if (lignes[i].length() != nombreColonnes)
                return -1;
        }

        int tailleMot = mot.length();

        if (tailleMot == 1) {
            char caractereRecherche = mot.charAt(0);
            int compteur = 0;
            for (int i = 0; i < nombreLignes; i++) {
                for (int j = 0; j < nombreColonnes; j++) {
                    if (lignes[i].charAt(j) == caractereRecherche)
                        compteur++;
                }
            }
            return compteur;
        }

        boolean motEstPalindrome = true;
        for (int i = 0; i < tailleMot / 2; i++) {
            if (mot.charAt(i) != mot.charAt(tailleMot - 1 - i)) {
                motEstPalindrome = false;
                break;
            }
        }

        int[][] directions = { { 0, 1 }, { 1, 0 }, { 1, 1 }, { 1, -1 } };
        int totalOccurrences = 0;

        for (int ligne = 0; ligne < nombreLignes; ligne++) {
            for (int colonne = 0; colonne < nombreColonnes; colonne++) {
                for (int d = 0; d < directions.length; d++) {
                    int deplacementLigne = directions[d][0];
                    int deplacementColonne = directions[d][1];
                    if (motPresentALaPosition(lignes, ligne, colonne, deplacementLigne, deplacementColonne, mot))
                        totalOccurrences++;
                    if (!motEstPalindrome && motPresentALaPosition(lignes, ligne, colonne, -deplacementLigne,
                            -deplacementColonne, mot))
                        totalOccurrences++;
                }
            }
        }
        return totalOccurrences;
    }

    private static String[] decouperTexteEnLignes(String texte) {
        int tailletexte = texte.length();
        int nombreLignes = 1;
        for (int i = 0; i < tailletexte; i++) {
            if (texte.charAt(i) == '\n')
                nombreLignes++;
        }
        String[] lignes = new String[nombreLignes];
        int debut = 0, indexLigne = 0;
        for (int i = 0; i < tailletexte; i++) {
            if (texte.charAt(i) == '\n') {
                int fin = i;
                if (fin > debut && texte.charAt(fin - 1) == '\r')
                    fin--;
                lignes[indexLigne] = extraireSousChaine(texte, debut, fin);
                indexLigne++;
                debut = i + 1;
            }
        }
        lignes[indexLigne] = extraireSousChaine(texte, debut, texte.length());
        return lignes;
    }

    private static String extraireSousChaine(String chaine, int debut, int fin) {
        char[] tableauCaracteres = new char[fin - debut];
        for (int i = debut; i < fin; i++) {
            tableauCaracteres[i - debut] = chaine.charAt(i);
        }
        return new String(tableauCaracteres);
    }

    private static boolean motPresentALaPosition(String[] lignes, int ligneDepart, int colonneDepart,
            int deplacementLigne, int deplacementColonne, String mot) {
        int nombreLignes = lignes.length;
        int nombreColonnes = lignes[0].length();
        int tailleMot = mot.length();

        int ligneFin = ligneDepart + deplacementLigne * (tailleMot - 1);
        int colonneFin = colonneDepart + deplacementColonne * (tailleMot - 1);

        if (ligneFin < 0 || ligneFin >= nombreLignes || colonneFin < 0 || colonneFin >= nombreColonnes)
            return false;

        for (int k = 0; k < tailleMot; k++) {
            int ligneCourante = ligneDepart + deplacementLigne * k;
            int colonneCourante = colonneDepart + deplacementColonne * k;
            if (lignes[ligneCourante].charAt(colonneCourante) != mot.charAt(k))
                return false;
        }
        return true;
    }
}
