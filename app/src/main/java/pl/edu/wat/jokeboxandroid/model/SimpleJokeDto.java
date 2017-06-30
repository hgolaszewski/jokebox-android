package pl.edu.wat.jokeboxandroid.model;

/**
 * Created by Hubert on 27.06.2017.
 */

public class SimpleJokeDto {

    int id;
    String content;
    SimpleCategoryDto category;
    int likeNumber;
    int unlikeNumber;

    public SimpleJokeDto() {
    }

    public SimpleJokeDto(String content, SimpleCategoryDto category, int id, int likeNumber, int unlikeNumber) {
        this.id = id;
        this.likeNumber = likeNumber;
        this.unlikeNumber = unlikeNumber;
        this.content = content;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(int likeNumber) {
        this.likeNumber = likeNumber;
    }

    public int getUnlikeNumber() {
        return unlikeNumber;
    }

    public void setUnlikeNumber(int unlikeNumber) {
        this.unlikeNumber = unlikeNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleJokeDto that = (SimpleJokeDto) o;

        if (id != that.id) return false;
        return content.equals(that.content);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + content.hashCode();
        return result;
    }
}
