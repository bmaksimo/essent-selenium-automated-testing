package stepdefinitions.quote.api;

public class PayloadForiWelcome {
    private String username;
    private String password;

    public PayloadForiWelcome(String userId_m, String password_m){
         this.username = userId_m;
         this.password = password_m;
    }
}
