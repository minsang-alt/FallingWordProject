import java.io.*;
import java.util.Scanner;
import java.util.Vector;
public class WordList {
	private Vector<String> wordVector = new Vector<String>();
	
	public WordList() {
		wordVector.add("coputer");
		wordVector.add("Java");
		wordVector.add("I love you");
		wordVector.add("숙제는 더 싫다");
		wordVector.add("공부는 재밌다.");
		wordVector.add("You make me happy");
		
		try {
			Scanner scanner = new Scanner(new FileReader("words.txt"));
			while(scanner.hasNext()) {
				String word = scanner.nextLine();
				wordVector.add(word);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public String getWord() {
		int index  = (int)(Math.random()*wordVector.size());
		return wordVector.get(index);
				
	}
}
