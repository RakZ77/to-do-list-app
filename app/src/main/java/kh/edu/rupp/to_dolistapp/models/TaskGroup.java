package kh.edu.rupp.to_dolistapp.models;

public class TaskGroup {
    private int id;
    private String name;
    private int tasks;
    private int progress;
    private String icon;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getTasks() { return tasks; }
    public void setTasks(int tasks) { this.tasks = tasks; }

    public int getProgress() { return progress; }
    public void setProgress(int progress) { this.progress = progress; }

    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
}