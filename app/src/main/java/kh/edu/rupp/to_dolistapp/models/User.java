package kh.edu.rupp.to_dolistapp.models;

public class User {
    private int id;
    private String name;
    private String email;
    private String status;

    public int getId() {return id; }
    public void setId(int id){ this.id = id; }

    public String getName(){ return name; }
    public String getEmail(){ return email; }
    public String getStatus(){ return status; }

    public void setName(String name){ this.name = name; }
    public void setEmail(String email){ this.email = email; }
    public void setStatus(String status){ this.status = status; }

}
