/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchengineproject;

import java.util.ArrayList;

/**
 *
 * @author Timothy
 */
public class tfidfRankedRetrieval implements Strategy {

    @Override
    public ArrayList<AccumulatorPosting> rankingAlgorithm(String query, 
            DiskInvertedIndex dindex) {
        System.out.println("tdidf");
        
        return null;
    }
    
}