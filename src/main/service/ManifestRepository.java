package main.service;

import main.domain.Shipment;

import java.util.ArrayList;
import java.util.List;

public class ManifestRepository {
    private final List<Shipment> shipments = new ArrayList<>();

    public void save(Shipment shipment) { shipments.add(shipment); }
}
