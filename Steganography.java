import java.io.File;
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.awt.image.DataBufferByte;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Steganography{
	public static void main(String[] args) {
	}
	
	/*
	 *Encrypt an image with text, the output file will be of type .png
	 *@param path		the path (folder) containing the image to modify
	 *@param original	the name of the image to modify
	 *@param ext1		the extension type of the image to modify (jpg, png)
	 *@param stegan	  	the output name of the file
	 *@param message  	the text to hide in the image
	 *@param type		integer representing either basic or advanced encoding
	 */
	public boolean encode(String path, String original, String ext1, String stegan, String message)
	{
		String file_name = image_path(path,original,ext1);
		BufferedImage image_orig = getImage(file_name);
		
		// user space is not necessary for Encrypting
		BufferedImage image = user_space(image_orig);
		image = add_text(image,message);
		
		return(setImage(image,new File(image_path(path,stegan,"png")),"png"));
	}
	
	/*
	 *Decrypt assumes the image being used is of type .png, extracts the hidden text from an image
	 *@param path   	the path (folder) containing the image to extract the message from
	 *@param name 		the name of the image to extract the message from
	 *@param type		integer representing either basic or advanced encoding
	 */
	public String decode(String path, String name)
	{
		byte[] decode;
		try
		{
			// user space is necessary for decrypting
			BufferedImage image  = user_space(getImage(image_path(path,name,"png")));
			decode = decode_text(get_byte_data(image));
			return(new String(decode));
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(
				null, 
				"There is no hidden message in this image!",
				"Error",
				JOptionPane.ERROR_MESSAGE
			);
			return "";
		}
	}
	
	/*
	 *Returns the complete path of a file, in the form: path\name.ext
	 *@param path		the path (folder) of the file
	 *@param name 		the name of the file
	 *@param ext	  	the extension of the file
	 *@return 		a String representing the complete path of a file
	 */
	private String image_path(String path, String name, String ext)
	{
		return path + "/" + name + "." + ext;
	}
	
	/*
	 *Get method to return an image file
	 *@param f		the complete path name of the image.
	 *@return A 		bufferedImage of the supplied file path
	 *@see			steganography.image_path
	 */
	private BufferedImage getImage(String f)
	{
		BufferedImage image = null;
		File file = new File(f);
		
		try
		{
			image = ImageIO.read(file);
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(
				null, 
				"Image could not be read!",
				"Error",
				JOptionPane.ERROR_MESSAGE
			);
		}
		return image;
	}
	
	/*
	 *Set method to save an image file
	 *@param image		the image file to save
	 *@param file	  	file  to save the image to
	 *@param ext	 	the extension and thus format of the file to be saved
	 *@return 		returns true if the save is succesful
	 */
	private boolean setImage(BufferedImage image, File file, String ext)
	{
		try
		{
			// delete resources used by the file
			file.delete(); 
			ImageIO.write(image,ext,file);
			return true;
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(
				null, 
				"File could not be saved!",
				"Error",
				JOptionPane.ERROR_MESSAGE
			);
			return false;
		}
	}
	
	/*
	 *Handles the addition of text into an image
	 *@param image		the image to add hidden text to
	 *@param text	 	the text to hide in the image
	 *@return 		returns the image with the text embedded in it
	 */
	private BufferedImage add_text(BufferedImage image, String text)
	{
		// convert all items to byte arrays: image, message, message length
		byte img[]  = get_byte_data(image);
		byte msg[] = text.getBytes();
		byte len[]   = bit_conversion(msg.length);
		try
		{
			// 0 bytes of space for first positiong
			encode_text(img, len,  0);
			// 4 bytes of space for length: 4 bytes * 8 bit = 32 bits
			encode_text(img, msg, 32); 
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(
				null, 
				"Target File cannot hold message!", 
				"Error",
				JOptionPane.ERROR_MESSAGE
			);
		}
		return image;
	}
	
	/*
	 *Creates a user space version of a Buffered Image, for editing and saving bytes
	 *@param image		the image to put into user space, removes compression interferences
	 *@return 		the user space version of the supplied image
	 */
	private BufferedImage user_space(BufferedImage image)
	{
		// create new_img with the attributes of image
		BufferedImage new_img = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D graphics = new_img.createGraphics();
		graphics.drawRenderedImage(image, null);
		// release all allocated memory for this image
		graphics.dispose(); 
		return new_img;
	}
	
	/*
	 *Gets the byte array of an image
	 *@param image 		the image to get byte data from
	 *@return 		returns the byte array of the image supplied
	 *@see 			Raster
	 *@see 			WritableRaster
	 *@see 			DataBufferByte
	 */
	private byte[] get_byte_data(BufferedImage image)
	{
		WritableRaster raster = image.getRaster();
		DataBufferByte buffer = (DataBufferByte)raster.getDataBuffer();
		return buffer.getData();
	}
	
	/*
	 *Gernerates proper byte format of an integer
	 *@param i 		the integer to convert
	 *@return 		returns a byte[4] array converting the supplied integer into bytes
	 */
	private byte[] bit_conversion(int i)
	{
		// originally integers (ints) cast into bytes
		// byte byte7 = (byte)((i & 0xFF00000000000000L) >>> 56);
		// byte byte6 = (byte)((i & 0x00FF000000000000L) >>> 48);
		// byte byte5 = (byte)((i & 0x0000FF0000000000L) >>> 40);
		// byte byte4 = (byte)((i & 0x000000FF00000000L) >>> 32);
		
		// only using 4 bytes
		byte byte3 = (byte)((i & 0xFF000000) >>> 24); //0
		byte byte2 = (byte)((i & 0x00FF0000) >>> 16); //0
		byte byte1 = (byte)((i & 0x0000FF00) >>> 8 ); //0
		byte byte0 = (byte)((i & 0x000000FF));
		// {0,0,0,byte0} is equivalent, since all shifts >= 8 will be 0
		return(new byte[]{byte3,byte2,byte1,byte0});
	}
	
	/*
	 *Encode an array of bytes into another array of bytes at a supplied offset
	 *@param image		array of data representing an image
	 *@param addition 	array of data to add to the supplied image data array
	 *@param offset	  	the offset into the image array to add the addition data
	 *@return 		returns data Array of merged image and addition data
	 */
	private byte[] encode_text(byte[] image, byte[] addition, int offset)
	{
		// check that the data + offset will fit in the image
		if(addition.length + offset > image.length)
		{
			throw new IllegalArgumentException("File not long enough!");
		}
		// loop through each addition byte
		for(int i = 0; i < addition.length; ++i)
		{
			// loop through the 8 bits of each byte
			int add = addition[i];
			//ensure the new offset value carries on through both loops
			for(int bit = 7; bit >= 0; --bit, ++offset) 
			{
				// assign an integer to b, shifted by bit spaces AND 1
				// a single bit of the current byte
				int b = (add >>> bit) & 1;
				// assign the bit by taking: [(previous byte value) AND 0xfe] OR bit to add
				// changes the last bit of the byte in the image to be the bit of addition
				image[offset] = (byte)((image[offset] & 0xFE) | b );
			}
		}
		return image;
	}
	
	/*
	 *Retrieves hidden text from an image
	 *@param image 		array of data, representing an image
	 *@return 		array of data which contains the hidden text
	 */
	private byte[] decode_text(byte[] image)
	{
		int length = 0;
		int offset  = 32;
		// loop through 32 bytes of data to determine text length
		// i=24 will also work, as only the 4th byte contains real data
		for(int i = 0; i < 32; ++i) 
		{
			length = (length << 1) | (image[i] & 1);
		}
		
		byte[] result = new byte[length];
		
		// loop through each byte of text
		for(int b = 0; b < result.length; ++b)
		{
			// loop through each bit within a byte of text
			for(int i = 0; i < 8; ++i, ++offset)
			{
				// assign bit: [(new byte value) << 1] OR [(text byte) AND 1]
				result[b] = (byte)((result[b] << 1) | (image[offset] & 1));
			}
		}
		return result;
	}
}
