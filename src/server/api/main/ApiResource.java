package server.api.main;

public abstract class ApiResource {
    public String start(String... args) {
        try {
            return run(args);
        } catch (Exception e) {
            return "Could not run Request. The following Exception occurred" + e.getMessage();
        }
    }

    public abstract String run(String... args);
}
