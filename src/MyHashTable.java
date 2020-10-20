
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;


public class MyHashTable<K,V> implements Iterable<HashPair<K,V>>{
    // num of entries to the table
    private int numEntries;
    // num of buckets 
    private int numBuckets;
    // load factor needed to check for rehashing 
    private static final double MAX_LOAD_FACTOR = 0.75;
    // ArrayList of buckets. Each bucket is a LinkedList of HashPair
    private ArrayList<LinkedList<HashPair<K,V>>> buckets; 
    
    // constructor
    public MyHashTable(int initialCapacity) {
        // ADD YOUR CODE BELOW THIS
    	
    	// set number of buckets to the input int
        numBuckets = initialCapacity;
        
        // initialize numEntries to 0, because no entries yet
        numEntries = 0;
        
        // initialize buckets
        buckets = new ArrayList<LinkedList<HashPair<K,V>>>();
        for (int i = 0; i< numBuckets; i++) {
        	buckets.add(new LinkedList<HashPair<K, V>>());
        }
        //ADD YOUR CODE ABOVE THIS
    }
    
    public int size() {
        return this.numEntries;
    }
    
    public boolean isEmpty() {
        return this.numEntries == 0;
    }
    
    public int numBuckets() {
        return this.numBuckets;
    }
    
    /**
     * Returns the buckets variable. Useful for testing  purposes.
     */
    public ArrayList<LinkedList< HashPair<K,V> > > getBuckets(){
        return this.buckets;
    }
    
    /**
     * Given a key, return the bucket position for the key. 
     */
    public int hashFunction(K key) {
        int hashValue = Math.abs(key.hashCode())%this.numBuckets;
        return hashValue;
    }
    
    /**
     * Takes a key and a value as input and adds the corresponding HashPair
     * to this HashTable. Expected average run time  O(1)
     */
    
    public V put(K key, V value) {
    	
    	// if there are not any buckets in the hash table, add two new buckets and add the hash pair to the correct bucket
    	if (isEmpty()) {
    		buckets.add(new LinkedList<HashPair<K, V>>());
    		buckets.add(new LinkedList<HashPair<K, V>>());
    		numBuckets = buckets.size(); // set numBuckets
    		buckets.get(hashFunction(key)).add(new HashPair<K, V>(key, value));
    		numEntries++;
        	return null;
    	}
    	// look through the bucket with hash value associated to key and if the key already is mapped, set value to value
    	LinkedList<HashPair<K, V>> bucket = buckets.get(hashFunction(key));
    	
		for (HashPair<K, V>  h: bucket) {
			if (h.getKey().equals(key)) {
				V toReturn = h.getValue();
				h.setValue(value);
				return toReturn;
			}
		}
		// if the key is not already mapped in the hash table then add a new hashpair with key and value
		bucket.add(new HashPair<K, V>(key, value));
		numEntries++;
    	if ((double) numEntries/ (double) numBuckets > MAX_LOAD_FACTOR) rehash(); // rehash if load factor exceeds max
    	return null;
    }
    
    /**
     * Get the value corresponding to key. Expected average runtime O(1)
     */
    
    public V get(K key) {
        for (HashPair <K, V> h: buckets.get(hashFunction(key))) {
        	if (h.getKey().equals(key)) return h.getValue();
        }
    	return null;
    }
    
    /**
     * Remove the HashPair corresponding to key . Expected average runtime O(1) 
     */
    public V remove(K key) {
    	V toReturn = get(key);
    	
    	// pointer at the hash pair to remove
    	HashPair<K, V> toRemove = null;
    	
    	// if the value is in the hash table, set pointer to hash pair to remove
    	if (toReturn != null) {
    		for (HashPair<K, V> h : buckets.get(hashFunction(key))) {
    			if (h.getKey().equals(key)) {
    				toRemove = h;
    				break;
    			}
    		// remove the desired hash pair from bucket
    		}buckets.get(hashFunction(key)).remove(toRemove);
    		numEntries--;
    	}return toReturn;
    }
    
    
    /** 
     * Method to double the size of the hashtable if load factor increases
     * beyond MAX_LOAD_FACTOR.
     * Made public for ease of testing.
     * Expected average runtime is O(m), where m is the number of buckets
     */
    
    public void rehash() {
    	numBuckets = numBuckets * 2;
    	// create new hash table with double the number of buckets
    	ArrayList<LinkedList<HashPair<K, V>>> newTable = new ArrayList<LinkedList<HashPair<K, V>>>();
    	for (int i = 0; i < numBuckets; i++) {
    		newTable.add(new LinkedList<HashPair<K, V>>());
    	}
    	for (HashPair<K, V> pair: this) {
    		// add each hash pair to its correct bucket with the new number of buckets in the new array list
    		newTable.get(hashFunction(pair.getKey())).add(pair);
    	}
    	// set new array list to buckets
    	buckets = newTable;
    	 
    }
    
    
    /**
     * Return a list of all the keys present in this hashtable.
     * Expected average runtime is O(m), where m is the number of buckets
     */
    
    public ArrayList<K> keys() {
        // create a new array list to add the keys to 
    	ArrayList<K> toReturn = new ArrayList<K>();
    	
    	// iterate through each bucket in the hash table and add the keys of the entries into the array list
    	for (HashPair<K, V> h : this) {
    		if (h != null) toReturn.add(h.getKey());
    	}
    	return toReturn;
    }
    
    /**
     * Returns an ArrayList of unique values present in this hashtable.
     * Expected average runtime is O(m) where m is the number of buckets
     */
    public ArrayList<V> values() {
    	// store the values in the table in hash pairs with <V, V>
        MyHashTable<V, V> valueTable = new MyHashTable<V, V>(1);
        for (HashPair<K, V> h : this) {
        	V value = h.getValue();
        	valueTable.put(value, value);
        }
        // the keys are unique values in the valueTable
        ArrayList<V> toReturn = valueTable.keys();
        
    	return toReturn;
    }
    
    
	/**
	 * This method takes as input an object of type MyHashTable with values that 
	 * are Comparable. It returns an ArrayList containing all the keys from the map, 
	 * ordered in descending order based on the values they mapped to. 
	 * 
	 * The time complexity for this method is O(n^2), where n is the number 
	 * of pairs in the map. 
	 */
    public static <K, V extends Comparable<V>> ArrayList<K> slowSort (MyHashTable<K, V> results) {
        ArrayList<K> sortedResults = new ArrayList<>();
        for (HashPair<K, V> entry : results) {
			V element = entry.getValue();
			K toAdd = entry.getKey();
			int i = sortedResults.size() - 1;
			V toCompare = null;
        	while (i >= 0) {
        		toCompare = results.get(sortedResults.get(i));
        		if (element.compareTo(toCompare) <= 0 )
        			break;
        		i--;
        	}
        	sortedResults.add(i+1, toAdd);
        }
        return sortedResults;
    }
    
    
	/**
	 * This method takes as input an object of type MyHashTable with values that 
	 * are Comparable. It returns an ArrayList containing all the keys from the map, 
	 * ordered in descending order based on the values they mapped to.
	 * 
	 * The time complexity for this method is O(n*log(n)), where n is the number 
	 * of pairs in the map. 
	 */
    
    public static <K, V extends Comparable<V>> ArrayList<K> fastSort(MyHashTable<K, V> results) {
    	
    	ArrayList<HashPair<K, V>> toSort = new ArrayList<HashPair<K, V>>();
    	
    	// get arrayList of the hash pairs in the table
    	for (HashPair<K, V> h : results) {
    		toSort.add(h);
    	}
    	ArrayList<HashPair<K, V>> sorted = quickSort(toSort, 0, toSort.size() - 1); // quicksort hash pairs
    	
    	// return an ArrayList with the keys in the sorted hash pair ArrayList
    	ArrayList<K> toReturn = new ArrayList<K>();
    	for (HashPair<K, V> h : sorted) {
    		toReturn.add(h.getKey());
    	}return toReturn;
    }
    
    public static <K, V extends Comparable<V>> ArrayList<HashPair<K, V>> quickSort(ArrayList<HashPair<K, V>> toSort, int low, int high){
    	if (low < high) {
    		// everything w/ index > pi is less than element at pi and vice versa for index less than pi
    		int pi = partition(toSort, low, high);
    		
    		// sort the two sides of the partition
    		// recursive
    		quickSort(toSort, low, pi-1);
    		quickSort(toSort, pi + 1, high);
    	}return toSort;
    }
    
    public static <K, V extends Comparable<V>> int partition(ArrayList<HashPair<K, V>> toSort, int low, int high) {
    	int smallIndex = low -1;
    	HashPair<K, V> pivot = toSort.get(high); // use last element in ArrayList as pivot
    	
    	while (low < high) { // iterate through ArrayList
    		
    		if (toSort.get(low).getValue().compareTo(pivot.getValue()) > 0) { // if element with low index > pivot
    			smallIndex++;
    			swap(toSort, smallIndex, low);
    		}low++; // happens in every iteration
    		
    	}swap(toSort, smallIndex + 1, high); // put the pivot in the correct position
    	return smallIndex + 1; // return the index of the partition
    }
    
    // helper function to swap elements in an ArrayList
    // assumes pos1 and pos2 are both valid indices in ArrayList
    public static <K, V extends Comparable<V>> void swap(ArrayList<HashPair<K, V>> toSort, int pos1, int pos2) {
    	HashPair<K, V> temp = toSort.get(pos1);
    	HashPair<K, V> h = toSort.get(pos2);
    	toSort.set(pos1, h);
    	toSort.set(pos2, temp);
    }
    
    @Override
    public MyHashIterator iterator() {
        return new MyHashIterator();
    }   
    
    private class MyHashIterator implements Iterator<HashPair<K,V>> {
        //ADD YOUR CODE BELOW HERE
    	
    	//LinkedList<HashPair<K, V>> curBucket;
    	HashPair<K, V> current;
    	HashPair<K, V>[] pairs;
    	int index;
    	
        //ADD YOUR CODE ABOVE HERE
    	/**
    	 * Expected average runtime is O(m) where m is the number of buckets
    	 */
        private MyHashIterator() {
            //ADD YOUR CODE BELOW HERE
        	
        	pairs = new HashPair[numEntries];
        	int i= 0;
        	for (LinkedList<HashPair<K, V>> bucket: buckets) {
        		if (bucket.size() != 0) {
	        		for (HashPair<K, V> h: bucket) {
	        			pairs[i] = h;
	        			i++;
	        		}
        		}
        	}
        	if (numEntries != 0) {
        		current = pairs[0];
        		index = 0;
        	}
            //ADD YOUR CODE ABOVE HERE
        }
        
        @Override
        /**
         * Expected average runtime is O(1)
         */
        public boolean hasNext() {
            //ADD YOUR CODE BELOW HERE
        	
        	// if current is pointing to an entry and not to null
        	if (index != numEntries) return true;
        	
        	return false;
        	
            //ADD YOUR CODE ABOVE HERE
        }
        
        @Override
        /**
         * Expected average runtime is O(1)
         */
        public HashPair<K,V> next() {
            //ADD YOUR CODE BELOW HERE
        	
        	// the hash pair that will be returned
        	HashPair<K, V> toReturn;
        	toReturn = current;
        	if (hasNext()) {
        		index++;
        		if (index != numEntries) current = pairs[index];
        	}
        	
        	return toReturn;
        	
            //ADD YOUR CODE ABOVE HERE
        }
        
    }
}
