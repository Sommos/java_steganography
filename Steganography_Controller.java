import java.io.File;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;

public class Steganography_Controller
{
	// program variables
	private Steganography_View view;
	private Steganography model;
	// panel displays
	private JPanel decode_panel;
	private JPanel encode_panel;
	// panel variables
	private JTextArea input;
	private JButton encodeButton,decodeButton;
	private JLabel image_input;
	// menu variables
	private JMenuItem encode;
	private JMenuItem decode;
	private JMenuItem exit;
	// action event classes
	private Encode enc;
	private Decode dec;
	private EncodeButton encButton;
	private DecodeButton decButton;
	// decode variable
	private String stat_path = "";
	private String stat_name = "";
	
	/*
	 *constructor to initialize view, model and environment variables
	 *@param aView		a GUI class, to be saved as view
	 *@param aModel 	a model class, to be saved as model
	*/
	public Steganography_Controller(Steganography_View aView, Steganography aModel)
	{
		// program variables
		view = aView;
		model = aModel;
		// assign view variables
		// 2 views
		encode_panel = view.getTextPanel();
		decode_panel = view.getImagePanel();
		// 2 data options
		input = view.getText();
		image_input = view.getImageInput();
		// 2 buttons
		encodeButton = view.getEButton();
		decodeButton = view.getDButton();
		// menu
		encode = view.getEncode();
		decode = view.getDecode();
		exit = view.getExit();
		// assign action events
		enc = new Encode();
		encode.addActionListener(enc);
		dec = new Decode();
		decode.addActionListener(dec);
		exit.addActionListener(new Exit());
		encButton = new EncodeButton();
		encodeButton.addActionListener(encButton);
		decButton = new DecodeButton();
		decodeButton.addActionListener(decButton);
		// encode view as default
		encode_view();
	}
	
	/*
	 *updates the single panel to display the encode View.
	*/
	private void encode_view()
	{
		update();
		view.setContentPane(encode_panel);
		view.setVisible(true);
	}
	
	/*
	 *updates the single panel to display the decode View.
	*/
	private void decode_view()
	{
		update();
		view.setContentPane(decode_panel);
		view.setVisible(true);
	}
	
	/*
	 *encode Class - handles the encode menu item
	*/
	private class Encode implements ActionListener
	{
		/*
		 *handles the click event
		 *@param e 		the ActionEvent Object
		*/
		public void actionPerformed(ActionEvent e)
		{
			// show the encode view
			encode_view();
		}
	}
	
	/*
	 *decode Class - handles the decode menu item
	*/
	private class Decode implements ActionListener
	{
		/*
		 *handles the click event
		 *@param e 		the ActionEvent Object
		*/
		public void actionPerformed(ActionEvent e)
		{
			// show the decode view
			decode_view(); 
			
			// start path of displayed File Chooser
			JFileChooser chooser = new JFileChooser("./");
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			chooser.setFileFilter(new Image_Filter());
			int returnVal = chooser.showOpenDialog(view);
			if (returnVal == JFileChooser.APPROVE_OPTION){
				File directory = chooser.getSelectedFile();
				try{
					String image = directory.getPath();
					stat_name = directory.getName();
					stat_path = directory.getPath();
					stat_path = stat_path.substring(0,stat_path.length()-stat_name.length()-1);
					stat_name = stat_name.substring(0, stat_name.length()-4);
					image_input.setIcon(new ImageIcon(ImageIO.read(new File(image))));
				}
				catch(Exception except) {
					// message if opening fails
					JOptionPane.showMessageDialog(
						view, 
						"The File cannot be opened!", 
						"Error!", 
						JOptionPane.INFORMATION_MESSAGE
					);
				}
			}
		}
	}
	
	/*
	 *exit Class - handles the exit menu item
	*/
	private class Exit implements ActionListener
	{
		/*
		 *handles the click event
		 *@param e 		the ActionEvent Object
		*/
		public void actionPerformed(ActionEvent e)
		{
			// exit the program
			System.exit(0); 
		}
	}
	
	/*
	 *Encode Button class - handles the Encode Button item
	*/
	private class EncodeButton implements ActionListener
	{
		/*
		 *handles the click event
		 *@param e 		the ActionEvent Object
		*/
		public void actionPerformed(ActionEvent e)
		{
			// start path of displayed File Chooser
			JFileChooser chooser = new JFileChooser("./");
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			chooser.setFileFilter(new Image_Filter());
			int returnVal = chooser.showOpenDialog(view);
			if (returnVal == JFileChooser.APPROVE_OPTION){
				File directory = chooser.getSelectedFile();
				try{
					String text = input.getText();
					String ext = Image_Filter.getExtension(directory);
					String name = directory.getName();
					String path = directory.getPath();
					path = path.substring(0,path.length()-name.length()-1);
					name = name.substring(0, name.length()-4);
					
					String stegan = JOptionPane.showInputDialog(
									view,
									"Enter output file name:", 
									"File name",
									JOptionPane.PLAIN_MESSAGE
					);
					
					if(model.encode(path,name,ext,stegan,text))
					{
						JOptionPane.showMessageDialog(
							view, 
							"The Image was encoded Successfully!", 
							"Success!", 
							JOptionPane.INFORMATION_MESSAGE
						);
					}
					else
					{
						JOptionPane.showMessageDialog(
							view, 
							"The Image could not be encoded!", 
							"Error!", 
							JOptionPane.INFORMATION_MESSAGE
						);
					}
					// display the new image
					decode_view();
					image_input.setIcon(new ImageIcon(ImageIO.read(new File(path + "/" + stegan + ".png"))));
				}
				catch(Exception except) {
					// message if opening fails
					JOptionPane.showMessageDialog(
						view, 
						"The File cannot be opened!", 
						"Error!", 
						JOptionPane.INFORMATION_MESSAGE
					);
				}
			}
		}
		
	}
	
	/*
	 *Decode Button class - handles the Decode Button item
	*/
	private class DecodeButton implements ActionListener
	{
		/*
		 *handles the click event
		 *@param e 		the ActionEvent Object
		*/
		public void actionPerformed(ActionEvent e)
		{
			String message = model.decode(stat_path, stat_name);
			System.out.println(stat_path + ", " + stat_name);
			if(message != "")
			{
				encode_view();
				JOptionPane.showMessageDialog(
					view, 
					"The Image was decoded Successfully!", 
					"Success!", 
					JOptionPane.INFORMATION_MESSAGE
				);
				input.setText(message);
			}
			else
			{
				JOptionPane.showMessageDialog(
					view, 
					"The Image could not be decoded!", 
					"Error!", 
					JOptionPane.INFORMATION_MESSAGE
				);
			}
		}
	}
	
	/*
	 *updates the variables to an initial state
	*/
	public void update()
	{
		// clear text area
		input.setText("");	
		// clear image
		image_input.setIcon(null);
		// clear path
		stat_path = "";		
		// clear name
		stat_name = "";				
	}
	
	/*
	 *main method for testing
	*/
	public static void main(String args[])
	{
		new Steganography_Controller(
			new Steganography_View("Steganography"),
			new Steganography()
		);
	}
}
