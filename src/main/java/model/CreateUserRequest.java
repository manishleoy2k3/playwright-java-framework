package model;

public class CreateUserRequest {
    private String name;
    private String job;

    public CreateUserRequest(String name, String job) {
        this.name = name;
        this.job = job;
    }

    public String getName() { return name; }
    public String getJob() { return job; }
}