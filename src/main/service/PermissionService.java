package main.service;

import main.domain.Permissions;
import main.domain.Ship;

public class PermissionService {
    public boolean canCarryHazardous(Ship ship) {
        return (ship.getPermissions() & Permissions.CARRY_HAZARDOUS) != 0;
    }

    public boolean canCrossRestrictedSector(Ship ship) {
        return (ship.getPermissions() & Permissions.CROSS_RESTRICTED_SECTOR) != 0;
    }

    public void grantHazardousPermission(Ship ship) {
        ship.setPermissions(ship.getPermissions() | Permissions.CARRY_HAZARDOUS);
    }

    public void removeEditPermission(Ship ship) {
        ship.setPermissions(ship.getPermissions() & ~Permissions.EDIT_MANIFEST);
    }
}
