package main.service;

import main.domain.Permissions;
import main.domain.Ship;

public class PermissionService {
    public boolean canCarryHazardous(Ship ship) {
        int permissions = ship.getRegistration().getPermissions();
        return (permissions & Permissions.CARRY_HAZARDOUS) != 0;
    }

}
