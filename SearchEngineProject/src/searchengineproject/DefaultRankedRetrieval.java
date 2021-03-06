/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchengineproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.PriorityQueue;

/**
 *
 * @author Timothy
 */
public class DefaultRankedRetrieval implements Strategy {
    /**
     * Uses the default formula to calculate the ranking score
     * @param query query string
     * @param dindex DiskInvertedIndex
     * @return 
     */
    @Override
    public ArrayList<AccumulatorPosting> rankingAlgorithm(String query,
            DiskInvertedIndex dindex) {
        String[] tokens = query.split(" ");
        HashMap<Integer, Double> accumulatorMap = new HashMap<>();
        ArrayList<AccumulatorPosting> results = new ArrayList<>();
        double n = dindex.getDocumentCount();
        
        // Loops through the each term in the query
        for(int i = 0; i < tokens.length; i++) {
            // Get the list of positional posting for the term
            ArrayList<PositionalPosting> posPostList = dindex.GetPostings(
                    PorterStemmer.processToken(tokens[i]), true);
            // Get dft which is the size of the positional posting array list
            if(posPostList == null) {
                return null;
            }
            
            double dft = posPostList.size();
            // Get wqt from the formula
            double wqt = Math.log(1 + (n/dft));
            double accumulator = 0;
            
            // Loop through the positional posting in the array list
            for(int j = 0; j < posPostList.size(); j++) {
                PositionalPosting posPost = posPostList.get(j);
                
                // Check if there is accumulator value already in the hashmap
                if(accumulatorMap.containsKey(posPost.getDocID())) {
                    accumulator = accumulatorMap.get(posPost.getDocID());
                }
                // Else set accumulator to 0
                else {
                    accumulator = 0;
                }
                
                // Get tftd which is the term frequency in the document which 
                // is the size of the positions array list
                double tftd = posPost.getPositions().size();
                // Get wdt from the following formula
                double wdt =  1 + Math.log(tftd);
                // Increment accumulator by multiplying wdt with wqt
                accumulator += wdt * wqt;
                // Map the document id to the accumulator
                accumulatorMap.put(posPost.getDocID(), accumulator);
            }
        }
        
        // Create a new priority queue based on the size of the accumulator map
        // and use the comparator in AccumulatorPQSort.java
        PriorityQueue<AccumulatorPosting> pq = new PriorityQueue<>(
                accumulatorMap.size(), new AccumulatorPQSort());
        
        // Loop through each entry in the accumulator map
        for(Entry<Integer, Double> entry : accumulatorMap.entrySet()) {
            // Check if the entry is non-zero
            if(entry.getValue() > 0) {
                // Get the document weight from the file
                double ld = dindex.getDocWeight(entry.getKey());
                // Create a new accumulator posting object
                AccumulatorPosting ap = new AccumulatorPosting(entry.getKey(),
                        entry.getValue()/ld);
                // Add the posting to the priority queue
                pq.add(ap);
            }
        }
        
        // Loop through the first 10 entries in the priority queue, break if
        // there are less than 10
        for(int i = 0; i < 10; i++) {
           if(pq.peek() != null) {
               results.add(pq.remove());
           }
           else {
               break;
           }
        }
        
        return results;
    }   
}
