package pl.edu.wat.jokeboxandroid.model;

import java.util.Date;

/**
 * Created by Hubert on 29.06.2017.
 */

public class OKResponseDto {

    String message;

    public OKResponseDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
