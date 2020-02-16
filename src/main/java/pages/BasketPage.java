package pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.$;

public class BasketPage {
    private final static Logger logger = LoggerFactory.getLogger(BasketPage.class);

    private SelenideElement productsBlockTxtFld = $(By.cssSelector(".basket-title h2"));
    private SelenideElement searchBar = $(By.cssSelector("input[type='search']"));

    public String getProductsBlockTitle(){
        logger.info("Get title of products block");
        return productsBlockTxtFld.getText();
    }

    public void findBookUsingSearchBar(String bookTitle) {
        logger.info(String.format("Enter '%s' in search bar", bookTitle));
        searchBar.setValue(bookTitle).sendKeys(Keys.ENTER);
    }
}
