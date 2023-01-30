package com.maxqiu.demo;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.maxqiu.demo.common.ClientUtils;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;

/**
 * @author Max_Qiu
 */
public class QueryTest {
    @BeforeAll
    static void beforeAll() {
        ClientUtils.clientSslWithCaFingerprint();
        // ClientUtils.clientSslWithCaFile();
    }

    @Test
    void normal() throws IOException {
        MatchQuery matchQuery = new MatchQuery.Builder().field("city").query(FieldValue.of("beijing")).build();
        Query query = new Query.Builder().match(matchQuery).build();
        SearchRequest.Builder searchRequestBuilder = new SearchRequest.Builder().index("myindex");
        searchRequestBuilder.query(query);
        SearchRequest searchRequest = searchRequestBuilder.build();
        SearchResponse<Object> search = ClientUtils.client.search(searchRequest, Object.class);
        System.out.println(search);
    }

    @Test
    void lambda() throws IOException {
        SearchResponse<Object> search =
            ClientUtils.client.search(req -> req.query(q -> q.match(m -> m.field("city").query("beijing"))), Object.class);
        System.out.println(search);
    }

    @AfterAll
    static void afterAll() {
        ClientUtils.close();
    }
}
