package com.challenge.quasarfire.communications.service;

import com.challenge.quasarfire.communications.domain.Satellite;

import java.util.List;

/**
 * @author Andres Ortiz andresortiz248@gmail.com
 * */
public class GetLocationAndMessageCommand {
    List<Satellite> satellites;

    public List<Satellite> getSatellites() {
        return satellites;
    }
}
