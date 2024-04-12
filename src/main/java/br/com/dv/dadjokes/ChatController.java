package br.com.dv.dadjokes;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/dad-jokes")
    public String getJoke(@RequestParam(value = "message", defaultValue = "Tell me a dad joke") String message) {
        return chatClient.call(message);
    }

    @GetMapping("/dad-jokes-with-prompt")
    public String getCustomJoke(@RequestParam(value = "topic", defaultValue = "Walks into a bar") String topic) {
        PromptTemplate promptTemplate = new PromptTemplate("""
                Tell me a dad joke with the following topic: {topic}
                """);
        promptTemplate.add("topic", topic);
        return chatClient.call(promptTemplate.create()).getResult().getOutput().getContent();
    }

}
