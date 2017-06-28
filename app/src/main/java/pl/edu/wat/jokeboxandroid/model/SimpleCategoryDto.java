package pl.edu.wat.jokeboxandroid.model;

/**
 * Created by Hubert on 27.06.2017.
 */

public class SimpleCategoryDto {

    String name;
    String requestparam;

    public SimpleCategoryDto() {
    }

    public SimpleCategoryDto(String name, String requestparam) {
        this.name = name;
        this.requestparam = requestparam;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRequestparam() {
        return requestparam;
    }

    public void setRequestparam(String requestparam) {
        this.requestparam = requestparam;
    }

}
