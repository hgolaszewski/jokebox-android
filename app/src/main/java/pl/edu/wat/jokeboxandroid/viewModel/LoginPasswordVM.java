package pl.edu.wat.jokeboxandroid.viewModel;

import java.io.Serializable;

/**
 * Created by Hubert on 28.06.2017.
 */

public class LoginPasswordVM {

    String login;
    String password;

    public LoginPasswordVM() {
    }

    public LoginPasswordVM(String login, String password){
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
