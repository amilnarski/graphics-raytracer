package in.aaronmiller.raytrace;
/*
   ReadWritePPM contains methods that 

    readImage - read a ppm image file into a 2d array of RGBPixel
    writeImage - write a ppm image from a 2d array of RGBPixel
    
   Author: Michael Eckmann
   Skidmore College
   Spring 2012

*/

import java.io.*;
import java.util.*;

public class ReadWritePPM
{

	/*
		readImage - read a ppm image file into a 2d array of RGBPixel

		takes in the file name and returns the 2d array of RGBPixels

	*/
	public static RGBPixel[][] readImage(String fName) throws IOException
	{
		File theFile = new File(fName);
		Scanner scan = new Scanner(theFile);
		int i=0, j=0;
		int tokenCounter = 0;
		int cols = 0;
		int rows = 0;
		StringTokenizer tokens = null;

		outloop:
		while (scan.hasNextLine())
		{
			String aLine = scan.nextLine();
			if (aLine.charAt(0) == '#')
				continue;

			// process the line:
			tokens = new StringTokenizer(aLine, " ");
			while (tokens.hasMoreTokens())
			{
				tokenCounter++;
				String aToken = tokens.nextToken();
				if (tokenCounter == 1) 
				{
					// expect that aToken is "P3"
					if (!aToken.equals("P3")) System.out.println("Expected P3 magic number, got " + aToken);
				}
				if (tokenCounter == 2)
				{
					// expect that aToken is the number of columns
					cols = Integer.parseInt(aToken);
				}
				if (tokenCounter == 3)
				{
					// expect that aToken is the number of rows
					rows = Integer.parseInt(aToken);
				}
				if (tokenCounter == 4)
				{
					// expect that aToken is the maximum value
					// ignore
					break outloop;
				}

			}
				
		}

		RGBPixel outImage[][] = new RGBPixel[rows][cols];

		// still worry if more tokens in previously read line

		int channelCount = 0;
		int redVal = 0, blueVal = 0, greenVal = 0;
		while (scan.hasNextLine())
		{
			String aLine = scan.nextLine();
			if (aLine.charAt(0) == '#')
				continue;

			// process the line:
			tokens = new StringTokenizer(aLine, " ");
			while (tokens.hasMoreTokens())
			{
				String aToken = tokens.nextToken();
				if (channelCount == 0) 
				{
					redVal = Integer.parseInt(aToken);
					channelCount++;
				}
				else
				if (channelCount == 1) 
				{
					// expect that aToken is the number of columns
					greenVal = Integer.parseInt(aToken);
					channelCount++;
				}
				else
				if (channelCount == 2) 
				{
					// expect that aToken is the number of rows
					blueVal = Integer.parseInt(aToken);
					outImage[i][j] = new RGBPixel(redVal, greenVal, blueVal);
					j++;
					if (j >= cols)
					{
						i++;
						j = 0;
					}
					channelCount = 0;
		
				}
			}
				
		}
/*
		// possible that there's still some tokens on the line already read
		while (tokens.hasMoreTokens())
		{
			int redVal = Integer.parseInt(tokens.nextToken());
			int greenVal = Integer.parseInt(tokens.nextToken());
			int blueVal = Integer.parseInt(tokens.nextToken());
			outImage[i][j] = new RGBPixel(redVal, greenVal, blueVal);
			j++;
			if (j >= cols)
			{
				i++;
				j = 0;
			}
		}

		while (scan.hasNextLine())
		{
			String line = scan.nextLine();
			StringTokenizer tokens2 = new StringTokenizer(line, " ");
			while (tokens2.hasMoreTokens())
			{
				int redVal = Integer.parseInt(tokens2.nextToken());
				int greenVal = Integer.parseInt(tokens2.nextToken());
				int blueVal = Integer.parseInt(tokens2.nextToken());
				outImage[i][j] = new RGBPixel(redVal, greenVal, blueVal);
				j++;
				if (j >= cols)
				{
					i++;
					j = 0;
				}
			}
		}

*/

		return outImage;
	}

	/*
		writeImage - write to a ppm image from a 2d array of RGBPixel

		takes in a 2d array of RGBPixel and a file name and saves the
                image stored in the 2d array to disk in the file fName.
	*/
	public static void writeImage(RGBPixel inImage[][], String fName) throws IOException
	{
		File theFile = new File(fName);
		BufferedWriter bw = new BufferedWriter(new FileWriter(theFile));
		bw.write("P3");
		bw.newLine();
		bw.write(inImage[0].length + " " + inImage.length + " 255");
		bw.newLine();
		int i=0, j=0;

		while (true)
		{
			int redVal = inImage[i][j].getRed();
			int greenVal = inImage[i][j].getGreen();
			int blueVal = inImage[i][j].getBlue();
			bw.write(""+redVal + " " + greenVal + " " + blueVal);
			bw.newLine();
			j++;
			if (j >= inImage[0].length)
			{
				i++;
				j = 0;
			}
			if (i >= inImage.length) break;
		}
		bw.close();
	}

	public static void main(String[] args) throws IOException
	{

		String inFileName = "phillyImage1.ppm";
		RGBPixel originalImage[][];

		originalImage = readImage(inFileName);

		writeImage(originalImage, "out" + inFileName);
	}

	

}
