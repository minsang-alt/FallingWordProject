import java.awt.Color;
import java.awt.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Ranking extends JDialog {
	static String fileName = "ranking.txt";
	
	
	HashMap<String,Integer>rankingHashMap  = new HashMap<>();
	
	public Ranking() {
		setTitle("순위표");
		setContentPane(new MakeRankingTable());
		setSize(500,570);
		setVisible(true);
	}
	
	public static void saveScore(String name, int score) {
		File f = new File(fileName);
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(fileName,true));
			PrintWriter pw = new PrintWriter(bw,true);
			pw.write("\n"+name + " " + Integer.toString(score));
			pw.flush();
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public class MakeRankingTable extends JPanel {
		
		private Vector<Pair> rankingVector;
		
		
		public MakeRankingTable() {
			
			readRankingFile();
			
			//정렬된 벡터
			Vector<Pair> rankingVector = saveVector();
			
			setLayout(null);
			
			//10등까지만 순위표에 표시
			for(int i=0; i<10; i++) {
				Pair a = rankingVector.get(i);
				String name = a.key;
				int score = a.value;
				
				JLabel la = makeRankingLabel(name,score,i);
				la.setLocation(30,20+i*50);
				add(la);
			}
			
			
		}
		
		public JLabel makeRankingLabel(String name, int score ,int i) {
			String rank = Integer.toString(i+1);
			String sc = Integer.toString(score);
			JLabel la = new JLabel("Rank "+rank+" " + name+ " "+sc);
			la.setSize(300,50);
			la.setForeground(Color.BLACK);
			return la;
		}
		
		
		
		
	}
	
	
	
	public void readRankingFile() {
		try {
			Scanner fscanner = new Scanner(new FileReader(new File(fileName)));
			
			while (fscanner.hasNext()) {
				String name = fscanner.next();
				String score = fscanner.next();
				
				if(rankingHashMap.get(name)!=null) {
					int com = rankingHashMap.get(name);
					if(com < Integer.parseInt(score)) rankingHashMap.put(name, Integer.parseInt(score));
				}
				else {
				rankingHashMap.put(name, Integer.parseInt(score));
				
			
				}
			}
			fscanner.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		

	}
	
	//벡터에 저장후 정렬
	public Vector<Pair> saveVector() {
		Vector<Pair> rankingVector = new Vector<>();
		Set<String> keys = rankingHashMap.keySet();
		Iterator<String> it = keys.iterator();
		while(it.hasNext()) {
			String key = it.next();
			int value = rankingHashMap.get(key);
			rankingVector.add(new Pair(key,value));
		}
		
		rankingVector = descendingSortValue(rankingVector);
		
		return rankingVector;
	}
	
	
	//내림차순 정렬
	
	public Vector<Pair> descendingSortValue(Vector<Pair> rankingVector) {

		 Collections.sort(rankingVector, new ScoreComparator());
		 
		 return rankingVector;
	}
	
	//내림차순 정렬을 위한 비교함수
	class ScoreComparator implements Comparator<Object>{
		public int compare(Object arg0, Object arg1) {
			   return ((Pair)arg1).value - ((Pair)arg0).value;
			 }
	}
		  

}
class Pair{
	String key;
	Integer value;
	
	public Pair(String key,Integer value) {
		this.key = key;
		this.value = value;
	}
	public String getKey() {
		return key;
	}
	public Integer getValue() {
		return value;
	}
}