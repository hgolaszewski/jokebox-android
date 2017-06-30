package pl.edu.wat.jokeboxandroid.model;

import pl.edu.wat.jokeboxandroid.viewModel.Token;

/**
 * Created by Hubert on 29.06.2017.
 */

public class SimpleCategoryDtoInput {

    String name;
    String requestparam;
    String address;
    Token token;

    public SimpleCategoryDtoInput(String name, String requestparam, String address, Token token){
        this.name = name;
        this.requestparam = requestparam;
        this.address = address;
        this.token = token;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
