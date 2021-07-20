package nl.tsai.airosearch.search;

import java.util.List;

public interface SearchRepository<T> {

    List<T> searchByTerm(String term, int size, int page);

    List<T> searchSingleByTerm(String term);
}
