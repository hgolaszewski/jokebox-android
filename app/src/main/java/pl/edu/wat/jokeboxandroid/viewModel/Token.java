package pl.edu.wat.jokeboxandroid.viewModel;

/**
 * Created by Hubert on 28.06.2017.
 */

public class Token {

    String value;

    public Token() {
    }

    public Token(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
