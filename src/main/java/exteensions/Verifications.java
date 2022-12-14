package exteensions;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.sikuli.script.FindFailed;
import utilities.CommonOps;

import java.util.List;
import static org.testng.Assert.*;




public class Verifications extends CommonOps {
    @Step("verify Text In Element")
    public  static void  verifyTextElement(WebElement elem, String expected){
        wait.until(ExpectedConditions.visibilityOf(elem));
        assertEquals(elem.getText(),expected);
    }
    @Step("verify Number Of Elements")
    public static void  numberOfElements(List<WebElement> elems, int expected){
        wait.until(ExpectedConditions.visibilityOf(elems.get(elems.size()-1)));
        assertEquals(elems.size(),expected);
    }

    @Step("verify visibility Of Elements (soft-Assertion)")
    public static void visibilityOfElements(List<WebElement> elems){
        for (WebElement elem : elems){
            softAssert.assertTrue(elem.isDisplayed());
        }
        softAssert.assertAll("Some Elements Were Not Displayed");
    }


    @Step("Verify Element Visually")
    public static void visualElement(String expectedImageName){
        try {
            screen.find(getData("ImageRepo") +expectedImageName+ ".png");
        } catch (FindFailed findFailed) {
            System.out.println("Error Comparing Image File:" +findFailed);
            fail("Error Comparing Image File:" +findFailed);
        }
    }

    @Step("Verify Element Displayed")
    public static void existanceOfElement(List<WebElement> elements){
        assertTrue(elements.size() > 0);
    }

    @Step("Verify Element Is Not Displayed")
    public static void nonexistanceOfElement(List<WebElement> elements){
        assertFalse(elements.size() > 0);
    }

    @Step("Verify Text with Text ")
    public static void verifyText(String actual,String expected){
        assertEquals(actual,expected);
    }

    @Step("Verify Number with Number ")
    public static void verifyNumber(int actual, int expected){
        assertEquals(actual,expected);
    }

}
