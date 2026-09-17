package main.java.domain;

import main.java.enums.Sector;
import main.java.enums.SecurityLevel;

public class Planet {
    private String name;
    private Sector sector;
    private SecurityLevel securityLevel;

    public Planet(String name, Sector sector, SecurityLevel securityLevel) {
        this.name = name;
        this.sector = sector;
        this.securityLevel = securityLevel;
    }

    public String getName() { return name; }
    public Sector getSector() { return sector; }
    public SecurityLevel getSecurityLevel() { return securityLevel; }
}
