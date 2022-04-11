package walmart;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class AppTest {
    int testFileNum = 5;
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
    public void endToEndTest() throws IOException {
       for(int i = 0;i < testFileNum;i++) {
           String testFilePath = "src/test/resources/inputTest" + i + ".txt";
           File file = new File(testFilePath);
           String path = file.getAbsolutePath();
           App app = new App();
           app.main(new String[]{path});
       }
       for(int i = 0;i < testFileNum;i++){
           String testFileResultFilePath = "src/test/resources/inputTest" + i + "_output.txt";
           String expectedResultFilePath = "src/test/resources/expectedOutcomes/inputTest" + i + "_output.txt";
           File testOutputFile = new File(testFileResultFilePath);
           File expectedOutputFile = new File(expectedResultFilePath);
           InputStream testOutputInputStream = new FileInputStream(testOutputFile);
           InputStream expectedInputStream = new FileInputStream(expectedOutputFile);
           byte[] testOutputBytes = testOutputInputStream.readAllBytes();
           byte[] expectedOutputBytes = expectedInputStream.readAllBytes();
           assertEquals(expectedOutputBytes.length,testOutputBytes.length);
           for(int index = 0;index < testOutputBytes.length;index++){
               assertEquals(expectedOutputBytes[index],testOutputBytes[index]);
           }
       }
    }

}