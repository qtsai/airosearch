package nl.tsai.airosearch.search;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Configuration
public class HibernateSearchIndexConfig {

    @Bean
    public FullTextEntityManager fullTextEntityManager(EntityManagerFactory entityManagerFactory) throws InterruptedException {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManagerFactory.createEntityManager());
        fullTextEntityManager.createIndexer().startAndWait();
        return fullTextEntityManager;
    }

}
