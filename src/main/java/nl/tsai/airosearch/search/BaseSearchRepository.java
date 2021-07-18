package nl.tsai.airosearch.search;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.query.dsl.QueryBuilder;

public abstract class BaseSearchRepository<T> implements SearchRepository<T> {

    private final Class<T> entityClazz;
    private final FullTextEntityManager fullTextEntityManager;

    public BaseSearchRepository(FullTextEntityManager fullTextEntityManager, Class<T> entityClazz) {
        this.fullTextEntityManager = fullTextEntityManager;
        this.entityClazz = entityClazz;
    }

    protected QueryBuilder getQueryBuilder() {
        return fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(entityClazz).get();
    }

    protected FullTextQuery createFullTextQuery(Query query) {
        return fullTextEntityManager.createFullTextQuery(query, entityClazz);
    }

}
