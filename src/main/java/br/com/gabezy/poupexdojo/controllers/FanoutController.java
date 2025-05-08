package br.com.gabezy.poupexdojo.controllers;

import br.com.gabezy.poupexdojo.dtos.MessageDTO;
import br.com.gabezy.poupexdojo.services.FanoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exchange")
public class FanoutController {

    private final FanoutService fanoutService;

    public FanoutController(FanoutService fanoutService) {
        this.fanoutService = fanoutService;
    }

    @PostMapping
    public ResponseEntity<String> sendMessage(@RequestBody MessageDTO message) {
        fanoutService.sendMessage(message);
        return ResponseEntity.ok("Mensagem enviada com sucesso!");
    }

}
