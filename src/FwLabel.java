import javax.swing.JLabel;

public class FwLabel extends JLabel {
	private boolean valid  = true; //라벨이 아직도 유효한지 체크
	//
	public FwLabel(String text)
	{
		super(text);
		setSize(120,40);
		
	}
	
	public void setValid(boolean valid)
	{
		this.valid = valid;
	}
	public boolean getValid() {
		return valid;
	}
}
