package main.domain;

public class Registration {
    private String license;
    private int permissions;

    public Registration(String license, int permissions) {
        this.license = license;
        this.permissions = permissions;
    }

    public int getPermissions() { return permissions; }
    public void setPermissions(int permissions) { this.permissions = permissions; }
}
