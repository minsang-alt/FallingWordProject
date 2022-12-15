import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class CharactorMotion extends Thread{
	
	//하트가 깎일때 쓰는 적의 모션
	private String enemy1AttackArray[] ={"enemyAttackImg/enemy1Attack.png","enemyAttackImg/enemy1Attack2.png","enemyAttackImg/enemy1Attack3.png","enemyAttackImg/enemy1Attack4.png"};
	//적의 피가 깎일 때 쓰는 적의 모션
	private String enemy1StructArray[] = {"struckImg/enemy1struct.png"};
	//커비의 공격 모션
	private String kirby1AttackArray[] = {"kirbyAttackImg/kirby1Attack1.png","kirbyAttackImg/kirby1Attack2.png","kirbyAttackImg/kirby1Attack3.png","kirbyAttackImg/kirby1Attack4.png","kirbyAttackImg/kirby1Attack5.png","kirbyAttackImg/kirby1Attack6.png","kirbyAttackImg/kirby1Attack7.png","kirbyAttackImg/kirby1Attack8.png","kirbyAttackImg/kirby1Attack9.png","kirbyAttackImg/kirby1Attack10.png"};
	
	private ImageIcon [] iconArray;
	private String index ;
	private JLabel la;
	private long delay;
	public CharactorMotion(String index , JLabel la, long delay) {
		this.index = index;
		this.la = la;
		this.delay = delay;
	}
	public void makeIcon() {
		if(index.equals("enemy1Attack")) {
		iconArray = new ImageIcon[enemy1AttackArray.length];
		for(int i=0; i<iconArray.length; i++) {
			iconArray[i] = new ImageIcon(enemy1AttackArray[i]);
		}
		}
		else if(index.equals("enemy1Struct")) {
			iconArray = new ImageIcon[enemy1StructArray.length];
			for(int i=0; i<iconArray.length; i++) {
				iconArray[i] = new ImageIcon(enemy1StructArray[i]);
			}
		}
		else if(index.equals("kirby1Attack")) {
			iconArray = new ImageIcon[kirby1AttackArray.length];
			for(int i=0; i<iconArray.length; i++) {
				iconArray[i] = new ImageIcon(kirby1AttackArray[i]);
			}
		}
		
	}
	public void run() {
		makeIcon();
		for(int i=0; i<iconArray.length; i++)
		{
			la.setIcon(iconArray[i]);
			try {
				
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
