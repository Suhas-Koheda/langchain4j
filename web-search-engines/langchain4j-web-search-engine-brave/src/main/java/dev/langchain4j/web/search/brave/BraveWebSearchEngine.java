package dev.langchain4j.web.search.brave;

import dev.langchain4j.web.search.WebSearchEngine;
import dev.langchain4j.web.search.WebSearchInformationResult;
import dev.langchain4j.web.search.WebSearchRequest;
import dev.langchain4j.web.search.WebSearchResults;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static dev.langchain4j.internal.Utils.getOrDefault;
import static dev.langchain4j.internal.ValidationUtils.ensureNotBlank;
import static java.time.Duration.ofSeconds;

public class BraveWebSearchEngine implements WebSearchEngine {


    private static final String DEFAULT_BASE_URL = "https://api.search.brave.com/res/v1/web/search";

    private final BraveClient braveClient;
    private final  String apiKey;
    private final Integer count;
    private final String safeSearch;
    private final String resultFilter;
    private final String freshness;

    public BraveWebSearchEngine(String baseUrl,
                                 String apiKey,
                                 Duration timeout,
                                 Integer count,
                                 String safeSearch,
                                 String resultFilter,
                                 String freshness) {
        this.braveClient= BraveClient.builder()
                .baseUrl(getOrDefault(baseUrl, DEFAULT_BASE_URL))
                .timeout(getOrDefault(timeout, ofSeconds(10)))
                .build();
        this.apiKey = ensureNotBlank(apiKey, "apiKey");
        this.count = count;
        this.safeSearch = safeSearch;
        this.resultFilter = resultFilter;
        this.freshness = freshness;
    }

    @Override
    public WebSearchResults search(final String query) {
        return search(WebSearchRequest.from(query));
    }

    @Override
    public WebSearchResults search(final WebSearchRequest webSearchRequest) {
        BraveWebSearchRequest braveWebSearchRequest=BraveWebSearchRequest.builder()
                .apiKey(apiKey)
                .count(count)
                .safeSearch(safeSearch)
                .resultFilter(resultFilter)
                .freshness(freshness)
                .build();
        BraveResponse response =braveClient.search(braveWebSearchRequest);
        System.out.println(response.toString());


        //todo : refining the output response to find the web results


        return null;
    }
}
