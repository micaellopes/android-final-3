package br.unipe.borracheiro.borracheiro.model;

/**
 * Created by -Vinícius on 04/06/2017.
 */

public class User {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return email + " - " + password;
    }
}
