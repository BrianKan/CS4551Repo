Brian Kan 3034324155 
CS 4551


Readme -------
To execute the program, compile the two java files with javac. Then, run the CS4551_Kan file with the input file name as the first argument.

javac CS4551_Kan.java Image.java

java CS4551_Kan Ducky.ppm


Comments ---
The program works for all the basic functions (Tasks 1-3), but n-level
error diffusion seems to leave artifacts in the background. These
aren't present in the samples, but they seem to be present in alot of 
online pictures that demonstrate the Floyd-Steinberg dithering process. 
NONE of the functions modify the original image, even though the
java windows display them as the original image name. Each of the images
are saved according to the process used to create them.