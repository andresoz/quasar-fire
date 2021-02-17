package com.challenge.quasarfire.communications.service;

import com.challenge.quasarfire.communications.domain.Satellite;

import java.util.List;

public class GetLocationAndMessageCommand {
    List<Satellite> satellites;

    public List<Satellite> getSatellites() {
        return satellites;
    }
}
