package iut.sae.algo;

import org.junit.Test;
import junit.framework.TestCase;

//Ligne d'import à faire varier selon le type d'algorithme
import iut.sae.algo.simplicite.Recherche;

public class SimpliciteRechercheTest extends TestCase{
    
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
          String foin="*****\n" + //
                      "*SAE*\n" + //
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
          int attendu=16;
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
}
