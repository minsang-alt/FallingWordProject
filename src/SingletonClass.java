
public class SingletonClass {
	private static SingletonClass sc= null;
	IntroFrame intro;
	GameFrame game;
	
	private SingletonClass() {
		intro = new IntroFrame();
		game = new GameFrame();
	}
	public static void setSc() {
		sc = null;
	}
	public static SingletonClass getInstance() {
		if(sc==null) {
			sc = new SingletonClass();
		}
		return sc;
	}
}
