package spring.ai.tutorial.reader;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.JsonReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyJsonReader {

    private final Resource resource;

    public MyJsonReader(@Value("classPath:data.employeeList.json") Resource resource) {
        this.resource = resource;
    }

    public List<Document> loadJsonDocument() {
        JsonReader jsonReader = new JsonReader(resource);
        // 특정 필드에 대한 정보만 추출하기 원하면 해당 필드들을 명시하면 된다
//        JsonReader jsonReader = new JsonReader(resource, "id", "name");
        return jsonReader.get();
    }

}
