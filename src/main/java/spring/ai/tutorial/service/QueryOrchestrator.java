package spring.ai.tutorial.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import spring.ai.tutorial.dto.QueryRoute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class QueryOrchestrator {

    private final ProductService productService;
    private final OrderService orderService;
    private final VectorStore vectorStore;

    public Map<String, Object> execute(QueryRoute queryRoute) {

        Map<String, Object> resultMap = new HashMap<>();

        for (QueryRoute.Action action : queryRoute.actions()) {

            switch(action.type()) {
                case "GET_PRODUCT" -> {
                    String productName = (String) action.parameters().get("productName");
                    resultMap.put("GET_PRODUCT", productService.getProduct(productName));
                }

                case "GET_ORDER" -> {
                    String customerName = (String) action.parameters().get("customerName");
                    resultMap.put("GET_ORDER", orderService.getOrdersByCustomerName(customerName));
                }

                case "SEARCH_RAG" -> {
                    String query = (String) action.parameters().get("query");
                    List<Document> documents = vectorStore.similaritySearch(query);
                    resultMap.put("SEARCH_RAG", documents);
                }

                default -> {
                    throw new IllegalArgumentException("Unsupported action type " + action.type());
                }
            }

        }

        return resultMap;
    }

}
