import java.io.*;
import java.util.Scanner;
import java.util.Vector;
public class WordList {
	private Vector<String> engWordVector = new Vector<String>();
	private Vector<String> korWordVector = new Vector<String>();
	public WordList() {
		
		
		try {
			//영어 텍스트 받아오기
			Scanner scanner = new Scanner(new FileReader("engwords.txt"));
			
			while(scanner.hasNext()) {
				String word = scanner.nextLine();
				engWordVector.add(word);
			}
			//한글 텍스트 받아오기
			scanner = new Scanner(new FileReader("korWord.txt"));
			while(scanner.hasNext()) {
				String word = scanner.nextLine();
				korWordVector.add(word);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public String engGetWord() {
		int index  = (int)(Math.random()*engWordVector.size());
		return engWordVector.get(index);
				
	}
	public String korGetWord() {
		int index  = (int)(Math.random()*korWordVector.size());
		return korWordVector.get(index);
	}
}
