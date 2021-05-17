
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.tuple.Pair;

public class Main {
	List<String> tokens = new ArrayList<String>();

	String[] array;

	public Main(String fileName,int topN) throws IOException {
		
		Path mypath = Paths.get(fileName);
		Scanner scanner = new Scanner(mypath);

		while (scanner.hasNextLine()) {
			String temp = scanner.nextLine().toLowerCase().replaceAll("\\p{Punct}", "");
			array = temp.split("\\s+");

			for (int i = 0; i < array.length; i++) {
				tokens.add(array[i]);

			}

		}

	
		computeAvgLengthByFirstChar();
		Set pairs = calculateMinPairDist();
	        	System.out.println(pairs);
	}

	private void computeAvgLengthByFirstChar() {
		tokens.sort(Comparator.naturalOrder());
		Map<Character, Double> map = new LinkedHashMap<>();

		for (int i = 0; i < tokens.size(); i++) {
			// Get the element and move to the loop counter to the next index
			String element = tokens.get(i++);

			double sum = element.length();
			Character ch = Character.valueOf(element.charAt(0));
			int count = 1;

			// Starting with the next index, iterate the list until you find an element with
			// a different initial letter
			while (i < tokens.size() && ch.equals(Character.valueOf(tokens.get(i).charAt(0)))) {
				// Add the length of element and move the outer loop counter to the next index
				sum += tokens.get(i++).length();
				count++;
			}

			map.put(ch, sum / count);

			// Adjust the the outer loop counter by decrementing by 1
			i--;
		}

		// Display the result
		System.out.println(map);
	}

	

	private Set calculateMinPairDist() {
	Set Pairs = new HashSet<>();

   for (int i = 0; i < tokens.size(); i++) {

			for (int j = i + 1; j < tokens.size(); j++) {

				String first_element = tokens.get(i);
				String second_element = tokens.get(j);
				
				if (first_element.equals(second_element)) {
					continue;
				}
			
				
				double distance = calculatTotalDistance(first_element, second_element);
				String s =Double.toString(distance);
			Pairs.add("{t1" + first_element +"t2" + second_element + "factor:" +s + "}");
			
			
		
			}
		
		}

	         
		
		return Pairs;
	}

	public double calculatTotalDistance(String term1, String term2) {
		int freq1 = Collections.frequency(tokens, term1);
		int freq2 = Collections.frequency(tokens, term2);
		int sum = 0;
		for (int i = 1; i < tokens.size(); i++) {
			if (tokens.get(i) != term1) {
				continue;
			}
			for (int j = i; j < tokens.size(); j++) {
				if (tokens.get(j) != term2) {
					continue;
				}
				sum += Math.abs(j - i);
				break;
			}
		}
		return (freq1 * freq2) / (1 + Math.log(sum));

	}

	public static void main(String[] args) throws IOException {
		new Main(args[0],Integer.parseInt(args[1]));

	}
}
