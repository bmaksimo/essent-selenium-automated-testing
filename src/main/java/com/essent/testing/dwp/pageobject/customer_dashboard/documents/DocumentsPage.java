package com.essent.testing.dwp.pageobject.customer_dashboard.documents;

import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;

public class DocumentsPage extends Component {
    public String documentText(){
        return findElementWhenVisible(By.xpath("(//list-simple-two-liner-cell//span[1])[2]")).getText();
    }
}
