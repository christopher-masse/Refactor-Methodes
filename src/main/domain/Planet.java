package main.domain;

import main.enums.Sector;

public class Planet {
    private String name;
    private Sector sector;
    private int securityLevel;

    public Planet(String name, Sector sector, int securityLevel) {
        this.name = name;
        this.sector = sector;
        this.securityLevel = securityLevel;
    }

    public String getName() { return name; }
    public Sector getSector() { return sector; }
    public int getSecurityLevel() { return securityLevel; }
}
