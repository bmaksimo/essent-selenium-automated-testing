package stepdefinitions.quote.api.model;

public class IWelcomeLogin {
    private String username;
    private String password;

    public IWelcomeLogin(String userId_m, String password_m){
         this.username = userId_m;
         this.password = password_m;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
