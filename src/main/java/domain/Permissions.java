package main.java.domain;

public final class Permissions {
    public static final int VIEW_MANIFEST = 1 << 0;
    public static final int EDIT_MANIFEST = 1 << 1;
    public static final int CARRY_HAZARDOUS = 1 << 2;
    public static final int CROSS_RESTRICTED_SECTOR = 1 << 3;

    private Permissions() { }
}
