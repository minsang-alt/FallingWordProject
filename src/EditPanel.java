import java.awt.*;

import javax.swing.*;

public class EditPanel extends JPanel {
	private int money =0;
	private JLabel moneyLabel = new JLabel(Integer.toString(money));
	public EditPanel() {
		//돈라벨 폰트및 색 설정
		moneyLabel.setForeground(Color.blue);
		moneyLabel.setFont(new Font("Gothic",Font.BOLD,20));
		JLabel coin = new JLabel("coin");
		coin.setFont(new Font("Gothic",Font.BOLD,10));
		
		setLayout(new BorderLayout());
		JLabel store = new JLabel("상점");
		//edit패널에서 상단 패널을 만듬 
		JPanel top = new JPanel();
		//상단 패널에 돈라벨과 상점이름라벨을 붙인다.
		top.add(store);
		top.add(moneyLabel);
		top.add(coin);
		add(top,BorderLayout.NORTH);
		
		
		ItemMenu itemMenu = new ItemMenu();
		add(itemMenu,BorderLayout.CENTER);
	}
	
	
	public void increaseCoin(int money) {
		this.money +=money;
		moneyLabel.setText(Integer.toString(this.money));
	}
	
	
}
class ItemMenu extends JPanel{
	public ItemMenu() {
		setLayout(new GridLayout(3,3,5,5));
		
	}
	
}
