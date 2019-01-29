package stepdefinitions.quote.api;

public class PayloadForiWelcome {
    private String userId;
    private String password;

    public PayloadForiWelcome(String userId_m, String password_m){
         this.userId = userId_m;
         this.password = password_m;
    }
}
