import java.awt.Color;
import java.awt.Insets;
import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JMenu;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;

/*
 *class Steganography_View
*/
public class Steganography_View extends JFrame
{
	// size variables for window
	private static int WIDTH = 500;
	private static int HEIGHT = 400;
	
	// elements for JPanel
	private JTextArea input;
	private JScrollBar scroll,scroll2;
	private JButton encodeButton,decodeButton;
	private JLabel image_input;
	
	// elements for JMenu
	private JMenu file;
	private JMenuItem encode;
	private JMenuItem decode;
	private JMenuItem exit;
	
	/*
	 *constructor for Steganography_View class
	 *@param name 		used to set the title on the JFrame
	*/
	public Steganography_View(String name)
	{
		// set the title of the JFrame
		super(name);
		
		// JMenubar
		JMenuBar menu = new JMenuBar();
		
		JMenu file = new JMenu("File");	file.setMnemonic('F');
		encode = new JMenuItem("Encode"); encode.setMnemonic('E'); file.add(encode);
		decode = new JMenuItem("Decode"); decode.setMnemonic('D'); file.add(decode);
		file.addSeparator();
		exit = new JMenuItem("Exit"); exit.setMnemonic('x'); file.add(exit);
		
		menu.add(file);
		setJMenuBar(menu);
		
		// display rules
		// allow window to be resized: true?false
		setResizable(true);		
		// background color of window: Color(int,int,int) or Color.name
		setBackground(Color.lightGray);	
		// set first location on the screen to display window
		setLocation(100,100);		
		// what to do on close operation: exit, do_nothing, etc
        	setDefaultCloseOperation(EXIT_ON_CLOSE);
		// set the size of the window
        	setSize(WIDTH,HEIGHT);	
		// show the window: true?false
        	setVisible(true);						
	}
	
	/*
	 *@return the menu item 'Encode'
	*/
	public JMenuItem getEncode()
	{ 
		return encode;			
	}
	/*
	 *@return the menu item 'Decode'
	*/
	public JMenuItem getDecode()
	{ 
		return decode;			
	}
	/*
	 *@return the menu item 'Exit'
	*/
	public JMenuItem getExit()	
	{ 
		return exit;				
	}
	/*
	 *@return the TextArea containing the text to encode
	*/
	public JTextArea getText()		
	{ 
		return input;				
	}
	/*
	 *@return the JLabel containing the image to decode text from
	*/
	public JLabel getImageInput()	
	{ 
		return image_input;		
	}
	/*
	 *@return the JPanel displaying the encode view
	*/
	public JPanel getTextPanel()	
	{ 
		return new Text_Panel();	
	}
	/*
	 *@return the JPanel displaying the decode view
	*/
	public JPanel getImagePanel()	
	{ 
		return new Image_Panel();	
	}
	/*
	 *@return the encode button
	*/
	public JButton getEButton()	
	{ 
		return encodeButton;		
	}
	/*
	 *@return the decode button
	*/
	public JButton getDButton()	
	{ 
		return decodeButton;		
	}
	
	/*
	 *class Text_Panel
	*/
	private class Text_Panel extends JPanel
	{
		/*
		 *constructor to enter text to be encoded
		*/
		public Text_Panel()
		{
			// setup GridBagLayout
			GridBagLayout layout = new GridBagLayout(); 
			GridBagConstraints layoutConstraints = new GridBagConstraints(); 
			setLayout(layout);
			
			input = new JTextArea();
			// set constraints for text panel
			layoutConstraints.gridx = 0; 
			layoutConstraints.gridy = 0; 
			layoutConstraints.gridwidth = 1; 
			layoutConstraints.gridheight = 1; 
			layoutConstraints.fill = GridBagConstraints.BOTH; 
			layoutConstraints.insets = new Insets(0,0,0,0); 
			layoutConstraints.anchor = GridBagConstraints.CENTER; 
			layoutConstraints.weightx = 1.0; 
			layoutConstraints.weighty = 50.0;
			JScrollPane scroll = new JScrollPane(input,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
			layout.setConstraints(scroll,layoutConstraints);
			scroll.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
	    		add(scroll);
	    	
	    		encodeButton = new JButton("Encode Now");
			// set constraints for decode button
	    		layoutConstraints.gridx = 0; layoutConstraints.gridy = 1; 
			layoutConstraints.gridwidth = 1; layoutConstraints.gridheight = 1; 
			layoutConstraints.fill = GridBagConstraints.BOTH; 
			layoutConstraints.insets = new Insets(0,-5,-5,-5); 
			layoutConstraints.anchor = GridBagConstraints.CENTER; 
			layoutConstraints.weightx = 1.0; layoutConstraints.weighty = 1.0;
			layout.setConstraints(encodeButton,layoutConstraints);
	    		add(encodeButton);
	    	
	    		// set basic display
			setBackground(Color.lightGray);
			setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
		}
	}
	
	/*
	 *class Image_Panel
	*/
	private class Image_Panel extends JPanel
	{
		/*
		 *constructor for displaying an image to be decoded
		*/
		public Image_Panel()
		{
			// setup GridBagLayout
			GridBagLayout layout = new GridBagLayout(); 
			GridBagConstraints layoutConstraints = new GridBagConstraints(); 
			setLayout(layout);
			
			image_input = new JLabel();
			// set constraints for image panel
			layoutConstraints.gridx = 0; 
			layoutConstraints.gridy = 0; 
			layoutConstraints.gridwidth = 1; 
			layoutConstraints.gridheight = 1; 
			layoutConstraints.fill = GridBagConstraints.BOTH; 
			layoutConstraints.insets = new Insets(0,0,0,0); 
			layoutConstraints.anchor = GridBagConstraints.CENTER; 
			layoutConstraints.weightx = 1.0; 
			layoutConstraints.weighty = 50.0;
			JScrollPane scroll2 = new JScrollPane(image_input,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
			layout.setConstraints(scroll2,layoutConstraints);
			scroll2.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			image_input.setHorizontalAlignment(JLabel.CENTER);
	    		add(scroll2);
	    	
	    		decodeButton = new JButton("Decode Now");
			// set constraints for decode button
	    		layoutConstraints.gridx = 0; 
			layoutConstraints.gridy = 1; 
			layoutConstraints.gridwidth = 1; 
			layoutConstraints.gridheight = 1; 
			layoutConstraints.fill = GridBagConstraints.BOTH; 
			layoutConstraints.insets = new Insets(0,-5,-5,-5); 
			layoutConstraints.anchor = GridBagConstraints.CENTER; 
			layoutConstraints.weightx = 1.0; 
			layoutConstraints.weighty = 1.0;
			layout.setConstraints(decodeButton,layoutConstraints);
	    		add(decodeButton);
	    	
	    		// set basic display
			setBackground(Color.lightGray);
			setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
	    }
	 }
	
	/*
	 *main method for testing
	*/
	public static void main(String args[])
	{
		new Steganography_View("Steganography");
	}
}
