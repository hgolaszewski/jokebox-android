package pl.edu.wat.jokeboxandroid.model;

import java.util.Date;

/**
 * Created by Hubert on 29.06.2017.
 */

public class OKResponseDto {

    Date date;
    String message;

    public OKResponseDto(Date date, String message) {
        this.date = date;
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
