package com.example.productservice.util;

import com.example.productservice.model.ResponsePage;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ElasticSearchUtils {
    public static void mustMatch(BoolQueryBuilder filterBuilder, String path, Object value) {
        if (!ObjectUtils.isEmpty(value)) {
            filterBuilder.must(QueryBuilders.matchQuery(path, value));
        }
    }

    public static void mustTerm(BoolQueryBuilder filterBuilder, String path, Object value) {
        if (!ObjectUtils.isEmpty(value)) {
            filterBuilder.must(QueryBuilders.termQuery(path, value));
        }
    }

    public static void mustNotTerm(BoolQueryBuilder filterBuilder, String path, Object value) {
        if (!ObjectUtils.isEmpty(value)) {
            filterBuilder.mustNot(QueryBuilders.termQuery(path, value));
        }
    }

    public static void mustTerms(BoolQueryBuilder filterBuilder, String path, Object value) {
        if (!ObjectUtils.isEmpty(value)) {
            filterBuilder.must(QueryBuilders.termsQuery(path, value));
        }
    }

    public static void mustTerms(BoolQueryBuilder filterBuilder, String path, Collection<?> value) {
        if (!ObjectUtils.isEmpty(value)) {
            filterBuilder.must(QueryBuilders.termsQuery(path, value));
        }
    }

    public static void mustWildcardQuery(BoolQueryBuilder filterBuilder, String path, Object value) {
        if (!ObjectUtils.isEmpty(value)) {
            filterBuilder.must(QueryBuilders.wildcardQuery(path, toWildcardQueryString(value)).caseInsensitive(true));
        }
    }

    public static void mustWildcardNestedQuery(BoolQueryBuilder filterBuilder, String parentPath, String childPath,
                                               Object value) {
        if (!ObjectUtils.isEmpty(value)) {
            filterBuilder.must(QueryBuilders.nestedQuery(parentPath,
                    QueryBuilders.wildcardQuery(childPath, toWildcardQueryString(value)).caseInsensitive(true),
                    ScoreMode.None));
        }
    }

    public static void shouldWildcardNestedQuery(BoolQueryBuilder filterBuilder, String parentPath, String childPath,
                                                 Object value) {
        if (!ObjectUtils.isEmpty(value)) {
            filterBuilder.should(QueryBuilders.nestedQuery(parentPath,
                    QueryBuilders.wildcardQuery(childPath, toWildcardQueryString(value)).caseInsensitive(true),
                    ScoreMode.None));
        }
    }

    public static void shouldWildcardQuery(BoolQueryBuilder filterBuilder, String path, Object value) {
        if (!ObjectUtils.isEmpty(value)) {
            filterBuilder.should(QueryBuilders.wildcardQuery(path, toWildcardQueryString(value)).caseInsensitive(true));
        }
    }

    public static String toWildcardQueryString(Object input) {
        if (org.springframework.util.ObjectUtils.isEmpty(input)) {
            return "**";
        } else {
            return "*" + input + "*";
        }
    }

    public static <T> ResponsePage<List<T>> searchHitsToPage(SearchHits<T> hits, Pageable page) {
        List<T> resultContent = hits.getSearchHits().stream()
                .map(SearchHit::getContent).collect(Collectors.toList());
        int totalPage = resultContent.isEmpty() ? 0 : (int) (hits.getTotalHits() / page.getPageSize() + 1);

        return ResponsePage.of(totalPage, hits.getTotalHits(), page.getPageNumber(), resultContent);
    }
}
