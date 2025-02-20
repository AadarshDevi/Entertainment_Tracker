package main.java.backend.SearchEngine;

import java.util.ArrayList;

/**
 * The SearchHistory stores the previous history for easy access to previous
 * data when search is being backtracked.
 */
public class SearchHistory {

    /**
     * the search history contains the arraylist of searches made int he past
     */
    private ArrayList<ArrayList<String>> searchHistory;

    /**
     * the original list is the arraylist containing the original data
     */
    private ArrayList<String> originalList;

    /**
     * the maximum times the SearchHistory can save
     */
    private int maxHistoryLength;

    /**
     * the number of times SearchHistory has saved
     */
    private int historyLength;

    /**
     * the defult value for maximum length of the save arraylist
     */
    private final int MAX_HISTORY_LENGTH = 12;

    public SearchHistory() {
        historyLength = 0;
        maxHistoryLength = MAX_HISTORY_LENGTH;
        searchHistory = new ArrayList<>();
    }

    public SearchHistory(int maxHistoryLength) {
        this.historyLength = 0;
        this.maxHistoryLength = maxHistoryLength;
        searchHistory = new ArrayList<>();
    }

    public SearchHistory(ArrayList<String> originalList) {
        this.historyLength = 0;
        maxHistoryLength = MAX_HISTORY_LENGTH;
        this.originalList = originalList;
        searchHistory = new ArrayList<>();
    }

    public SearchHistory(int maxHistoryLength, ArrayList<String> originalList) {
        this.historyLength = 0;
        this.maxHistoryLength = maxHistoryLength;
        this.originalList = originalList;
        searchHistory = new ArrayList<>();
    }

    public void clearHistory() {
        searchHistory.clear();
        historyLength = 0;
    }

    public void addHistory(ArrayList<String> history) {

        if (searchHistory.size() >= maxHistoryLength)
            searchHistory.remove(0);

        searchHistory.add(history);
        historyLength = Math.min(historyLength + 1, maxHistoryLength);
    }

    public void removeHistory() {
        if (!searchHistory.isEmpty()) {
            searchHistory.removeLast();
            historyLength--;
        } else
            throw new IllegalStateException("Cannot remove from empty history");

    }

    public ArrayList<String> getHistory() {
        if (searchHistory.isEmpty())
            throw new IllegalStateException("Search history is empty");

        return searchHistory.getLast();
    }

    public boolean isEmpty() {
        return searchHistory.isEmpty();
    }

    public int getHistorySize() {
        return searchHistory.size();
    }

    public ArrayList<String> getHistoryAtIndex(int index) {
        if (index >= 0 && index < searchHistory.size()) {
            return new ArrayList<>(searchHistory.get(index));
        }
        throw new IndexOutOfBoundsException("Invalid history index");
    }

    public ArrayList<ArrayList<String>> getAllHistory() {
        return new ArrayList<>(searchHistory);
    }

    public ArrayList<String> getOriginalList() {
        return originalList;
    }

    public void setOriginalList(ArrayList<String> originalList) {
        this.originalList = originalList;
    }
}
