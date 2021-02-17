package com.challenge.quasarfire.communications.web.controller;

import com.challenge.quasarfire.communications.service.GetLocationAndMessageCommand;
import com.challenge.quasarfire.communications.service.LocationAndMessageDTO;
import com.challenge.quasarfire.communications.service.ReceiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
    @RequestMapping("/quasarfire")
public class ReceiverController {

    @Autowired
    ReceiverService receiverService;

    @PostMapping("/topsecret")
    public ResponseEntity<LocationAndMessageDTO> getLocationAndMessage(@RequestBody GetLocationAndMessageCommand command) {
        return receiverService.getLocationAndMessage(command).map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
