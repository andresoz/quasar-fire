package com.challenge.quasarfire.communications.service;

import com.challenge.quasarfire.communications.domain.Position;
import com.challenge.quasarfire.communications.domain.Receiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
/**
 * @author Andres Ortiz andresortiz248@gmail.com
 * */
@Service
public class ReceiverService {

    @Autowired
    Receiver receiver;

    /**
     * Service that orchestrates the logic to get the location and message from the emitter.
     *
     * @param command - The Satellites information DTO.
     * @return The location and message from the emitter.
     */
    public Optional<LocationAndMessageDTO> getLocationAndMessage(GetLocationAndMessageCommand command) {
        String[][] messages = command.getSatellites().stream().map(satellite -> satellite.getMessage()).toArray(String[][]::new);
        float[] distances = new float[command.getSatellites().size()];
        for (int i = 0; i < command.getSatellites().size(); i++) {
            distances[i] = command.getSatellites().get(i).getDistance();
        }
        if(receiver.getLocation(distances) == null){
            return Optional.empty();
        }else {
            return Optional.of(new LocationAndMessageDTO(new Position(receiver.getLocation(distances)), receiver.getMessage(messages)));
        }
    }

    /**
     * Service that orchestrates the logic to get the location and message received from a satellite.
     *
     * @param command - The Satellite information DTO.
     * @return The location and message received from satellite.
     */
    public Optional<LocationAndMessageDTO> getLocationSatellite(GetLocationSatelliteCommand command, String satelliteName){
        if(receiver.getSatellitePosition(satelliteName) == null){
            return Optional.empty();
        }else {
            return Optional.of(new LocationAndMessageDTO(receiver.getSatellitePosition(satelliteName), receiver.getSatelliteMessage(command.getMessage())));
        }
    }
}
