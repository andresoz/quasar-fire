package com.challenge.quasarfire.communications.web.controller;

import com.challenge.quasarfire.communications.service.GetLocationAndMessageCommand;
import com.challenge.quasarfire.communications.service.GetLocationSatelliteCommand;
import com.challenge.quasarfire.communications.service.LocationAndMessageDTO;
import com.challenge.quasarfire.communications.service.ReceiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 * @author Andres Ortiz andresortiz248@gmail.com
 * */
@RestController
    @RequestMapping("/quasarfire")
public class ReceiverController {

    @Autowired
    ReceiverService receiverService;

    /**
     * Controller to get the location and message given the reception of the distance and partial message by three satellites.
     *
     * @param command     - The Satellites information DTO.
     * @return The location and message from the emitter.
     */
    @PostMapping("/topsecret")
    public ResponseEntity<LocationAndMessageDTO> getLocationAndMessage(@RequestBody GetLocationAndMessageCommand command) {
        return receiverService.getLocationAndMessage(command).map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Controller to get the location and message given received by a satellite.
     *
     * @param command     - The Satellite information DTO.
     * @return The satellite location and the message received.
     */
    @GetMapping("/topsecret_split/{satellite_name}")
    public ResponseEntity<LocationAndMessageDTO> getLocationSatellite(@RequestBody GetLocationSatelliteCommand command,
                                                                       @PathVariable(required = true, name = "satellite_name") String satelliteName) {

        return receiverService.getLocationSatellite(command, satelliteName).map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
