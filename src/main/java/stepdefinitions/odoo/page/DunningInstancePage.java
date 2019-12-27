package stepdefinitions.odoo.page;

import com.essent.testing.odoo.pageobject.impl.Component;
import com.essent.testing.odoo.table.OdooTableFilter;
import com.essent.testing.table.Filter;
import java.util.List;
import org.openqa.selenium.WebElement;

public class DunningInstancePage extends Component {
  public List<WebElement> selectRowOnTable(
      String tableName, List<Filter> filters, String contextParameters) throws Exception {
    return new OdooTableFilter(contextParameters).getTable(tableName).findBy(filters).getRow();
  }
}
