package main.java.backend.SearchEngine;

import java.util.ArrayList;

public class IncrementalSearch {

    private ArrayList<String> originalList;
    private ArrayList<String> currentSearchList;

    // private SearchHistory searchHistory;
    private StringBuilder searchBuilder;

    public IncrementalSearch(int maxHistoryLength) {
        searchBuilder = new StringBuilder();
        // searchHistory = new SearchHistory(maxHistoryLength, originalList);
    }

    public IncrementalSearch(int maxHistoryLength, ArrayList<String> originalList) {
        this.originalList = originalList;
        currentSearchList = new ArrayList<>(originalList);
        searchBuilder = new StringBuilder();
        // searchHistory = new SearchHistory(maxHistoryLength, originalList);
    }

    public IncrementalSearch(ArrayList<String> originalList) {
        this.originalList = originalList;
        currentSearchList = new ArrayList<>(originalList);
        searchBuilder = new StringBuilder();
        // searchHistory = new SearchHistory(originalList);
    }

    public ArrayList<Integer> simpleSearch(String phrase) {

        resetEngine();

        ArrayList<Integer> searchIndexes = new ArrayList<>();

        int i = 1;

        for (String string : originalList) {
            if (string.toLowerCase().contains(phrase.toLowerCase())) {
                searchIndexes.add(i);
                System.out.println("Index: " + i + " :: String: " + string.substring(9, 25));
            }
            i++;
        }
        System.out.println();

        return searchIndexes;
    }

    public ArrayList<Integer> search(String phrase) {

        resetEngine();

        ArrayList<Integer> searchIndexes = new ArrayList<>();
        ArrayList<String> filteredResults = new ArrayList<>();

        System.out.println(">>> Length: " + currentSearchList.size());

        String[] searchedWords = phrase.toLowerCase().split("\\s+");

        for (int i = 0; i < currentSearchList.size(); i++) {

            String currentItem = currentSearchList.get(i).toLowerCase();

            boolean allWordsFound = true;

            // NEW: Check each word in the search phrase
            for (String word : searchedWords) {
                if (!currentItem.contains(word)) {
                    allWordsFound = false;
                    break;
                }
            }

            // MODIFIED: Only add if all words are found
            if (allWordsFound) {
                filteredResults.add(currentSearchList.get(i));
                searchIndexes.add(i + 1);
            }
        }

        currentSearchList = filteredResults;
        // searchHistory.addHistory(currentSearchList);
        System.out.println(">>> Items: " + searchIndexes.size());

        return searchIndexes;
    }

    public StringBuilder getSearchBuilder() {
        return searchBuilder;
    }

    public ArrayList<String> getCurrentSearchList() {
        return currentSearchList;
    }

    public ArrayList<String> getOriginalList() {
        return originalList;
    }

    public void setOriginalList(ArrayList<String> originalList) {
        this.originalList = originalList;
        currentSearchList = new ArrayList<>(originalList);
    }

    // reset current search list
    public void resetEngine() {
        currentSearchList = new ArrayList<>(originalList);
    }

    // public void backTrack() {
    // if (searchHistory.getHistorySize() > 1) {
    // // Remove last history entry
    // searchHistory.removeHistory();

    // // Restore previous search state
    // currentSearchList.clear();
    // currentSearchList.addAll(searchHistory.getHistory());

    // // Remove last character from search builder
    // if (searchBuilder.length() > 0) {
    // searchBuilder.deleteCharAt(searchBuilder.length() - 1);
    // }
    // } else {
    // // Reset to original state if no history
    // currentSearchList.clear();
    // currentSearchList.addAll(originalList);
    // searchBuilder.setLength(0);
    // searchHistory = new SearchHistory(originalList);
    // System.out.println("SearchHistory: Reset");
    // }
    // }

    // public SearchHistory getSearchHistory() {
    // return searchHistory;
    // }
}