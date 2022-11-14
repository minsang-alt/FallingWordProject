import javax.swing.*;

public class EditPanel extends JPanel {
	private JTextField tf = new JTextField(20);
	private JButton saveBtn = new JButton("save");
	
	public EditPanel() {
		add(tf);
		add(saveBtn);
	}
}
