/*******************************************************
 CS4551 Multimedia Software Systems
 @ Author: Elaine Kang
 *******************************************************/
import java.util.Scanner;
//
// Template Code - demonstrate how to use Image class

public class CS4551_Kan
{
  public static void main(String[] args)
  {
    Image img,img2,img3,img4;
    String imageName;
    int choice = 0;
    boolean running=true;
    Scanner input = new Scanner(System.in);

	// if there is no commandline argument, exit the program
    if(args.length != 1)
    {
      usage();
      System.exit(1);
    }
   
    imageName=args[0].substring(0,args[0].lastIndexOf("."));
    
    // Create an Image object with the input PPM file name.
    // Display it and write it into another PPM file.
    while(running)
    {
      System.out.println("\nMain Menu -----------------------------");
      System.out.println("1. Conversion to Gray-scale Image (24bits->8bits)");
      System.out.println("2. Conversion to N-level Image");
      System.out.println("3. Conversion to 8bit Indexed Color Image using Uniform Color Quantization (24bits->8bits)");
      System.out.println("4. Quit \n");
      System.out.print("Please enter the task number [1-4]: ");
      choice= input.nextInt();

// MENU BEGINS
    switch(choice){

// 1 GRAYSCALE      
      case 1: 
        img = new Image(args[0]);
        img.convertGrayscale();
        img.display();
        img.write2PPM(imageName+"-gray.ppm");
      break;

// 2 N - LEVEL IMAGE
      case 2:
      choice=0;

      while(!(choice==2||choice==4||choice==8||choice==16)){
        System.out.print("Please enter the N-level(2,4,8,16): ");
        choice= input.nextInt();
      }

      // img = new Image(args[0]); // Thresholding 
      // img.convertGrayscale();
      // img.convertThresholding(choice);
      // img.display();
      // img.write2PPM(imageName+"-threshholding-"+choice+"level.ppm");
      
      img2 = new Image(args[0]); // Error Diffusion
      img2.convertGrayscale();
      img2.displayPixelValue(95, 164);
      img2.convertErrorDiffusion(choice);
      img2.displayPixelValue(95, 164);
      img2.display();
      img2.write2PPM(imageName+"-errorDiffusion-"+choice+"level.ppm");

      break;

// UNIFORM COLOR QUANTIZATION
      case 3:
       img3 = new Image(args[0]);
       img3.convertToEightBits();
       img3.display();
       img3.write2PPM(imageName+"-index.ppm");

       img4 = new Image(args[0]);
       img4.convertToEightBits();
       img4.generateLookupTable();
       img4.convertUniformCQuantization();
       img4.display();
       img4.write2PPM(imageName+"-QT8.ppm");
      break;

// QUIT     
      case 4: 
        running=false;
      break;
      default:
        System.out.println("Invalid input.");
      break;
       } // SWITCH STATEMENT


    } 
// MENU ENDS


    if(input!=null){
      input.close(); // Closes scanner
    }

    System.out.println("Thank you for using this program!");
  }

  public static void clearScreen(){
            System.out.println("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");
  }

  public static void usage()
  {
    System.out.println("\nUsage: java CS4551_Main [input_ppm_file]\n");
  }
}