package com.example.prjcinema;

public class Film {
    private String id;
    private String name;
    private String genre;
    private String photo;
    private String releaseDate;

    // Default constructor
    public Film() {}

    public Film(String id, String name, String genre, String photo, String releaseDate) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.photo = photo;
        this.releaseDate = releaseDate;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getPhoto() { return photo; }
    public void setPhoto(String photo) { this.photo = photo; }

    public String getReleaseDate() { return releaseDate; }
    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }
}
