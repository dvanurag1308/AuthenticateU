
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
 import java.awt.image.BufferedImage;  
 import java.awt.image.DataBufferByte;  
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
 import javax.swing.*;  
 import org.opencv.core.*; 
import org.opencv.imgcodecs.Imgcodecs;
 import org.opencv.imgproc.Imgproc;  
 import org.opencv.objdetect.CascadeClassifier;  
import org.opencv.videoio.VideoCapture;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.imageio.ImageIO;
import java.lang.*;

  
 class FaceDetector extends JPanel{  
   
      private static final long serialVersionUID = 1L;  
      private BufferedImage image;  
      // Create a constructor method  
      public FaceDetector(){  
           super();   
      }  
      /**  
       * Converts/writes a Mat into a BufferedImage.  
       *   
       * @param matrix Mat of type CV_8UC3 or CV_8UC1  
       * @return BufferedImage of type TYPE_3BYTE_BGR or TYPE_BYTE_GRAY  
       */  
      public boolean MatToBufferedImage(Mat matBGR){  
           long startTime = System.nanoTime();  
           int width = matBGR.width(), height = matBGR.height(), channels = matBGR.channels() ;  
           byte[] sourcePixels = new byte[width * height * channels];  
           matBGR.get(0, 0, sourcePixels);  
           // create new image and get reference to backing data  
           image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);  
           final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();  
           System.arraycopy(sourcePixels, 0, targetPixels, 0, sourcePixels.length);  
           long endTime = System.nanoTime();  
           System.out.println(String.format("Elapsed time: %.2f ms", (float)(endTime - startTime)/1000000));  
           return true;  
      }  
      public void paintComponent(Graphics g){  
           super.paintComponent(g);   
           if (this.image==null) return;  
            g.drawImage(this.image,10,10,this.image.getWidth(),this.image.getHeight(), null);  
           //g.drawString("This is my custom Panel!",10,20);  
            
      }                

 }  

   

 class processor {  
      public CascadeClassifier face_cascade;
      // Create a constructor method  
      public processor(){  
           face_cascade=new CascadeClassifier("C:/Users/Srinath/opencv/build/etc/haarcascades/haarcascade_eye.xml");  
           if(face_cascade.empty())  
           {  
                System.out.println("--(!)Error loading A\n");  
                 return;  
           }  
           else  
           {  
                      System.out.println("Face classifier loooaaaaaded up");  
           }  
      }  
      public Mat detect(Mat inputframe){  
           Mat mRgba=new Mat();  
           Mat mGrey=new Mat();  
           MatOfRect faces = new MatOfRect();  
           inputframe.copyTo(mRgba);  
           inputframe.copyTo(mGrey);  
           Imgproc.cvtColor( mRgba, mGrey, Imgproc.COLOR_BGR2GRAY);  
           Imgproc.equalizeHist( mGrey, mGrey );  
           face_cascade.detectMultiScale(mGrey, faces);  
           System.out.println(String.format("Detected %s faces", faces.toArray().length));  
          for(Rect rect:faces.toArray())  
           {  
                Point center= new Point(rect.x + rect.width*0.5, rect.y + rect.height*0.5 );
                Imgproc.ellipse( mRgba, center, new Size( rect.width*0.5, rect.height*0.5), 0, 0, 360, new Scalar( 255, 0, 255 ), 4, 8, 0 );  
           } 
           return mRgba;  
      }  
 }  
 public class window extends processor{  
     static String date;
     static int count = 0;
    static public CascadeClassifier face_cascader;
    
    public static String returnHex(byte[] inBytes) throws Exception {
        String hexString = null;
        for (int i=0; i < inBytes.length; i++) { //for loop ID:1
            hexString +=
            Integer.toString( ( inBytes[i] & 0xff ) + 0x100, 16).substring( 1 );
        }                                   // Belongs to for loop ID:1
    return hexString;
  }  
    
      public static void main(String arg[]) throws IOException, NoSuchAlgorithmException, Exception{  
       // Load the native library.  
       System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
       face_cascader=new CascadeClassifier("C:/Users/Srinath/opencv/build/etc/haarcascades/haarcascade_eye.xml");  
       String window_name = "Capture - Face detection";  
       JFrame frame = new JFrame(window_name);  
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    frame.setSize(400,400);  
    processor my_processor=new processor();  
    FaceDetector my_panel = new FaceDetector();  
    frame.setContentPane(my_panel);       
    frame.setVisible(true);        
      
       //-- 2. Read the video stream 
    File input = new File("capi.png");
                    BufferedImage image = ImageIO.read(input);
                    ByteArrayOutputStream output = new ByteArrayOutputStream();
                    ImageIO.write(image,"png",output);
                    System.out.println("Start MD5 Digest hi");
                    byte[] data = output.toByteArray();
                    for(byte i : data){
                        System.out.print(i);
                    }
                     
                    System.out.println("one");
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    md.update(data);
                    byte[] hash = md.digest();
                    //System.out.println(returnHex(hash));
                    //System.out.println("old hash:");
                    for(byte i : hash){
                       // System.out.print(i);
                    }
                    //System.out.println(returnHex(hash));
                    //System.out.println(hash.toString()+" saved image");
         
                    
        Mat webcam_image=new Mat();  
        VideoCapture capture =new VideoCapture(0);
    
    if( capture.isOpened())  
           {  
            while( true && count <10)  
            {  
                 capture.read(webcam_image);  
              if( !webcam_image.empty() )  
               {   
                    frame.setSize(webcam_image.width()+40,webcam_image.height()+60);  
                    //-- 3. Apply the classifier to the captured image  
                    webcam_image=my_processor.detect(webcam_image);
        
                   //-- 4. Display the image  
                    my_panel.MatToBufferedImage(webcam_image);
                    // We could look at the error...  
                    my_panel.repaint();
                    count++;
                 
                    Imgcodecs.imwrite("capture.png", webcam_image);
                    File input1 = new File("capture.png");
                    BufferedImage image1 = ImageIO.read(input1);
                    ByteArrayOutputStream output1 = new ByteArrayOutputStream();
                    ImageIO.write(image1,"png",output1);
                    byte[] data1 = output.toByteArray();
                    System.out.println("new");
                    for(byte k : data1){
                        System.out.print(k);
                    }
                    //System.out.println("Start MD5 Digest");
                    MessageDigest md1 = MessageDigest.getInstance("MD5");
                    md1.update(data1);
                    byte[] hash1 = md1.digest();
                    System.out.println(returnHex(hash1));
                    //System.out.println("gen hash:");
                    for(byte k : hash1){
                        //System.out.print(k);
                    }
                    //System.out.println(hash1.toString()+" new image");
                    if(Arrays.toString(hash) == null ? hash1.toString() == null : hash.toString().equals(hash1.toString()))
                    {
                    
                   //System.out.println("match");
                    }
                 
                    
       
                     }
                    
            }}
                    
                    
                    /*my_panel.addMouseListener(new MouseAdapter() {
                   
                    public void mousePressed(MouseEvent e) {
                       
                     capture.release();
            
                    System.out.println("Hi");
                    
                }
                
                    }
                   
                    );
                    
            } 
               }  */
               else  
               {   
                    System.out.println(" --(!) No captured frame -- Break!");   
                     
               } 
            }

    
 }
             //return;  
        


