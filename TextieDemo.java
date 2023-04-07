import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
class Textie implements ActionListener,CaretListener{
	JFrame frame;
	JTextArea txt_area;
	JMenuItem about;
	JMenuItem new_file;
	JMenuItem open;
	JMenuItem save;
	JMenuItem save_as;
	JMenuItem exit;
	JMenuItem Cut;
	JMenuItem Copy;
	JMenuItem Paste;
	JMenuItem select_all;
	String name = "Untited";
	String path = " ";
	Textie(){
		frame = new JFrame(name+" - Textie");
		frame.setSize(400,400);
		frame.setVisible(true);
		frame.setLayout(new BorderLayout(10,10));
		
		txt_area = new JTextArea();
		txt_area.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,20));
		txt_area.addCaretListener(this);
		frame.add(new JScrollPane(txt_area));
		
		
		JMenuBar menu_bar = new JMenuBar();
		menu_bar.setBounds(0,0,200,400);
		
		
		JMenu file = new JMenu("File");
		new_file = new JMenuItem("New...",KeyEvent.VK_N);
		new_file.setAccelerator(
				 KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_DOWN_MASK));
				 
		open = new JMenuItem("Open",KeyEvent.VK_O);
		open.setAccelerator(
				 KeyStroke.getKeyStroke(KeyEvent.VK_O,InputEvent.CTRL_DOWN_MASK));
				 
		save = new JMenuItem("Save",KeyEvent.VK_S);
		save.setAccelerator(
				 KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_DOWN_MASK));
				 
		save_as = new JMenuItem("Save as",KeyEvent.VK_S);
		save_as.setAccelerator(
				 KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.ALT_DOWN_MASK));
				 
		
		exit = new JMenuItem("Exit",KeyEvent.VK_E);
		exit.setAccelerator(
				 KeyStroke.getKeyStroke(KeyEvent.VK_E,InputEvent.CTRL_DOWN_MASK));
		
		save.setEnabled(false);
		file.add(new_file);
		file.add(open);
		file.add(save);
		file.add(save_as);
		file.addSeparator();
		file.add(exit);
		new_file.addActionListener(this);
		open.addActionListener(this);
		save.addActionListener(this);
		save_as.addActionListener(this);
		exit.addActionListener(this);
		menu_bar.add(file);
		
		
		JMenu edit = new JMenu("Edit");
		Cut = new JMenuItem("Cut",KeyEvent.VK_X);
		Cut.setAccelerator(
				 KeyStroke.getKeyStroke(KeyEvent.VK_X,InputEvent.CTRL_DOWN_MASK));
		
		Copy = new JMenuItem("Copy",KeyEvent.VK_C);
		Copy.setAccelerator(
				 KeyStroke.getKeyStroke(KeyEvent.VK_C,InputEvent.CTRL_DOWN_MASK));
		
		Paste = new JMenuItem("Paste",KeyEvent.VK_V);
		Paste.setAccelerator(
				 KeyStroke.getKeyStroke(KeyEvent.VK_V,InputEvent.CTRL_DOWN_MASK));
		
		select_all = new JMenuItem("Select All",KeyEvent.VK_A);
		select_all.setAccelerator(
				 KeyStroke.getKeyStroke(KeyEvent.VK_A,InputEvent.ALT_DOWN_MASK));
		
		edit.add(Cut);
		edit.add(Copy);
		edit.add(Paste);
		edit.add(select_all);
		Cut.addActionListener(this);
		Copy.addActionListener(this);
		Paste.addActionListener(this);
		select_all.addActionListener(this);
		menu_bar.add(edit);
		
		
		JMenu format = new JMenu("Format");
		JMenuItem font_ = new JMenuItem("Font...");
		font_.addActionListener(this);
		format.add(font_);
		menu_bar.add(format);
		
		
		JMenu help = new JMenu("Help");
		about = new JMenuItem("About");
		about.addActionListener(this);
		help.add(about);
		menu_bar.add(help);
		
		frame.setJMenuBar(menu_bar);
	}
	void open_file(){
		JFileChooser file_open = new JFileChooser();
			int open_approval = file_open.showOpenDialog(null);
			if(open_approval == JFileChooser.APPROVE_OPTION){
				File file = file_open.getSelectedFile();
				path = file.getPath();
				name = file.getName();
				try{
					BufferedReader buff = new BufferedReader(new FileReader(path));
					String line = null,file_content = "";
					while((line=buff.readLine()) != null){
						file_content += (line+"\n");
					}
					txt_area.setText(file_content);
					buff.close();
					if(name.equals(null) == false)
						frame.setTitle(name+" - Textie");
					if(!save.isEnabled())
						save.setEnabled(true);
				}catch(Exception e){
					JOptionPane.showMessageDialog(frame,e.getMessage(),"Alert",JOptionPane.ERROR_MESSAGE);
				}	
			}
	}
	void Save_as(){
		JFileChooser file_save = new JFileChooser();
			int save_approval = file_save.showSaveDialog(null);
			if(save_approval == JFileChooser.APPROVE_OPTION){
				File file = file_save.getSelectedFile();
				path = file.getPath();
				name = file.getName();
				try{
					FileWriter fw = new FileWriter(path);
					fw.write(txt_area.getText());
					fw.close();
					if(name.equals(null) == false)
						frame.setTitle(name+" - Textie");
				}catch(NullPointerException ne){
						String s = ne.getMessage();
				}catch(IOException ioe){
					JOptionPane.showMessageDialog(frame,ioe.getMessage(),"Alert",JOptionPane.ERROR_MESSAGE);
				}
			}
			if(!save.isEnabled())
				save.setEnabled(true);
	}
	void Save(){
		try{
			FileWriter fileWrite = new FileWriter(path);
			fileWrite.write(txt_area.getText());
			fileWrite.close();
			frame.setTitle(name+" - Textie");
		}catch(IOException ioe){
				JOptionPane.showMessageDialog(frame,ioe.getMessage(),"Alert",JOptionPane.ERROR_MESSAGE);
		}
	}
	void Font_selection(){
		JDialog font_dialog = new JDialog(frame,"Font");
		
		JTabbedPane Tab_pane = new JTabbedPane(); 
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();//.getAvailableFontFamilyNames();
		Font font_array[] = ge.getAllFonts();
		String arr[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		JList <String> font_list = new JList <String> (arr);
		font_list.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
		font_list.setSelectedValue(txt_area.getFont().getName(),true);
		JPanel panel1 = new JPanel();
		JButton button1 = new JButton("OK");
		button1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				if(ae.getSource() == button1){
					int index = font_list.getSelectedIndex();
					Font selectedFont = font_array[index];
					txt_area.setFont(new Font(selectedFont.getName(),Font.PLAIN,20));
				}
			}
		});
		JScrollPane jScroll = new JScrollPane(font_list);
		panel1.add(jScroll);
		panel1.add(button1);
		panel1.setLayout(new FlowLayout(FlowLayout.CENTER));
		Tab_pane.add("Font",panel1);
		
		JPanel panel2 = new JPanel();
		Integer size_array[] = {8,9,10,11,12,14,16,18,20,22,24,28,32,36,42,46,52,60,72};
		JComboBox <Integer> size_list = new JComboBox <Integer> (size_array);
		size_list.setSelectedIndex(8);
		JButton button3 = new JButton("OK");
		button3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				if(ae.getSource() == button3){
					Integer selectedSize = (Integer)size_list.getSelectedItem();
					int font_weight = selectedSize.intValue();
					txt_area.setFont(new Font(txt_area.getFont().getName(),Font.PLAIN,selectedSize.intValue()));
				}
			}
		});
		panel2.add(size_list);
		panel2.add(button3);
		panel2.setLayout(new FlowLayout(FlowLayout.CENTER));
		Tab_pane.add("Font Style",panel2);
		
		JPanel panel3 = new JPanel();
		
		font_dialog.add(Tab_pane);
		font_dialog.setSize(300,300);
		font_dialog.setVisible(true);
	}
	public void actionPerformed(ActionEvent ae){
	    String act_cmd = ae.getActionCommand();
		if(act_cmd.equals("New...")){
			Textie NewText = new Textie();
			NewText.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
		else if(act_cmd.equals("Open")){
			open_file();
		}
		else if(act_cmd.equals("Save as")){
			Save_as();
		}
		else if(act_cmd.equals("Save")){
			Save();
		}
		else if(act_cmd.equals("Exit")){
				System.exit(0);
		}
		else if(act_cmd.equals("Cut")){
			txt_area.cut();
		}
		else if(act_cmd.equals("Copy")){
			txt_area.copy();
		}
		else if(act_cmd.equals("Paste")){
			txt_area.paste();
		}
		else if(act_cmd.equals("Select All")){
			txt_area.selectAll();
		}
		else if(act_cmd.equals("About")){
			JOptionPane.showMessageDialog(frame,"Hello! Welcome to TEXTIE Editor\n Textie is a simple text editor\n It is implemented using JAVA Swing.");
		}
		else if(act_cmd.equals("Font...")){
			Font_selection();
		}
	}
	public void caretUpdate(CaretEvent ce){
		String s = txt_area.getText();
		if(s.equals("") == false)
			frame.setTitle("*"+name+" - Textie");
		else
			frame.setTitle(name+" - Textie");
	}
}
class TextieDemo{
	public static void main(String args[]){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				Textie textE = new Textie();
				textE.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});
	}
}