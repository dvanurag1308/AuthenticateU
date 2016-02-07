/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackathon;
import java.awt.*;  
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;  
import java.awt.image.DataBufferByte;  
import java.io.File;
import java.text.SimpleDateFormat;
import static java.time.Clock.system;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;  
import org.opencv.core.Core;  
import org.opencv.core.Mat;  
import org.opencv.core.MatOfRect;  
import org.opencv.core.Point;  
import org.opencv.core.Rect;  
import org.opencv.core.Scalar;  
import org.opencv.core.Size;  
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;  
import org.opencv.objdetect.CascadeClassifier;  
import org.opencv.videoio.VideoCapture;
import javax.imageio.ImageIO;
import java.util.*;
/**
 *
 * @author Nanditha
 */
public class Hackathon extends JPanel{

     private static final long serialVersionUID = 1L;  
      public static BufferedImage image1;
      public static String color;

    
      
      public Hackathon(){  
           super();   
      }  
      
      
          public boolean MatToBufferedImage(Mat matBGR){  
           long startTime = System.nanoTime();  
           int width = matBGR.width(), height = matBGR.height(), channels = matBGR.channels() ;  
           byte[] sourcePixels = new byte[width * height * channels];  
           matBGR.get(0, 0, sourcePixels);  
           // create new image and get reference to backing data  
           image1 = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);  
           final byte[] targetPixels = ((DataBufferByte) image1.getRaster().getDataBuffer()).getData();  
           System.arraycopy(sourcePixels, 0, targetPixels, 0, sourcePixels.length);  
           long endTime = System.nanoTime();  
           //System.out.println(String.format("Elapsed time: %.2f ms", (float)(endTime - startTime)/1000000));  
           return true;  
      }  
     @Override
      public  void paintComponent(Graphics g){  
           super.paintComponent(g);   
           if (Hackathon.image1==null) return;  
            g.drawImage(this.image1,10,10,this.image1.getWidth(),this.image1.getHeight(), null);  
           //g.drawString("This is my custom Panel!",10,20);  
            
      }   
      public static String printPixelARGB(int pixel) {
            int alpha = (pixel >> 24) & 0xff;
            int red = (pixel >> 16) & 0xff;
            int green = (pixel >> 8) & 0xff;
            int blue = (pixel) & 0xff;
            //System.out.println("argb: " + alpha + ", " + red + ", " + green + ", " + blue);
           /* List<Integer> pixelcolors = new ArrayList<>();
            pixelcolors.add(red);
            pixelcolors.add(green);
            pixelcolors.add(blue);*/
            color = colorChecker(red,green,blue);
            return color;
     }
     public static String marchThroughImage(BufferedImage image){
         int w = image.getWidth();
         int h = image.getHeight();
         int pixel =0;
         System.out.println("width, height: " + w + ", " + h);
         for (int i = 0; i < h; i++) {
             for (int j = 0; j < w; j++) {
                 //System.out.println("x,y: " + j + ", " + i);
                 pixel = image.getRGB(j, i);
                 
             }
         }
         color = printPixelARGB(pixel);
         return color;
     }
    public static String colorChecker(int red, int green, int blue){
      
        Color colour = new Color(red,green,blue);
      
        System.out.println((colour).toString());
        String color1 = colour.toString();
        System.out.println(color1);
        
        if((red>green && red >blue)){
            if(red>20 && red<=230) {color = "Red";}
            else if(red>230){color = "Red";}
        }
        else if((green>red && green>blue)){
            if(green>20 && green<=230) {color = "Green";}
            else if(green>230){color = "Green";}
        }
        else if((blue>red && blue>green)){
            if(blue>20 && blue<=230) {color = "Blue";}
            else if(blue>230){color = "Blue";}
        } 
        else{
        if(red>230 && green>230 && blue>230){color = "White";}
        else if(red<20 && green<20 && blue<20){color = "Black";}
            }
        
      System.out.println("Color is:"+color);
      return color;
       
    }
 
    public static void main(String[] args) {
        // TODO code application logic here
        
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String date;
        int count =0;
        //Hackathon ha = new Hackathon();
        String window_name = "Capture - Cards"; 
        JFrame frame = new JFrame(window_name);  
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
        frame.setSize(400,400);  
        Hackathon jpanel = new Hackathon();  
        frame.setContentPane(jpanel);       
        frame.setVisible(true);        
        Mat picture=new Mat(); 
    	VideoCapture cameo =new VideoCapture(0);   
        if( cameo.isOpened())  
           {  
            while( true )  
            {  
                 cameo.read(picture);  
              if( !picture.empty() )  
               {   
                    frame.setSize(picture.width()+40,picture.height()+50);  
                     
                    jpanel.MatToBufferedImage(picture);
                     
                    jpanel.repaint();
                    if(count <1){
                    //date = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss").format(new Date());
                    date = "image.png";
                    Imgcodecs.imwrite(date, picture);
                    count++;
                    }    
                    marchThroughImage(image1);
                    cameo.release();
                    
               }
            }
           }
         
    }
    
   
}
    
