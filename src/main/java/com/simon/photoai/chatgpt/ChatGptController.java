package com.simon.photoai.chatgpt;

import com.simon.photoai.FormInputDTO;
import com.simon.photoai.OpenAiApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class ChatGptController {

    private static final String MAIN_PAGE = "index";
    public static final String PHOTO_ASSISTANT_PROMPT =
            "You are a professional photographer and an expert in camera settings. "
                    + "A user wants to capture a specific mood or scene and describes it briefly in a few words. "
                    + "Provide them with the optimal camera settings:\n"
                    + "- Shutter Speed\n"
                    + "- ISO\n"
                    + "- Aperture (F-Stop)\n\n"
                    + "Consider factors such as time of day, lighting conditions, whether motion should be frozen or emphasized, "
                    + "and overall artistic intent. Also include a short explanation (1–2 sentences) for each setting "
                    + "so the user understands why it was chosen.\n\n"
                    + "The user’s desired photo description is: \"%s\"";

    @Autowired
    private ObjectMapper jsonMapper;
    @Autowired
    private OpenAiApiClient client;

    private String chatWithLLM(String message) throws Exception {
        String prompt = String.format(PHOTO_ASSISTANT_PROMPT, message);
        var completion = CompletionRequest.defaultWith(prompt);
        var postBodyJson = jsonMapper.writeValueAsString(completion);
        var responseBody = client.postToOpenAiApi(postBodyJson);
        var completionResponse = jsonMapper.readValue(responseBody, CompletionResponse.class);
        return completionResponse.firstAnswer().orElseThrow();
    }

    @GetMapping(path = "/")
    public String index() {
        return MAIN_PAGE;
    }

    @PostMapping(path = "/")
    public String chat(Model model, @ModelAttribute FormInputDTO dto) {
        try {
            model.addAttribute("request", dto.prompt());
            model.addAttribute("response", chatWithLLM(dto.prompt()));
        } catch (Exception e) {
            model.addAttribute("response", "Error in communication with LLM.");
        }
        return MAIN_PAGE;
    }

}
