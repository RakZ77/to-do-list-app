package kh.edu.rupp.to_dolistapp.models;

public class Task {
    private int id;
    private String name;
    private String title;
    private int progress;
    private String color;

    public Task(String title) {
        this.title = title;
        // Optional: set default values for other fields
        this.id = 0;
        this.name = "";
        this.progress = 0;
        this.color = "#FFFFFF"; // default white
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getProgress() { return progress; }
    public void setProgress(int progress) { this.progress = progress; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
}