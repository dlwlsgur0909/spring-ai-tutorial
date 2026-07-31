package spring.ai.tutorial.api;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.ai.tutorial.reader.MyJsonReader;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReaderController {

    private final MyJsonReader myJsonReader;

    @GetMapping("/reader")
    public List<Document> reader() {
        return myJsonReader.loadJsonDocument();
    }

}
