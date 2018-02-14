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
    Image img;
    int choice = 0;
    boolean running=true;
    Scanner input = new Scanner(System.in);

	// if there is no commandline argument, exit the program
    if(args.length != 1)
    {
      usage();
      System.exit(1);
    }
   
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
        img.write2PPM("out.ppm");
      break;

// 2 N - LEVEL IMAGE
      case 2: 
      img = new Image(args[0]);
      img.convertGrayscale();
      img.display();
      img.write2PPM("out.ppm");
      break;

// UNIFORM COLOR QUANTIZATION
      case 3:
        System.out.println("Hello");
      break;

// QUIT     
      case 4: 
        running=false;
      break;
      default:
        System.out.println("Invalid input.");
      break;
       } // SWITCH STATEMENT
       clearScreen();

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