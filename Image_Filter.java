import java.io.*; 

public class Image_Filter extends javax.swing.filechooser.FileFilter
{
	/*
	 *determines if the extension is of the defined types
	 *@param ext 		extension of a file
	 *@return 		returns true if the extension is 'jpg' or 'png'
	*/
	protected boolean isImageFile(String ext)
	{
		return (ext.equals("jpg")||ext.equals("png"));
	}
	
	/*
	 *determines if the file is a directory or accepted extension
	 *@param f 		the File to run the directory/proper extension check on
	 *@return 		returns true if the File is a directory or accepted extension
	*/
	public boolean accept(File f)
	{
	    if (f.isDirectory())
	    {
	    	return true;
	    }

	    String extension = getExtension(f);
		if (extension.equals("jpg") || extension.equals("png"))
		{
			return true;
		}
		return false;
	}
	
	/*
	 *supplies File type description
	 *@return 		returns the String description
	*/
	public String getDescription()
	{
		return "Supported Image Files";
	}
	
	/*
	 *determines the extension
	 *@param f 		file to return the extension of
	 *@return 		returns the String representing the extension
	*/
	protected static String getExtension(File f)
	{
		String s = f.getName();
		int i = s.lastIndexOf('.');
		if (i > 0 &&  i < s.length() - 1) 
			return s.substring(i+1).toLowerCase();
		return "";
	}	
}
