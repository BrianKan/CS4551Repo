/*******************************************************
 CS4551 Multimedia Software Systems
 @ Author: Elaine Kang

 This image class is for a 24bit RGB image only.
 *******************************************************/

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.stream.FileImageInputStream;

// A wrapper class of BufferedImage
// Provide a couple of utility functions such as reading from and writing to PPM file

public class Image
{
  private BufferedImage img;
  private String fileName;			// Input file name
  private int pixelDepth=3;			// pixel depth in byte
  private int[][] lookupTable=new int [256][4];
  public Image(int w, int h)
  // create an empty image with w(idth) and h(eight)
  {
	fileName = "";
	img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	System.out.println("Created an empty image with size " + w + "x" + h);
  }

  public Image(String fn)
  // Create an image and read the data from the file
  {
	  fileName = fn;
	  readPPM(fileName);
	  System.out.println("Created an image from " + fileName+ " with size "+getW()+"x"+getH());
  }

  public int getW()
  {
	return img.getWidth();
  }

  public int getH()
  {
	return img.getHeight();
  }

  public int getSize()
  // return the image size in byte
  {
	return getW()*getH()*pixelDepth;
  }

  public void setPixel(int x, int y, byte[] rgb)
  // set byte rgb values at (x,y)
  {
	int pix = 0xff000000 | ((rgb[0] & 0xff) << 16) | ((rgb[1] & 0xff) << 8) | (rgb[2] & 0xff);
	img.setRGB(x,y,pix);
  }

  public void setPixel(int x, int y, int[] irgb)
  // set int rgb values at (x,y)
  {
	byte[] rgb = new byte[3];

	for(int i=0;i<3;i++)
	  rgb[i] = (byte) irgb[i];

	setPixel(x,y,rgb);
  }

  public void getPixel(int x, int y, byte[] rgb)
  // retreive rgb values at (x,y) and store in the byte array
  {
  	int pix = img.getRGB(x,y);

  	rgb[2] = (byte) pix;
  	rgb[1] = (byte)(pix>>8);
  	rgb[0] = (byte)(pix>>16);
  }


  public void getPixel(int x, int y, int[] rgb)
  // retreive rgb values at (x,y) and store in the int array
  {
	int pix = img.getRGB(x,y);

	byte b = (byte) pix;
	byte g = (byte)(pix>>8);
	byte r = (byte)(pix>>16);

    // converts singed byte value (~128-127) to unsigned byte value (0~255)
	rgb[0]= (int) (0xFF & r);
	rgb[1]= (int) (0xFF & g);
	rgb[2]= (int) (0xFF & b);
  }

  public void displayPixelValue(int x, int y)
  // Display rgb pixel in unsigned byte value (0~255)
  {
	int pix = img.getRGB(x,y);

	byte b = (byte) pix;
	byte g = (byte)(pix>>8);
	byte r = (byte)(pix>>16);

    System.out.println("RGB Pixel value at ("+x+","+y+"):"+(0xFF & r)+","+(0xFF & g)+","+(0xFF & b));
   }

  public void readPPM(String fileName)
  // read a data from a PPM file
  {
	File fIn = null;
	FileImageInputStream fis = null;

	try{
		fIn = new File(fileName);
		fis = new FileImageInputStream(fIn);

		System.out.println("Reading "+fileName+"...");

		// read Identifier
		if(!fis.readLine().equals("P6"))
		{
			System.err.println("This is NOT P6 PPM. Wrong Format.");
			System.exit(0);
		}

		// read Comment line
		String commentString = fis.readLine();

		// read width & height
		String[] WidthHeight = fis.readLine().split(" ");
		int width = Integer.parseInt(WidthHeight[0]);
		int height = Integer.parseInt(WidthHeight[1]);

		// read maximum value
		int maxVal = Integer.parseInt(fis.readLine());

		if(maxVal != 255)
		{
			System.err.println("Max val is not 255");
			System.exit(0);
		}

		// read binary data byte by byte and save it into BufferedImage object
		int x,y;
		byte[] rgb = new byte[3];
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		for(y=0;y<getH();y++)
		{
	  		for(x=0;x<getW();x++)
			{
				rgb[0] = fis.readByte();
				rgb[1] = fis.readByte();
				rgb[2] = fis.readByte();
				setPixel(x, y, rgb);
			}
		}

       	fis.close();

		System.out.println("Read "+fileName+" Successfully.");

	} // try
	catch(Exception e)
	{
		System.err.println(e.getMessage());
	}
  }

  public void write2PPM(String fileName)
  // wrrite the image data in img to a PPM file
  {
	FileOutputStream fos = null;
	PrintWriter dos = null;

	try{
		fos = new FileOutputStream(fileName);
		dos = new PrintWriter(fos);

		System.out.println("Writing the Image buffer into "+fileName+"...");

		// write header
		dos.print("P6"+"\n");
		dos.print("#CS451"+"\n");
		dos.print(getW() + " "+ getH() +"\n");
		dos.print(255+"\n");
		dos.flush();

		// write data
		int x, y;
		byte[] rgb = new byte[3];
		for(y=0;y<getH();y++)
		{
			for(x=0;x<getW();x++)
			{
				getPixel(x, y, rgb);
				fos.write(rgb[0]);
				fos.write(rgb[1]);
				fos.write(rgb[2]);

			}
			fos.flush();
		}
		dos.close();
		fos.close();

		System.out.println("Wrote into "+fileName+" Successfully.");

	} // try
	catch(Exception e)
	{
		System.err.println(e.getMessage());
	}
  }

  public void display()
  // display the image on the screen
  {
     // Use a label to display the image
      //String title = "Image Name - " + fileName;
      String title = fileName;
      JFrame frame = new JFrame(title);
      JLabel label = new JLabel(new ImageIcon(img));
      frame.add(label, BorderLayout.CENTER);
      frame.pack();
      frame.setVisible(true);
      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
  }

  // CUSTOM METHODS -------------------------------------
  // Brian Kan CS4551 S2018

  // CONVERT GRAYSCALE
  public void convertGrayscale(){
	int[] irgb=new int[3];
	double Gray = 0;

    for(int i=0; i<getW();i++){
		for(int j=0;j<getH();j++){
			this.getPixel(i,j,irgb);

			Gray=Math.round(0.299 * irgb[0] + 0.587 * irgb[1] + 0.114 * irgb[2]);
			if(Gray>255){
				Gray=255;
			}
			if(Gray<0){
				Gray=0;
			}
			irgb[0]=(int)Gray;
			irgb[1]=(int)Gray;
			irgb[2]=(int)Gray;
			this.setPixel(i,j,irgb);
			}
		}
	}

	// For thresholding, I originally went with the if(value<#) route
	// but it seemed inefficent, so I went searching online for a way to 
	// programatically generate threshold values and found an algorithm on 
	// a C++ forums that detailed the equation (value*nlevel/256)*255/(255*nlevel/256)
	// which could be simplified to (value/threshold size)*block size,
	// where threshold size was 256/nlevel and block size was 255/(nlevel-1)
	//  Because of integer division the result would be 0,1,2,3 * block size
	//   (e.g. 0,1,2,3 * 85 for nlevel 4) so every value would land on a multiple
	//    of the block size, 85, 170, etc.


	public void convertThresholding(int nlevel){
		int[] irgb=new int[3];
		int newColor=0;
		for(int i=0; i<getW();i++){
			for(int j=0;j<getH();j++){
				this.getPixel(i,j,irgb);

				switch(nlevel){
					//BI LEVEL
					case 2: // Bi-level
					 if(irgb[0]>=128){
						 newColor=255;
					 }
					 else{
						 newColor=0;
					 }
					break;
					//QUAD LEVEL
					case 4: // Quad-level
					// if(irgb[0]>=192){
					// 	newColor=255;
					// }
					// else if(irgb[0]>=128){
					// 	newColor=170;
					// }
					// else if(irgb[0]>=64){
					// 	newColor=85;
					// }
					// else{
					// 	newColor=0;
					// }
					// break;
					newColor=(irgb[0]/64)*85;
					break;
					// OCTA LEVEL
					case 8: //Octa-level
					newColor=(int)((irgb[0]/32)*36.428);
					break;
					case 16: //Hexa-level
					newColor=(irgb[0]/16)*17;
					break;
				}

				irgb[0]=newColor;
				irgb[1]=newColor;
				irgb[2]=newColor;

				this.setPixel(i,j,irgb);
				}
			}
		}


	public void convertErrorDiffusion(int nlevel){
		// Current(i,j) Right(i+1,j) BottomLeft(i-1,j+1) Bottom(i,j+1) BottomRight(i+1,j+1)
		int[] irgb=new int[3];
		int newColor=0;
		double error=0;
		for(int j=0; j<getH();++j){
			for(int i=0;i<getW();++i){
				this.getPixel(i,j,irgb);
				//System.out.println(i+" "+j+" "+irgb[0]);
				switch(nlevel){
					//BI LEVEL
					case 2: // Bi-level
					 if(irgb[0]<128){
						 newColor=0;
					 }
					 else{
						 newColor=255;
					 }
					error=irgb[0]-newColor;
					floydSteingburgDiffusion(i, j, error);
					break;

					case 4: 
					newColor=(irgb[0]/64)*85;
					error=irgb[0]-newColor;
					floydSteingburgDiffusion(i, j, error);
					break;

					case 8: //Octa-level
					newColor=(int)((irgb[0]/32)*36.5);
					if(newColor>255){
						newColor=255;
					}
			
					error=irgb[0]-newColor;
					// if(i%2==1){
					// 	System.out.println(error+" "+irgb[0]+" "+newColor);
					// }
					floydSteingburgDiffusion(i, j, error);
					break;

					case 16: //Hexa-level
					newColor=(irgb[0]/16)*17;
					error=irgb[0]-newColor;
					floydSteingburgDiffusion(i, j, error);
					break;
				}

				irgb[0]=newColor;
				irgb[1]=newColor;
				irgb[2]=newColor;

				this.setPixel(i,j,irgb);

				}
			}
		
	}

	
	public void floydSteingburgDiffusion(int i,int j,double error){
		int tempArray[] = new int[3];
		if(i+1<getW()){ 					// Right (i+1,j)
			tempArray=new int[3];
			this.getPixel(i+1,j,tempArray);
			tempArray[0]=(int)(tempArray[0]+(error*(7.0/16.0)));
			this.setPixel(i+1,j,tempArray);
		 }
		 if(i-1>=0&&j+1<getH()){  			 // Bottom Left (i-1,j+1)
			tempArray=new int[3];
			this.getPixel(i-1,j+1,tempArray);
			tempArray[0]=(int)(tempArray[0]+(error*(3.0/16.0)));
			this.setPixel(i-1,j+1,tempArray);
		 }
		 if(j+1<getH()){ 					// Bottom (i,j+1)
			tempArray=new int[3];
			this.getPixel(i,j+1,tempArray);
			tempArray[0]=(int)(tempArray[0]+(error*(5.0/16.0)));
			this.setPixel(i,j+1,tempArray);
		 }
		 if(i+1<getW()&&j+1<getH()){				 //BottomRight(i+1,j+1)
			tempArray=new int[3];
			this.getPixel(i+1,j+1,tempArray);
			tempArray[0]=(int)(tempArray[0]+(error*(1.0/16.0)));
			this.setPixel(i+1,j+1,tempArray);
		 }
	}

	public void generateLookupTable(){ // 0 Index 1 R 2 G 3 B
		String indexBinary,redBinary,greenBinary,blueBinary;
		for(int i=0;i<256;i++){ // Populate indices with numbers 0-255
			this.lookupTable[i][0]=i;
		}
		for(int i=0;i<256;i++){
				indexBinary=Integer.toString(lookupTable[i][0],2);
									// Substring into RGB values
				
				while(indexBinary.length()<8){
					indexBinary="0"+indexBinary;
				}
									
				redBinary=indexBinary.substring(0,3);
				greenBinary=indexBinary.substring(3,6);
				blueBinary=indexBinary.substring(6);
				
				lookupTable[i][1]= 16+Integer.parseInt(redBinary,2)*32;
				lookupTable[i][2]= 16+Integer.parseInt(greenBinary,2)*32;
				lookupTable[i][3]= 32+Integer.parseInt(blueBinary,2)*64;
	
		}

		// PRINT OUT TABLES
		System.out.println("Index	R  G  B");
		System.out.println("------------------");
		for(int i=0;i<256;i++){
			System.out.println(lookupTable[i][0]+"   "+lookupTable[i][1]+"   "+lookupTable[i][2]+"   "+lookupTable[i][3]);
		}
	}
	public void convertToEightBits(){
		int[] irgb= new int[3];
		int r,b,g,resultInt;
		String resultString,redString,blueString,greenString;
		for(int i=0; i<getH();i++){
			for(int j=0;j<getW();j++){
				this.getPixel(j,i,irgb);
				r=irgb[0]/32;
				g=irgb[1]/32;
				b=irgb[2]/64;
				redString=Integer.toString(r,2);
				greenString=Integer.toString(g,2);
				blueString=Integer.toString(b,2);

				while(redString.length()<3){
					redString="0"+redString;
				}
				while(greenString.length()<3){
					greenString="0"+greenString;
				}
				while(blueString.length()<2){
					blueString="0"+blueString;
				}


				resultString=redString+greenString+blueString;// Convert RGB to 8 bits
				if(resultString.length()>8){
					System.out.println("ERROR OCCURED");
				}
				resultInt=Integer.parseInt(resultString,2); // Get resultant integer from 8 bit binary value
				
				//System.out.println("Coords "+j+","+i+"  "+resultString+"  "+resultInt);
				irgb[0]=resultInt;
				irgb[1]=resultInt;
				irgb[2]=resultInt;

				this.setPixel(j,i,irgb);
			}
		}
		
	}
	public void convertUniformCQuantization(){
		int[] irgb= new int[3];
		int index=0;
		for(int i=0; i<getH();i++){
			for(int j=0;j<getW();j++){
				this.getPixel(j,i,irgb);
				index=irgb[0];
			
				irgb[0]=lookupTable[index][1];
				irgb[1]=lookupTable[index][2];
				irgb[2]=lookupTable[index][3];
				this.setPixel(j,i,irgb);
			}
		}

	}


} // Image class