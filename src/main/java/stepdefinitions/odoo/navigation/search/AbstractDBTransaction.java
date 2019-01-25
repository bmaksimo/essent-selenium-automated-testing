package stepdefinitions.odoo.navigation.search;

/**
 * Created by Fernando Mano on 1/23/19.
 */
public abstract class AbstractDBTransaction {
    MyLogger mylogger;

    public AbstractDBTransaction() {
        this.mylogger = new MyDBLogger();
    }
}
