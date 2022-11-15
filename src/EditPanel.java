import java.awt.*;

import javax.swing.*;

public class EditPanel extends JPanel {
	private int money =0;
	
	public EditPanel() {
		setLayout(new BorderLayout());
		JLabel store = new JLabel("상점");
		JPanel top = new JPanel();
		top.add(store);
		top.add(new JLabel(Integer.toString(money)));
		
		add(top,BorderLayout.NORTH);
		ItemMenu itemMenu = new ItemMenu();
		add(itemMenu,BorderLayout.CENTER);
	}
	
}
class ItemMenu extends JPanel{
	public ItemMenu() {
		setLayout(new GridLayout(3,3,5,5));
		
	}
	
}
