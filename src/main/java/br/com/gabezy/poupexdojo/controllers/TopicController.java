package br.com.gabezy.poupexdojo.controllers;

import br.com.gabezy.poupexdojo.dtos.MessageDTO;
import br.com.gabezy.poupexdojo.services.TopicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topic")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping
    public ResponseEntity<String> sendMessage(@RequestParam("key") String bindingKey, @RequestBody MessageDTO message) {
        topicService.sendMessage(bindingKey, message);
        return ResponseEntity.ok("Mensagem enviada com sucesso!");
    }

}
