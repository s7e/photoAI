package com.simon.photoai.chatgpt;

import java.util.List;

public record CompletionRequest(String model,
                                List<Message> messages,
                                double temperature,
                                int max_tokens) {

    public static CompletionRequest defaultWith(String prompt) {
        return new CompletionRequest(
                "gpt-3.5-turbo",
                List.of(new Message("user", prompt)),
                0.7,
                300
        );
    }
}
