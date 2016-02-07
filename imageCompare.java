/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackathon;
import static hackathon.Hackathon.image1;
import static hackathon.Hackathon.marchThroughImage;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

/**
 *
 * @author Nanditha
 */
public class imageCompare {
    public static BufferedImage image1;
    
    public static void main(String[] args){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String img_name;
        int count1 =0;
        //Hackathon ha = new Hackathon();
        String window_name = "Capture - Cards"; 
        JFrame frame = new JFrame(window_name);  
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
        frame.setSize(400,400);  
        Hackathon jpanel = new Hackathon();  
        frame.setContentPane(jpanel);       
        frame.setVisible(true);        
        Mat picture1=new Mat(); 
    	VideoCapture cam =new VideoCapture(0);   
        if( cam.isOpened())  
           {  
            while( true )  
            {  
                 cam.read(picture1);  
              if( !picture1.empty() )  
               {   
                    frame.setSize(picture1.width()+40,picture1.height()+50);  
                     
                    jpanel.MatToBufferedImage(picture1);
                     
                    jpanel.repaint();
                    if(count1 <1){
                    //date = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss").format(new Date());
                    img_name = "standard_image.png";
                    Imgcodecs.imwrite(img_name, picture1);
                    count1++;
                    }    
                    String permanentColor = marchThroughImage(image1);
                    cam.release();
                    
               }
            }
           }
    }
}
