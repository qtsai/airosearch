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

    @SuppressWarnings("unchecked")
    public List<Country> searchByTerm(String term, int size, int page) {
        QueryBuilder queryBuilder = getQueryBuilder();
        Query query = getQueryBuilder().bool()
                .should(exactCodeMatching(queryBuilder, term))
                .should(exactNameMatching(queryBuilder, term))
                .should(wildcardNameMatching(queryBuilder, term))
                .should(fuzzyNameMatching(queryBuilder, term))
                .createQuery();
        FullTextQuery fullTextQuery = createFullTextQuery(query);
        return (List<Country>) fullTextQuery
                .setMaxResults(size)
                .setFirstResult(firstResult(size, page))
                .getResultList();
    }

    private int firstResult(int size, int page) {
        if (page < 1) page = 1;
        return size * (page - 1);
    }

    private Query exactCodeMatching(QueryBuilder qb, String term) {
        return qb.keyword()
                .boostedTo(100f)
                .onFields("code")
                .matching(term)
                .createQuery();
    }

    private Query exactNameMatching(QueryBuilder qb, String term) {
        return qb.keyword()
                .boostedTo(50f)
                .onFields("name")
                .matching(term)
                .createQuery();
    }

    private Query wildcardNameMatching(QueryBuilder qb, String term) {
        return qb.keyword().wildcard()
                .boostedTo(20f)
                .onFields("name")
                .matching(String.format("%s*", term))
                .createQuery();
    }

    private Query fuzzyNameMatching(QueryBuilder qb, String term) {
        return qb.keyword()
                .boostedTo(1f)
                .fuzzy()
                .withEditDistanceUpTo(1)
                .onFields("name")
                .matching(term)
                .createQuery();
    }
}
