import java.util.HashMap;
import java.util.ArrayList;

public class SearchEngine {
	public HashMap<String, ArrayList<String>> wordIndex; // this will contain a set of pairs (String, LinkedList of
															// Strings)
	public MyWebGraph internet;
	public XmlParser parser;

	public SearchEngine(String filename) throws Exception {
		this.wordIndex = new HashMap<String, ArrayList<String>>();
		this.internet = new MyWebGraph();
		this.parser = new XmlParser(filename);
	}

	/*
	 * This does a graph traversal of the web, starting at the given url. For each
	 * new page seen, it updates the wordIndex, the web graph, and the set of
	 * visited vertices.
	 * 
	 * This method will fit in about 30-50 lines (or less)
	 */
	public void crawlAndIndex(String url) throws Exception {
		// TODO : Add code here

		if (!this.internet.getVisited(url)) {

			for (String word : parser.getContent(url)) {
				
				word = word.toLowerCase();
				ArrayList<String> urls = this.wordIndex.get(word);
				
				if (urls == null) {
					
					urls = new ArrayList<String>();
					urls.add(url);
					wordIndex.put(word, urls);
					
				} else {
					
					if (!urls.contains(url))
						urls.add(url);
					
				}
				
			}
			
			this.internet.addVertex(url);
			this.internet.setVisited(url, true);

			for (String u : this.parser.getLinks(url)) {

				crawlAndIndex(u);
				this.internet.addEdge(url, u);

			}

		}

	}

	/*
	 * This computes the pageRanks for every vertex in the web graph. It will only
	 * be called after the graph has been constructed using crawlAndIndex(). To
	 * implement this method, refer to the algorithm described in the assignment
	 * pdf.
	 * 
	 * This method will probably fit in about 30 lines.
	 */
	public void assignPageRanks(double epsilon) {
		// TODO : Add code here

		ArrayList<String> vertices = this.internet.getVertices();
		
		for (String v :vertices) {
			
			this.internet.setPageRank(v,1.0);
			
		}
		
		ArrayList<Double> lastRanks = getRanks();
		ArrayList<Double> thisRanks = computeRanks(vertices);
		
		while (!converges(lastRanks,thisRanks,epsilon)) {
		
			setRanks(thisRanks);
			lastRanks = thisRanks;
			thisRanks = computeRanks(vertices);
			
		}

	}
	
	private static boolean converges(ArrayList<Double> lastRanks, ArrayList<Double> thisRanks, double epsilon) {

		boolean converges = true;

		int i = 0;
		for (double d : lastRanks) {

			if (Math.abs(d - thisRanks.get(i)) >= epsilon) {

				converges = false;
				break;

			}
			i++;

		}

		return converges;

	}
	
	private ArrayList<Double> getRanks() {
		
		ArrayList<Double> ranks = new ArrayList<Double>();
		
		for (String v : this.internet.getVertices()) {
			
			ranks.add(this.internet.getPageRank(v));
			
		}
		
		return ranks;
		
	}
	
	private void setRanks(ArrayList<Double> ranks) {
		
		int i = 0;
		for (String v : this.internet.getVertices()) {
			
			this.internet.setPageRank(v,ranks.get(i));
			i++;
			
		}
		
	}

	/*
	 * The method takes as input an ArrayList<String> representing the urls in the
	 * web graph and returns an ArrayList<double> representing the newly computed
	 * ranks for those urls. Note that the double in the output list is matched to
	 * the url in the input list using their position in the list.
	 */
	
	
	public ArrayList<Double> computeRanks(ArrayList<String> vertices) {
		// TODO : Add code here

		ArrayList<Double> ranks = new ArrayList<Double>();
		double rank;
		double d = 0.5;
		double sum;
		
		for (String url : vertices) {
			
			sum = 0.0;
			
			for (String u : this.internet.getEdgesInto(url)) {
				
				sum += this.internet.getPageRank(u) / this.internet.getOutDegree(u);
				
			}
			
			rank = (1-d) + d*sum;
			ranks.add(rank);
			
		}
		
		return ranks;
	}

	/*
	 * Returns a list of urls containing the query, ordered by rank Returns an empty
	 * list if no web site contains the query.
	 * 
	 * This method should take about 25 lines of code.
	 */
	public ArrayList<String> getResults(String query) {
		// TODO: Add code here
		
		query = query.toLowerCase();
		ArrayList<String> urls = this.wordIndex.get(query);
		HashMap<String,Double> resultsMap = new HashMap<String,Double>();
		
		for (String url : urls) {
			
			resultsMap.put(url, this.internet.getPageRank(url));
			
		}
		
		ArrayList<String> results = Sorting.fastSort(resultsMap);
		
		return results;
	}
}
