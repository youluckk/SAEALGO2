package iut.sae.algo.simplicite.etu21;

public class Recherche {

    public static int chercheMot(String botteDeFoin, String aiguille) {
        // Vérifie si les chaînes sont ni nulles ni vides
        if (botteDeFoin == null || aiguille == null || aiguille.isEmpty()) {
            return -1;
        }
        String[] lignes = botteDeFoin.split("\n");
        int nbLignes = lignes.length;
        int nbColonnes = lignes[0].length();

        // Vérifie que les lignes ont la même longueur
        for (int i = 0; i < lignes.length; i++) {
            if (lignes[i].length() != nbColonnes) return -1;
        }

        // Création de la chaîne inversée qui servira a vérifier dans les deux sens
        String inverse = new StringBuilder(aiguille).reverse().toString();
        boolean inversable = !aiguille.equals(inverse);

        int total = 0;
        
        // Recherche horizontale
        for (String ligne : lignes) {
            total += compteOccurrences(ligne, aiguille);
            if (inversable)
                total += compteOccurrences(ligne, inverse);
        }

        // Recherche verticale
        for (int col = 0; col < nbColonnes; col++) {
            StringBuilder colonne = new StringBuilder();
            for (int row = 0; row < nbLignes; row++) {
                colonne.append(lignes[row].charAt(col));
            }
            String colString = colonne.toString();
            total += compteOccurrences(colString, aiguille);
            if (inversable)
                total += compteOccurrences(colString, inverse);
        }

        // Recherche première diagonale
        for (int k = 0; k <= nbLignes + nbColonnes - 2; k++) {
            StringBuilder diag = new StringBuilder();
            for (int i = 0; i < nbLignes; i++) {
                int j = k - i;
                if (j >= 0 && j < nbColonnes) {
                    diag.append(lignes[i].charAt(j));
                }
            }
            String diagString = diag.toString();
            total += compteOccurrences(diagString, aiguille);
            if (inversable)
                total += compteOccurrences(diagString, inverse);
        }

        // Recherche seconde diagonale
        for (int k = -nbColonnes + 1; k < nbLignes; k++) {
            StringBuilder diag = new StringBuilder();
            for (int i = 0; i < nbLignes; i++) {
                int j = i - k;
                if (j >= 0 && j < nbColonnes) {
                    diag.append(lignes[i].charAt(j));
                }
            }
            String diagString = diag.toString();
            total += compteOccurrences(diagString, aiguille);
            if (inversable)
                total += compteOccurrences(diagString, inverse);
        }

        return total;
    }

    // Méthode pour compter le nombre d'occurences d'un motif dans une chaîne
    private static int compteOccurrences(String texte, String motif) {
        int count = 0;
        for (int i = 0; i <= texte.length() - motif.length(); i++) {
            if (texte.startsWith(motif, i)) {
                count++;
            }
        }
        return count;
    }
}
