package com.travel3d.vietlutravel.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Foods")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "model_path")
    private String modelPath;

    @Column(name = "poster_path")
    private String posterPath;

    @Column(name = "alt_text")
    private String altText;

    private String price; // e.g. "30.000đ - 50.000đ"

    @Column(columnDefinition = "TEXT")
    private String ingredients; // e.g. "Gạo, Nếp, Đậu xanh"

    @Column(columnDefinition = "TEXT")
    private String story; // Câu chuyện văn hóa

    private Double rating; // e.g. 4.8
    private Integer reviewsCount; // e.g. 120

    public Food() {
    }

    public Food(String name, String description, String modelPath, String posterPath, String altText) {
        this.name = name;
        this.description = description;
        this.modelPath = modelPath;
        this.posterPath = posterPath;
        this.altText = altText;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModelPath() {
        return modelPath;
    }

    public void setModelPath(String modelPath) {
        this.modelPath = modelPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getReviewsCount() {
        return reviewsCount;
    }

    public void setReviewsCount(Integer reviewsCount) {
        this.reviewsCount = reviewsCount;
    }
}