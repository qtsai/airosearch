package nl.tsai.airosearch.country;

import nl.tsai.airosearch.search.BaseSearchRepository;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CountrySearchRepository extends BaseSearchRepository<Country> {

    public CountrySearchRepository(FullTextEntityManager fullTextEntityManager) {
        super(fullTextEntityManager, Country.class);
    }

    public List<Country> searchByTerm(String term, int size, int page) {
        final QueryBuilder queryBuilder = getQueryBuilder();
        final Query query = getQueryBuilder().bool()
                .should(exactCodeMatching(queryBuilder, term))
                .should(exactNameMatching(queryBuilder, term))
                .should(wildcardNameMatching(queryBuilder, term))
                .should(fuzzyNameMatching(queryBuilder, term))
                .createQuery();
        FullTextQuery fullTextQuery = createFullTextQuery(query).setMaxResults(size).setFirstResult(size * (page - 1));
        @SuppressWarnings("unchecked") final List<Country> results = fullTextQuery.getResultList();
        return results;
    }

    public List<Country> searchSingleByTerm(String term) {
        return searchByTerm(term, 1, 1);
    }

    private Query exactCodeMatching(QueryBuilder qb, String term) {
        return qb.keyword()
                .boostedTo(100f)
                .onField("code")
                .matching(term)
                .createQuery();
    }

    private Query exactNameMatching(QueryBuilder qb, String term) {
        return qb.keyword()
                .boostedTo(50f)
                .onField("name")
                .matching(term)
                .createQuery();
    }

    private Query wildcardNameMatching(QueryBuilder qb, String term) {
        return qb.keyword().wildcard()
                .boostedTo(20f)
                .onField("name")
                .matching(term.toLowerCase() + "*")
                .createQuery();
    }

    private Query fuzzyNameMatching(QueryBuilder qb, String term) {
        return qb.keyword()
                .boostedTo(1f)
                .fuzzy()
                .withEditDistanceUpTo(1)
                .onField("name")
                .matching(term)
                .createQuery();
    }
}
