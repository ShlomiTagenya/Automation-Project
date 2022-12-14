package pageObjects.Grafana;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LeftMenuPage {
    @FindBy(xpath = "//div[@class='sidemenu-item dropdown'][1]")
    private WebElement btn_search;

    @FindBy(xpath = "//div[@class='sidemenu-item dropdown'][2]")
    public WebElement btn_create;

    @FindBy(xpath = "//div[@class='sidemenu-item dropdown'][3]")
    public WebElement btn_dashboards;

    @FindBy(xpath = "//div[@class='sidemenu-item dropdown'][4]")
    public WebElement btn_explore;

    @FindBy(xpath = "//div[@class='sidemenu-item dropdown'][5]")
    public WebElement btn_alerting;

    @FindBy(xpath = "//div[@class='sidemenu-item dropdown'][6]")
    public WebElement btn_sconfiguration;

    @FindBy(xpath = "//div[@class='sidemenu-item dropdown'][7]")
    public WebElement btn_server;

}
