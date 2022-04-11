package walmart;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
class AppTest {

   @Test
   public void testCoordinate(){
       Coordinate coordinate1 = new Coordinate(0,0);
       assertEquals("A1",coordinate1.toString());
       Coordinate coordinate2 = new Coordinate(9,19);
       assertEquals("J20",coordinate2.toString());
   }

   @Test
   public void testValidRequest(){
       String requestString = "R001 2";
       Request request = new Request(requestString);
       assertEquals(2,request.guestNum);
       assertEquals(1,request.seqNum);
   }

   @Test
   public void testInvalidRequest(){
       String invalidRequestString = "R001 2 2";
       assertThrows(IllegalArgumentException.class,()-> new Request(invalidRequestString));
   }

    @Test
    public void endToEndTest(){
       for(int i = 0;i < 5;i++) {
           String testFilePath = "src/test/resources/inputTest" + i + ".txt";
           File file = new File(testFilePath);
           String path = file.getAbsolutePath();
           App app = new App();
           app.main(new String[]{path});
       }
    }

}