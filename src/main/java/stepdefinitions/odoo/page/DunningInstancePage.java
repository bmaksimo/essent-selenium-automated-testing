package stepdefinitions.odoo.page;

import com.essent.testing.odoo.pageobject.impl.Component;
import com.essent.testing.odoo.table.OdooTableFilter;
import com.essent.testing.table.Filter;
import org.openqa.selenium.WebElement;

import java.util.List;

public class DunningInstancePage extends Component {
    public List<WebElement> selectRowOnTable(String tableName, List<Filter> filters, String contextParameters) throws Exception {
        return new OdooTableFilter(contextParameters)
            .getTable(tableName)
            .findBy(filters)
            .get();
    }
}
