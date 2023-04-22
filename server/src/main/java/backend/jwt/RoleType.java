package backend.jwt;

public enum RoleType {
    USER("유저"),
    ADMIN("어드민");

    private final String value;

    RoleType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
