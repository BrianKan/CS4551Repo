import java.util.Scanner;

/*******************************************************
 CS4551 Multimedia Software Systems
 @ Author: Elaine Kang
 *******************************************************/

//
// Template Code - demonstrate how to use Image class

public class CS4551_Kan {
    public static void main(String[] args) {

        int choice = 0;
        boolean running = true;
        Scanner input = new Scanner(System.in);


        while (running) {
            System.out.println("\nMain Menu -----------------------------");
            System.out.println("1. Aliasing");
            System.out.println("2. Dictionary Coding");
            System.out.println("3. Quit \n");
            System.out.print("Please enter the task number [1-3]: ");
            choice = input.nextInt();

            switch (choice) {
                case 1:
                    Image img = new Image(512, 512);
                    img.display();
                    img.write2PPM("out.ppm");
                    break;
                case 2:
                    break;
                case 3:
                    if (input != null) {
                        input.close();
                    }
                    System.out.println("--Good Bye--");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid input.");
                    break;
            }

        }
    }

    public static void usage() {
        System.out.println("\nUsage: java CS4551_Kan\n");
    }
}