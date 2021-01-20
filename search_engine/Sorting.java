import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry; // You may need it to implement fastSort

public class Sorting {

	/*
	 * This method takes as input an HashMap with values that are Comparable. 
	 * It returns an ArrayList containing all the keys from the map, ordered 
	 * in descending order based on the values they mapped to. 
	 * 
	 * The time complexity for this method is O(n^2) as it uses bubble sort, where n is the number 
	 * of pairs in the map. 
	 */
    public static <K, V extends Comparable> ArrayList<K> slowSort (HashMap<K, V> results) {
        ArrayList<K> sortedUrls = new ArrayList<K>();
        sortedUrls.addAll(results.keySet());	//Start with unsorted list of urls

        int N = sortedUrls.size();
        for(int i=0; i<N-1; i++){
			for(int j=0; j<N-i-1; j++){
				if(results.get(sortedUrls.get(j)).compareTo(results.get(sortedUrls.get(j+1))) <0){
					K temp = sortedUrls.get(j);
					sortedUrls.set(j, sortedUrls.get(j+1));
					sortedUrls.set(j+1, temp);					
				}
			}
        }
        return sortedUrls;                    
    }
    
    
	/*
	 * This method takes as input an HashMap with values that are Comparable. 
	 * It returns an ArrayList containing all the keys from the map, ordered 
	 * in descending order based on the values they mapped to. 
	 * 
	 * The time complexity for this method is O(n*log(n)), where n is the number 
	 * of pairs in the map. 
	 */
    public static <K, V extends Comparable> ArrayList<K> fastSort(HashMap<K, V> results) {
    	// ADD YOUR CODE HERE
    	
    	ArrayList<K> keys = new ArrayList<K>();
    	
    	Entry<K,V>[] entries = (Entry<K,V>[]) new Entry[results.entrySet().size()];
    	
    	int i = 0;
    	for (Entry<K,V> e : results.entrySet()) {
    		
    		entries[i] = e;
    		i++;
    		
    	}
    	
    	entries = mergeSort(entries);
    	
    	for (Entry<K,V> entry : entries) {
    		
    		keys.add(entry.getKey());
    		
    	}
    	
    	return keys;
    }
    
    // *******MERGESORT*******
    
    private static <K, V extends Comparable> Entry<K,V>[] mergeSort(Entry<K,V>[] entries) {
    	
    	int l = entries.length;
    	
    	if (l == 1) {
    		
    		return entries;
    		
    	} else {
    		
    		int mid = (l - 1) / 2;

    		Entry<K,V>[] list1 = getElements(entries,0,mid);
    		Entry<K,V>[] list2 = getElements(entries,mid+1,l-1);

    		list1 = mergeSort(list1);
    		list2 = mergeSort(list2);
    		
    		return  merge( list1, list2 );
    		
    	}
    	
    }
    
    private static <K, V extends Comparable> Entry<K,V>[] merge(Entry<K,V>[] list1, Entry<K,V>[] list2) {
    	
    	int size1 = list1.length;
    	int size2 = list2.length;
    	int i1 = 0;
    	int i2 = 0;
    	int i = 0;
    	
    	Entry<K,V>[] list = (Entry<K,V>[]) new Entry[size1 + size2];
    	
    	while (i1 < size1 && i2 < size2) {
    		
    		if (list1[i1].getValue().compareTo(list2[i2].getValue()) >= 0) {
    			
    			list[i] = list1[i1]; 
    			i1++;
    			
    		} else {
    			
    			list[i] = list2[i2];
    			i2++;
    			
    		}
    		i++;
    			
    	}
    	
    	while (i1 < size1) {
    		
    		list[i] = list1[i1];
			i1++;
			i++;
    		
    	}
    	
    	while (i2 < size2) {
    		
    		list[i] = list2[i2];
			i2++;
			i++;
    		
    	}
    	
    	return list;
    	
    }
    
    private static <K, V extends Comparable> Entry<K,V>[] getElements(Entry<K,V>[] list, int min, int max) {
    	
    	Entry<K,V>[] arr = (Entry<K,V>[]) new Entry[max-min+1];
    	
    	int j = 0;
    	for (int i = min; i <= max; i++) {
    		
    		arr[j] = list[i];
    		j++;
    		
    	}
    	
    	return arr;
    	
    }
    
    // *******HEAPSORT THAT DOESN'T WORK*******
    private static <K, V extends Comparable> ArrayList<Entry<K,V>> heapSort(ArrayList<Entry<K,V>> entries) {
    	
    	Entry<K,V>[] heap = buildHeap(entries);
    	int l = heap.length;
    	
    	for (int i = 1; i < l-1; i++) {
    		
    		swap(heap[1], heap[l-i]);
    		downHeap(heap,1,l-1);
    		
    	}
    	
    	ArrayList<Entry<K,V>> sortedEntries = new ArrayList<Entry<K,V>>();
    	
    	for (int i = l-1; i >= 1; i--) {
    		
    		sortedEntries.add(heap[i]);
    		
    	}
    	
    	return sortedEntries;
    	
    }
    
    private static <K, V extends Comparable> Entry<K,V>[] buildHeap(ArrayList<Entry<K,V>> entries) {
    	
    	Entry<K,V>[] heap = (Entry<K,V>[]) new Entry[entries.size() + 1];
    	for (int i = 1; i <= entries.size(); i++) {
    		
    		heap[i] = entries.get(i-1);
    		upHeap(heap,i);
    		
    	}
    	return heap;
    	
    }
    
    private static <K, V extends Comparable> void upHeap(Entry<K,V>[] heap, int k) {
    	
    	int i = k;
    	while (i > 1 && heap[i].getValue().compareTo(heap[i/2].getValue()) > 0) {
    		
    		swap(heap[i],heap[i/2]);
    		i /= 2;
    		
    	}
    	
    }
    
    private static <K, V extends Comparable> void downHeap(Entry<K,V>[] heap, int start, int max) {
    	
    	int i = start;
    	int child;
    	while (2*i < max) {
    		
    		child = 2*i;
    		if (child < max) {
    			if (heap[child+1].getValue().compareTo(heap[child].getValue()) > 0) {
    				child++;
    			}
    		}
    		if (heap[child].getValue().compareTo(heap[i].getValue()) > 0) {
    			
    			swap(heap[child],heap[i]);
    			i = child;
    			
    		} else break;
    		
    	}
    	
    }
    
    private static <K, V extends Comparable> void swap(Entry<K,V> e1, Entry<K,V> e2) {
    	
    	Entry<K,V> tmp = e1;
    	e1 = e2;
    	e2 = tmp;
    	
    }

}