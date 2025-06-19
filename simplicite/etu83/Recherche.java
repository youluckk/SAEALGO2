package iut.sae.algo.simplicite.etu83;

public class Recherche {

    public static int chercheMot(String botteDeFoin, String aiguille) {

        // vérifie si la botte de foin est nulle
        if (botteDeFoin == null) {
            System.out.println("La botte ne peut pas etre nulle.");
            return -1;
        }

        // vérifie si l'aiguille est nulle
        if (aiguille == null) {
            System.out.println("L'aiguille ne peut pas etre nulle.");
            return -1;
        }

        // vérifie si la longueur de la botte est égale à 0
        if (botteDeFoin.length() == 0) {
            return 0;
        }

        // vérifie si l'aiguille est vide.
        if (aiguille.length() == 0) {
            System.out.println("L'aiguille ne peut pas etre nulle.");
            return 0;
        }

        // vérifie si la longueur de l'aiguille est bien inférieur à la longueur de la botte.
        if (aiguille.length() > botteDeFoin.length()) {
            System.out.println("Erreur, la longueur de l'aiguille doit etre supérieur ou égal a la longueur de la botte.");
            return -1;
        }

        // cas pour les aiguilles de 1 caractere (pour le test : testAiguilleSimple)
        if (aiguille.length() == 1) {
            String[] lignes = botteDeFoin.split("\n");
            int nbOccurences = 0;

            for (int numeroLigne = 0; numeroLigne < lignes.length; numeroLigne++) {
                String ligneCourante = lignes[numeroLigne];
                for (int i = 0; i < ligneCourante.length(); i++) {
                    if (ligneCourante.charAt(i) == aiguille.charAt(0)) {
                        nbOccurences++;
                    }
                }
            }
            return nbOccurences;
        }

        // vérifie que la botte est rectangle
        int longueurLigne = -1;
        int longueurActuelle = 0;

        for (int i = 0; i < botteDeFoin.length(); i++) {
            char c = botteDeFoin.charAt(i);
            if (c == '\n') {
                if (longueurLigne == -1) {
                    longueurLigne =longueurActuelle;
                } else if (longueurActuelle != longueurLigne) {
                    return -1;
                }
                longueurActuelle = 0;
            } else {
                longueurActuelle++;
            }
        }

        if ((longueurLigne != -1) && (longueurActuelle != longueurLigne)) {
            return -1;
        }

        // vérifie si l'aiguille est dans la botte (8 cas possibles):

        // horizontal :

        String[] lignes = botteDeFoin.split("\n");
        int nbOccurences = 0;
        boolean aiguilleDeuxSens = aiguilleDeuxSens(aiguille);

        for (int numeroLigne = 0; numeroLigne < lignes.length; numeroLigne++) {
            String ligneCourante = lignes[numeroLigne];

            for (int i = 0; i <= ligneCourante.length() - aiguille.length(); i++) {
                boolean trouve = true;
                for (int j = 0; j < aiguille.length(); j++) {
                    if (ligneCourante.charAt(i + j) != aiguille.charAt(j)) {
                        trouve = false;
                    }
                }
                if (trouve == true) {
                    nbOccurences++;
                }
            }
        }

        // horizontal inversé :
        if (!aiguilleDeuxSens) {
            for (int numeroLigne = 0; numeroLigne < lignes.length; numeroLigne++) {
                String ligneCourante = lignes[numeroLigne];

                // j'inverse la ligne (dans l'exemple du test TUI devient donc IUT)
                String ligneInversee = "";
                for (int k = ligneCourante.length() - 1; k >= 0; k--) {
                    ligneInversee += ligneCourante.charAt(k);
                }

                for (int i = 0; i <= ligneInversee.length() - aiguille.length(); i++) {
                    boolean trouve = true;
                    for (int j = 0; j < aiguille.length(); j++) {
                        if (ligneInversee.charAt(i + j) != aiguille.charAt(j)) {
                            trouve = false;
                        }
                    }

                    if (trouve) {
                        nbOccurences++;
                    }
                }
            }
        }

        // vertical :

        for (int numeroLigne = 0; numeroLigne < lignes.length; numeroLigne++) {
            for (int position = 0; position < lignes[numeroLigne].length(); position++) {
                if (lignes[numeroLigne].charAt(position) == aiguille.charAt(0)) {
                    if (numeroLigne + aiguille.length() <= lignes.length) {
                        boolean trouve = true;

                        for (int i = 0; i < aiguille.length(); i++) {
                            if (lignes[numeroLigne + i].charAt(position) != aiguille.charAt(i)) {
                                trouve = false;
                            }
                        }

                        if (trouve) {
                            nbOccurences++;
                        }

                    }

                }
            }
        }

        // vertical inversé :
        if (!aiguilleDeuxSens) {

            for (int numeroLigne = 0; numeroLigne < lignes.length; numeroLigne++) {
                for (int position = 0; position < lignes[numeroLigne].length(); position++) {
                    if (lignes[numeroLigne].charAt(position) == aiguille.charAt(aiguille.length() - 1)) {
                        if (numeroLigne + (aiguille.length() - 1) < lignes.length) {
                            boolean trouve = true;
                            for (int i = 0; i < aiguille.length(); i++) {
                                if (lignes[numeroLigne + i].charAt(position) != aiguille
                                        .charAt(aiguille.length() - 1 - i)) {
                                    trouve = false;
                                }
                            }

                            if (trouve) {
                                nbOccurences++;
                            }

                        }

                    }
                }
            }
        }

        // premiere diagonale :
        if (aiguille.length() <= lignes.length && aiguille.length() <= lignes[0].length()) {
            for (int ligne = 0; ligne <= lignes.length - aiguille.length(); ligne++) {
                for (int col = 0; col <= lignes[0].length() - aiguille.length(); col++) {
                    if (lignes[ligne].charAt(col) == aiguille.charAt(0)) {
                        boolean trouve = true;

                        for (int i = 0; i < aiguille.length(); i++) {
                            if (lignes[ligne + i].charAt(col + i) != aiguille.charAt(i)) {
                                trouve = false;
                            }
                        }
                        if (trouve) {
                            nbOccurences++;
                        }
                    }
                }
            }
        }

        // premiere diagonale inversé :

        if (!aiguilleDeuxSens) {
            if (aiguille.length() <= lignes.length && aiguille.length() <= lignes[0].length()) {
                for (int ligne = aiguille.length() - 1; ligne < lignes.length; ligne++) {
                    for (int col = aiguille.length() - 1; col < lignes[0].length(); col++) {
                        boolean trouve = true;
                        for (int i = 0; i < aiguille.length(); i++) {
                            if (lignes[ligne - i].charAt(col - i) != aiguille.charAt(i)) {
                                trouve = false;
                            }
                        }
                        if (trouve) {
                            nbOccurences++;
                        }
                    }
                }
            }
        }

        // deuxieme diagonale :
        if (aiguille.length() <= lignes.length && aiguille.length() <= lignes[0].length()) {
            for (int ligne = 0; ligne <= lignes.length - aiguille.length(); ligne++) {
                for (int col = aiguille.length() - 1; col < lignes[0].length(); col++) {
                    boolean trouve = true;
                    for (int i = 0; i < aiguille.length(); i++) {
                        if (lignes[ligne + i].charAt(col - i) != aiguille.charAt(i)) {
                            trouve = false;
                        }
                    }

                    if (trouve) {
                        nbOccurences++;
                    }
                }
            }
        }

        // deuxieme diagonale inversé :
        if (!aiguilleDeuxSens) {
            if (aiguille.length() <= lignes.length && aiguille.length() <= lignes[0].length()) {
                for (int ligne = aiguille.length() - 1; ligne < lignes.length; ligne++) {
                    for (int col = 0; col <= lignes[0].length() - aiguille.length(); col++) {
                        boolean trouve = true;

                        for (int i = 0; i < aiguille.length(); i++) {
                            if (lignes[ligne - i].charAt(col + i) != aiguille.charAt(i)) {
                                trouve = false;
                            }
                        }
                        if (trouve) {
                            nbOccurences++;
                        }
                    }
                }
            }
        }

        return nbOccurences;
    }

    // cette méthode permet, en cas de mot qui se lit dans les deux sens, d'ignorer les inverses (exemple horizontal inversées) pour éviter les doublons.
    private static boolean aiguilleDeuxSens(String mot) {
        int gauche = 0;
        int droite = mot.length() - 1;

        while (gauche < droite) {
            if (mot.charAt(gauche) != mot.charAt(droite)) {
                return false;
            }
            gauche++;
            droite--;
        }
        return true;
    }

}
