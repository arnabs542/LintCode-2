// Given a list of words and an integer k, return the top k frequent words in  
// the list.

//  Notice

// You should order the words by the frequency of them in the return list, the
// most frequent one comes first. If two words has the same frequency, the one
// with lower alphabetical order come first.

// ["work", "do", "happy", "good", "this", "this"] 2
// ["this", "do"]

// What we need is a maxHeap for the count
// We are gonna construct this data type using -- string word --, -- int count
// --,

// Example
// Given

// [
//     "yes", "lint", "code",
//     "yes", "code", "baby",
//     "you", "baby", "chrome",
//     "safari", "lint", "code",
//     "body", "lint", "code"
// ]
// for k = 3, return ["code", "lint", "baby"].

// for k = 4, return ["code", "lint", "baby", "yes"],

class Pair {
    String key;
    int value;
    
    Pair(String key, int value) {
        this.key = key;
        this.value = value;
    }
}

public class Solution {
    /**
     * @param words an array of string
     * @param k an integer
     * @return an array of string
     */
     
    private Comparator<Pair> pairComparator = new Comparator<Pair>() {
        public int compare(Pair left, Pair right) {
            if (left.value != right.value) {
                return left.value - right.value;
            }
            return right.key.compareTo(left.key);
        }
    };
    
    public String[] topKFrequentWords(String[] words, int k) {
        if (k == 0) {
            return new String[0];
        }
        
        HashMap<String, Integer> counter = new HashMap<>();
        for (String word : words) {
            if (counter.containsKey(word)) {
                counter.put(word, counter.get(word) + 1);
            } else {
                counter.put(word, 1);
            }
        }
        
        PriorityQueue<Pair> Q = new PriorityQueue<Pair>(k, pairComparator);
        for (String word : counter.keySet()) {
            Pair peak = Q.peek();
            Pair newPair = new Pair(word, counter.get(word));
            if (Q.size() < k) {
                Q.add(newPair);
                // a new pair count that is larger that the min, then add it to
                // the
                // minHeap
            } else if (pairComparator.compare(newPair, peak) > 0) {
                Q.poll();
                Q.add(new Pair(word, counter.get(word)));
            }
        }
        
        String[] result = new String[k];
        int index = k - 1;
        while (!Q.isEmpty()) {
            result[index--] = Q.poll().key;
        }
        
        return result;
     }
}

/*
1. The Comparator!!! I'll just write it inside the Solution and call it a
private
method:
    private Comparator<Pair> pairComparator = new Comparator<Pair>() {
        public int compare(Pair left, Pair right) {
            if (left.value != right.value) {
                return left.value - right.value;
            }
            return right.key.compareTo(left.key);
        }
    };
2. When and where to use minHeap or MaxHeap!!!
3. Always wants to use hash map
4. The hardest part:
    for (String word : counter.keySet()) {
        Pair peak = Q.peek();
        Pair newPair = new Pair(word, counter.get(word));
        if (Q.size() < k) {
            Q.add(newPair);
        } else if (pairComparator.compare(newPair, peak) > 0) {
            Q.poll();
            Q.add(new Pair(word, counter.get(word)));
        }
    }
*/