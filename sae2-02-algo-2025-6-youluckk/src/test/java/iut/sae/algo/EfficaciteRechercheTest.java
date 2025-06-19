package iut.sae.algo;

import org.junit.Test;
import junit.framework.TestCase;
import java.util.Random;

//Ligne d'import à faire varier selon le type d'algorithme
import iut.sae.algo.efficacite.Recherche;

public class EfficaciteRechercheTest extends TestCase{
    
    // Tests originaux conservés
    @Test
    public void testErreurAiguille(){
         assertEquals(-1, Recherche.chercheMot("", null));
    }

    @Test
    public void testErreurBotte(){
         assertEquals(-1, Recherche.chercheMot(null, "IUT"));
    }

    @Test
    public void testErreurBotteIrreguliere(){
         String botte = "ABC\nXXXX\nEFG";
         assertEquals(-1, Recherche.chercheMot(botte, "IUT"));
    }

    @Test
    public void testHorizontal(){
         String aiguille="IUT";
         String foin="IUT";
         int attendu=1;
         assertEquals(attendu, Recherche.chercheMot(foin, aiguille));
    }

    @Test
    public void testHorizontalInverse(){
         String aiguille="IUT";
         String foin="TUI";
         int attendu=1;
         assertEquals(attendu, Recherche.chercheMot(foin, aiguille));
    }

    @Test
    public void testVertical(){
         String aiguille="IUT";
         String foin="I\nU\nT";
         int attendu=1;
         assertEquals(attendu, Recherche.chercheMot(foin, aiguille));
    }

    @Test
    public void testVerticalInverse(){
         String aiguille="IUT";
         String foin="T\nU\nI";
         int attendu=1;
         assertEquals(attendu, Recherche.chercheMot(foin, aiguille));
    }

    @Test
    public void testPremiereDiagonale(){
         String aiguille="IUT";
         String foin="I..\n"+
                     ".U.\n"+
                     "..T";
         int attendu=1;
         assertEquals(attendu, Recherche.chercheMot(foin, aiguille));
    }

    @Test
    public void testPremiereDiagonaleInverse(){
         String aiguille="IUT";
         String foin="T..\n"+
                     ".U.\n"+
                     "..I";
         int attendu=1;
         assertEquals(attendu, Recherche.chercheMot(foin, aiguille));
    }

    @Test
    public void testSecondeDiagonale(){
         String aiguille="IUT";
         String foin="..I\n"+
                     ".U.\n"+
                     "T..";
         int attendu=1;
         assertEquals(attendu, Recherche.chercheMot(foin, aiguille));
    }

    @Test
    public void testSecondeDiagonaleInverse(){
         String aiguille="IUT";
         String foin="..T\n"+
                     ".U.\n"+
                     "I..";
         int attendu=1;
         assertEquals(attendu, Recherche.chercheMot(foin, aiguille));
    }

    @Test
    public void testVide(){
         String aiguille="IUT";
         String foin="";
         int attendu=0;
         assertEquals(attendu, Recherche.chercheMot(foin, aiguille));
    }

    @Test
    public void testSansAiguille(){
         String aiguille="IUT";
         String foin="ABC\nDEF\nGHI";
         int attendu=0;
         assertEquals(attendu, Recherche.chercheMot(foin, aiguille));
    }

    @Test
    public void testMultiligneSimple(){
         String aiguille="SAE";
         String foin="*****\n" + 
                     "*SAE*\n" + 
                     "*****";
         int attendu=1;
         assertEquals(attendu, Recherche.chercheMot(foin, aiguille));
    }

    @Test
    public void testAiguilleSimple(){
         String aiguille="A";
         String foin="AAA\n"+
                     "AAA\n"+
                     "AAA";
         int attendu=9;
         assertEquals(attendu, Recherche.chercheMot(foin, aiguille));
    }
    
    @Test
    public void testAiguilleIncomplete(){
         String aiguille="AA";
         String foin="AAA\n"+
                     "AAA\n"+
                     "AAA";
         int attendu=20;
         assertEquals(attendu, Recherche.chercheMot(foin, aiguille));
    }
    
    @Test
    public void testAiguilleUnique(){
         String aiguille="AAA";
         String foin="AAA\n"+
                     "AAA\n"+
                     "AAA";
         int attendu=8;
         assertEquals(attendu, Recherche.chercheMot(foin, aiguille));
    }
    
     @Test
     public void testTempsGrandeMatrice(){
     // Matrice 100x100 avec le mot "TEST" placé aléatoirement
     String foin = creerMatriceAvecMot(100, 100, "TEST");
     String aiguille = "TEST";
     
     long debut = System.nanoTime();
     int resultat = Recherche.chercheMot(foin, aiguille);
     long fin = System.nanoTime();
     
     long tempsNano = fin - debut;
     long tempsMicro = tempsNano / 1000;
     System.out.println("Temps grande matrice: " + tempsNano + " ns (" + tempsMicro + " micro seconde)");
     System.out.println("Occurrences trouvees: " + resultat);
     
     }

     @Test
     public void testTempsTresGrandeMatrice(){
     String foin = creerMatriceAvecMot(500, 500, "TEST");
     String aiguille = "TEST";
     
     long debut = System.nanoTime();
     int resultat = Recherche.chercheMot(foin, aiguille);
     long fin = System.nanoTime();
     
     long tempsNano = fin - debut;
     long tempsMicro = tempsNano / 1000;
     long tempsMs = tempsNano / 1_000_000;
     System.out.println("Temps tres grande matrice: " + tempsNano + " ns (" + tempsMicro + " micro seconde, " + tempsMs + " ms)");
     System.out.println("Occurrences trouvees: " + resultat);
     }
    
    @Test
    public void testMemoireGrandeMatrice(){
        Runtime rt = Runtime.getRuntime();
        rt.gc(); 
        
        long avant = rt.totalMemory() - rt.freeMemory();
        String foin = creerMatriceAvecMot(200, 200, "TEST");
        Recherche.chercheMot(foin, "TEST");
        long apres = rt.totalMemory() - rt.freeMemory();
        
        long memoire = (apres - avant) / 1024; 
        System.out.println("Memoire grande matrice: " + memoire + " KB");
    }
    
    @Test
    public void testMemoireTresGrandeMatrice(){
        Runtime rt = Runtime.getRuntime();
        rt.gc();
        
        long avant = rt.totalMemory() - rt.freeMemory();
        String foin = creerMatriceAvecMot(300, 300, "TEST");
        Recherche.chercheMot(foin, "TEST");
        long apres = rt.totalMemory() - rt.freeMemory();
        
        long memoire = (apres - avant) / 1024; //KB
        System.out.println("Memoire tres grande matrice: " + memoire + " KB");
    }
    
     private String creerMatriceAvecMot(int lignes, int colonnes, String mot){
          char[][] matrice = new char[lignes][colonnes];
          Random rand = new Random();
          
          // Remplir la matrice avec des caractères aléatoires
          for(int i = 0; i < lignes; i++){
               for(int j = 0; j < colonnes; j++){
                    matrice[i][j] = (char)('A' + rand.nextInt(26));
               }
          }
          
          // Placer le mot horizontalement à une position aléatoire
          if(colonnes >= mot.length()){
               int ligneAleatoire = rand.nextInt(lignes);
               int colAleatoire = rand.nextInt(colonnes - mot.length() + 1);
               
               for(int i = 0; i < mot.length(); i++){
                    matrice[ligneAleatoire][colAleatoire + i] = mot.charAt(i);
               }
          }
          
          // Optionnel : placer le mot verticalement aussi
          if(lignes >= mot.length()){
               int ligneAleatoire = rand.nextInt(lignes - mot.length() + 1);
               int colAleatoire = rand.nextInt(colonnes);
               
               for(int i = 0; i < mot.length(); i++){
                    matrice[ligneAleatoire + i][colAleatoire] = mot.charAt(i);
               }
          }
          
          // Convertir en String
          StringBuilder sb = new StringBuilder();
          for(int i = 0; i < lignes; i++){
               for(int j = 0; j < colonnes; j++){
                    sb.append(matrice[i][j]);
               }
               if(i < lignes - 1) sb.append('\n');
          }
          
          return sb.toString();
     }
}