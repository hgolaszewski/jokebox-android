package pl.edu.wat.jokeboxandroid.model;

/**
 * Created by Hubert on 27.06.2017.
 */

public class SimpleJokeDto {

    String content;
    SimpleCategoryDto category;

    public SimpleJokeDto() {
    }

    public SimpleJokeDto(String content, SimpleCategoryDto category) {
        this.content = content;
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public SimpleCategoryDto getCategory() {
        return category;
    }

    public void setCategory(SimpleCategoryDto category) {
        this.category = category;
    }

}
