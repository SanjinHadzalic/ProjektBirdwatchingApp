package hr.java.vjezbe.entiteti;

public class AppUserBuilder {
    private String username;
    private String password;
    private String role;

    public AppUserBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public AppUserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public AppUserBuilder setRole(String role) {
        this.role = role;
        return this;
    }

    public AppUser createAppUser() {
        return new AppUser(username, password, role);
    }
}