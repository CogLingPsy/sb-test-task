package ui;

import com.codeborne.selenide.Configuration;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.BasketPage;
import pages.ProductPage;

import static org.assertj.core.api.Assertions.assertThat;
import static com.codeborne.selenide.Selenide.open;

public class BookSiteTest {
    private ProductPage productPage = new ProductPage();
    private BasketPage basketPage = new BasketPage();

    @BeforeMethod
    public void configureBrowser() {
        Configuration.browser = "chrome";
        Configuration.startMaximized = true;
    }

    @DataProvider(name = "bookTitle")
    public static Object[] bookTitle() {
        String[] titles = {"coders at work", "reversing"};
        return titles;
    }

    @Test(dataProvider = "bookTitle")
    public void checkProductsBlockExistsAfterProductAdding(String bookTitle) {
        open("http://selenium1py.pythonanywhere.com/ru/basket/");
        basketPage.findBookUsingSearchBar(bookTitle);
        assertThat(productPage.isProductImageDisplayed()).as("The picture of the product is not presented").isTrue();
        productPage.addProductToBasket();
        productPage.goToBasketPage();
        String productsBlockTitle = basketPage.getProductsBlockTitle();
        assertThat(productsBlockTitle).as("The title of the products' block in basket differs").isEqualTo("Товары в корзине");
    }
}
