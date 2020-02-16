package pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.$;

public class ProductPage {
    private final static Logger logger = LoggerFactory.getLogger(ProductPage.class);

    private SelenideElement productImage = $(By.cssSelector(".product_pod .image_container"));
    private SelenideElement addToBasketBtn = $(By.cssSelector(".product_price button[type='submit']"));
    private SelenideElement goToBasketPageBtn = $(By.cssSelector(".btn-group>a[href*='basket']"));


    public void goToBasketPage() {
        logger.info("Go to Basket page");
        goToBasketPageBtn.click();
    }

    public boolean isProductImageDisplayed() {
        logger.info("Verify if product's image is displayed");
        return productImage.isDisplayed();
    }

    public void addProductToBasket() {
        logger.info("Add a product to basket");
        addToBasketBtn.click();
    }
}
