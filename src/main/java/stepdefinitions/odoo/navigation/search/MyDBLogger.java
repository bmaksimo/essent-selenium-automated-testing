package stepdefinitions.odoo.navigation.search;

/**
 * Created by Fernando Mano on 1/23/19.
 */
public class MyDBLogger extends AbstractDBLogger {

    public MyDBLogger() {
        super(myLogger);
    }

    @Override
    public void log() {
        System.out.println("Logger in use: " + this.getClass().toString());
    }

}
