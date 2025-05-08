package br.com.gabezy.poupexdojo.controllers;

import br.com.gabezy.poupexdojo.dtos.MessageDTO;
import br.com.gabezy.poupexdojo.services.DirectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/direct")
public class DirectController {

    private final DirectService directService;

    public DirectController(DirectService directService) {
        this.directService = directService;
    }

    @PostMapping
    public ResponseEntity<String> sendMessage(@RequestBody MessageDTO message) {
        directService.sendMessage(message);
        return ResponseEntity.ok("Mensagem enviada com sucesso!");
    }

}
