package stepdefinitions.odoo.navigation.search;

/**
 * Created by Fernando Mano on 1/23/19.
 */
public class MySQLDBTransaction extends AbstractDBTransaction {
    public void createTransaction() {
        System.out.println(mylogger.getClass().toString());
        mylogger.log();
    }

    public static void main(String[] args) {
        new MySQLDBTransaction().createTransaction();
    }
}
