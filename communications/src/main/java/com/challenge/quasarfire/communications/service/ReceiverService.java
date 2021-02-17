package com.challenge.quasarfire.communications.service;

import com.challenge.quasarfire.communications.domain.Position;
import com.challenge.quasarfire.communications.domain.Receiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReceiverService {

    @Autowired
    Receiver receiver;

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
}
