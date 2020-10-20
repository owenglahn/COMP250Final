import java.util.*;
import java.util.Arrays;
import java.util.LinkedList;

public class aTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LinkedList<HashPair<String, Integer>> test = new LinkedList<HashPair<String, Integer>>();
		HashPair<String, Integer> h1 = new HashPair<String, Integer>("Bob", 7);
		HashPair<String, Integer> h2 = new HashPair<String, Integer>("Joe", 8);
		HashPair<String, Integer> h3 = new HashPair<String, Integer>("Ralph", 9);
		HashPair<String, Integer> h4 = new HashPair<String, Integer>("Tom", 10);
		/*test.add(h1);
		test.add(h2);
		test.add(h3);
		test.add(h4);*/
		for (HashPair<String, Integer> h: test) {
			System.out.println(h.getValue());
			h.setValue(0);
			
		}
		/*HashPair<String, Integer> toRemove = null;
		
		for (HashPair<String, Integer> h: test) {
			if (h.getKey() == "Bob") {
				toRemove = h;
			}
			else h.setValue(0);
			System.out.println(h.getValue());
			System.out.println(h.getKey());
			System.out.print('\n');
		}test.remove(toRemove);
		for (HashPair<String, Integer> h: test) {
			System.out.println(h.getKey());
		}*/
		MyHashTable<String, Integer> testTable = new MyHashTable<String, Integer>(1);
		testTable.put("Owen", 1);
		testTable.put("Bob", 2);
		testTable.put("James", 3);
		testTable.put("Rowan", 4);
		testTable.put("Otto", 5);
		testTable.put("Fonz", 6);
		testTable.put("Jimmy", 6);
		testTable.put("Juan", 88);
		
		System.out.println(MyHashTable.fastSort(testTable).toString());
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.remove(null);
		System.out.println(testTable.values().toString());
		System.out.println(testTable.keys().toString());
		
		for (HashPair<String, Integer> h : testTable) {
			System.out.println(h.getKey() +" " + h.getValue());
		}
		System.out.println("hello".equals("Hello".toLowerCase()));
		
	}

}
