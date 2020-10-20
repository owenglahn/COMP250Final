import java.util.ArrayList;

public class Twitter {
	
	//ADD YOUR CODE BELOW HERE
	private MyHashTable<String, Tweet> tweetByAuthor;
	private MyHashTable<String, ArrayList<Tweet>> tweetByDate;
	private MyHashTable<String, String> stops;
	private MyHashTable<Tweet, Tweet> allTweets;
	//private ArrayList<Tweet> allTweets;
	//ADD CODE ABOVE HERE 
	
	// O(n+m) where n is the number of tweets, and m the number of stopWords
	public Twitter(ArrayList<Tweet> tweets, ArrayList<String> stopWords) {
		//ADD YOUR CODE BELOW HERE
		tweetByAuthor = new MyHashTable<String, Tweet>(10);
		tweetByDate = new MyHashTable<String, ArrayList<Tweet>>(5);
		allTweets = new MyHashTable<Tweet, Tweet>(2);
		stops = new MyHashTable<String, String>(2);
		for (Tweet t : tweets) {
			
			Tweet checkAuthor = tweetByAuthor.get(t.getAuthor());
			if (checkAuthor != null) { // not the author's first tweet
				if (t.getDateAndTime().compareTo(checkAuthor.getDateAndTime()) > 0) {
					tweetByAuthor.put(t.getAuthor(), t);
				}
			}else tweetByAuthor.put(t.getAuthor(), t); // if this is the author's first tweet
			
			allTweets.put(t, t); // table of every tweet
			
			// only the date and not the time
			if (tweetByDate.get(t.getDateAndTime().substring(0, 10)) != null) {
				tweetByDate.get(t.getDateAndTime().substring(0, 10)).add(t);
			}else {
				ArrayList<Tweet> toAdd = new ArrayList<Tweet>();
				toAdd.add(t);
				tweetByDate.put(t.getDateAndTime().substring(0, 10), toAdd);
			}
		}
		// hash table for stop words so that they can be accessed in O(1)
		for (String stop : stopWords) {
			stops.put(stop, stop);
		}
		
		//ADD CODE ABOVE HERE 
	}
	
	
    /**
     * Add Tweet t to this Twitter
     * O(1)
     */
	public void addTweet(Tweet t) {
		//ADD CODE BELOW HERE
		allTweets.put(t, t);
		Tweet check = tweetByAuthor.get(t.getAuthor());
		
		// if the author already has a tweet, override with the most recent tweet
		if (check != null) {
			if (t.getDateAndTime().compareTo(check.getDateAndTime()) > 0) {
				tweetByAuthor.put(t.getAuthor(), t);
			}
		}
		// if there are already tweets from that date, add the tweet to the tweets from that date
		ArrayList<Tweet> checkDate = tweetByDate.get(t.getDateAndTime().substring(0, 10));
		if (checkDate != null) {
			checkDate.add(t);
		// otherwise create a new array list to add the tweets from that date to
		}else {
			ArrayList<Tweet> toAdd = new ArrayList<Tweet>();
			toAdd.add(t);
			tweetByDate.put(t.getDateAndTime().substring(0, 10), toAdd);
		}
		//ADD CODE ABOVE HERE 
	}
	 

    /**
     * Search this Twitter for the latest Tweet of a given author.
     * If there are no tweets from the given author, then the 
     * method returns null. 
     * O(1)  
     */
    public Tweet latestTweetByAuthor(String author) {
        //ADD CODE BELOW HERE

    	// only the latest tweet by the author is in the hash table, return the tweet by that author
    	return tweetByAuthor.get(author);
    	
        //ADD CODE ABOVE HERE 
    }


    /**
     * Search this Twitter for Tweets by `date' and return an 
     * ArrayList of all such Tweets. If there are no tweets on 
     * the given date, then the method returns null.
     * O(1)
     */
    public ArrayList<Tweet> tweetsByDate(String date) {
        //ADD CODE BELOW HERE
    	
    	// already has values that are array lists, return the tweets from that date
    	return tweetByDate.get(date);
    	
        //ADD CODE ABOVE HERE
    }
    
	/**
	 * Returns an ArrayList of words (that are not stop words!) that
	 * appear in the tweets. The words should be ordered from most 
	 * frequent to least frequent by counting in how many tweet messages
	 * the words appear. Note that if a word appears more than once
	 * in the same tweet, it should be counted only once. 
	 */
    public ArrayList<String> trendingTopics() {
        //ADD CODE BELOW HERE
    	// iterate through all of the tweets and add the trending topics to a hash table
    	MyHashTable<String, Integer> topics = new MyHashTable<String, Integer>(1);
    	for (Tweet t : allTweets.keys()) {
    		ArrayList<String> words = cleanDuplicates(getWords(t.getMessage())); // all words in tweet with duplicates removed
    		
    		for (String word : words) {
    			if (stops.get(word) == null) { // if the word is not a stop word
	    			if (topics.get(word) != null) { // if the word is already a trending topic
	    				topics.put(word, topics.get(word) + 1);
	    			}else topics.put(word, 1); // if it is not a trending topic
    			}
    		}
    		
    	}
    	// return array list of trending topics sorted from most occurrences to fewest
    	return MyHashTable.fastSort(topics);
    	
        //ADD CODE ABOVE HERE    	
    }
    
    // helper to get rid of duplicates in an array list
    private static ArrayList<String> cleanDuplicates(ArrayList<String> msg){
    	
    	ArrayList<String> toReturn = new ArrayList<String>();
    	
    	for (int i = 0; i < msg.size(); i++) {
    		boolean first = true;
    		String current = msg.get(i);
    		for (int j = 0; j < i; j++) {
    			if (current.equalsIgnoreCase(msg.get(j))) {
    				first = false;
    				break;
    			}
    		}if (first) {
    			toReturn.add(current.toLowerCase());
    		}
    	}
    	return toReturn;
    }
    
    
    /**
     * An helper method you can use to obtain an ArrayList of words from a 
     * String, separating them based on apostrophes and space characters. 
     * All character that are not letters from the English alphabet are ignored. 
     */
    private static ArrayList<String> getWords(String msg) {
    	msg = msg.replace('\'', ' ');
    	String[] words = msg.split(" ");
    	ArrayList<String> wordsList = new ArrayList<String>(words.length);
    	for (int i=0; i<words.length; i++) {
    		String w = "";
    		for (int j=0; j< words[i].length(); j++) {
    			char c = words[i].charAt(j);
    			if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))
    				w += c;
    			
    		}
    		wordsList.add(w);
    	}
    	return wordsList;
    }

    

}
