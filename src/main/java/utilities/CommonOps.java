package utilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.windows.WindowsDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Screen;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import org.w3c.dom.Document;
import workflows.ElectronFlows;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class CommonOps extends Base{


    /*
    ################################################
    Method Name : getData
    Method Description : this method get the Data from XML configuration file
    Method parameters :String
    Method return : String
    ################################################
     */

    public static String getData(String nodeName) {
        DocumentBuilder dBuilder;
        Document doc = null;
        File Fxmlfile = new File("./Configuration/DataConfig.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(Fxmlfile);
        }
        catch (Exception e){
            System.out.println("Exception in reading XML file: " + e);
        }
        doc.getDocumentElement().normalize();
        return doc.getElementsByTagName(nodeName).item(0).getTextContent();
    }

     /*
    ################################################
    Method Name : initBrowser
    Method Description :This method initializes the browser with which we want to work
    Method parameters :String
    Method return :  Browser driver Type
    ################################################
     */

    public static void  initBrowser(String browserType){
     if (browserType.equalsIgnoreCase("chrome"))
         driver = initChromeDriver();
     else if (browserType.equalsIgnoreCase("firefox"))
         driver= initFirefoxDriver();
     else if (browserType.equalsIgnoreCase("ie"))
         driver= initIEDriver();
     else
         throw  new RuntimeException("Invalid Browser Type");
        driver.manage().timeouts().implicitlyWait(Long.parseLong(getData("Timeout")), TimeUnit.SECONDS);
        wait = new WebDriverWait(driver,Long.parseLong(getData("Timeout")));
        driver.get(getData("url"));
        driver.manage().window().maximize();
        ManagePages.initGrafana();
        actions = new Actions(driver);
    }

       /*
    ################################################
    Method Name : initChromeDriver
    Method Description :This method initializes the browser : ChromeDriver
    Method return :  Web driver
    ################################################
     */

    public static WebDriver initChromeDriver(){
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        return driver;
    }

    /*
    ################################################
    Method Name : initChromeDriver
    Method Description :This method initializes the browser : FireFox
    Method return :  Web driver
    ################################################
    */
    public static WebDriver initFirefoxDriver(){
        WebDriverManager.firefoxdriver().setup();
        WebDriver driver = new FirefoxDriver();
        return driver;
    }

    /*
    ################################################
    Method Name : initChromeDriver
    Method Description :This method initializes the browser : IED
    Method return : Web driver
    ################################################
    */
    public static WebDriver initIEDriver(){
        WebDriverManager.iedriver().setup();
        WebDriver driver = new InternetExplorerDriver();
        return driver;
    }

      /*
      ################################################
      Method Name : initMobile
      Method Description :This method initializes the Appium Studio app for mobile testing
     ################################################
     */
        public  static  void initMobile() {
        dc.setCapability(MobileCapabilityType.UDID, getData("UDID"));
        dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, getData("AppPackage"));
        dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, getData("AppActivity"));
        try {
            mobileDriver= new AndroidDriver(new URL(getData("AppiumServer")), dc);
        } catch (Exception e) {
            System.out.println("Can not connect to appium server , see details:" +e);
        }
        ManagePages.initMortgage();
        mobileDriver.manage().timeouts().implicitlyWait(Long.parseLong(getData("Timeout")), TimeUnit.SECONDS);
        wait = new WebDriverWait(mobileDriver,Long.parseLong(getData("Timeout")));
        actions = new Actions(driver);
    }

     /*
      ################################################
      Method Name : initAPI
      Method Description :This method initializes server-side tests with Rest-Assured
     ################################################
     */

    public static void  initAPI(){
        RestAssured.baseURI = getData("urlAPI");
        httpRequest = RestAssured.given().auth().preemptive().basic(getData("UserName"), getData("Password"));
    }

      /*
      ################################################
      Method Name : initElectron
      Method Description: This method initializes tests on an ElectronApp
     ################################################
     */

    public static void  initElectron(){
      System.setProperty("webdriver.chrome.driver",getData("ElectronDriverPath"));
      ChromeOptions opt = new ChromeOptions();
      opt.setBinary(getData("ElectronAppPath"));
      dc.setCapability("chromeOptions",opt);
      dc.setBrowserName("chrome");
      driver = new ChromeDriver(dc);
      ManagePages.initToDo();
        driver.manage().timeouts().implicitlyWait(Long.parseLong(getData("Timeout")), TimeUnit.SECONDS);
        wait = new WebDriverWait(driver,Long.parseLong(getData("Timeout")));
        actions = new Actions(driver);
    }

     /*
      ################################################
      Method Name : initElectron
      Method Description: This method initializes tests on an DesktopApp
     ################################################
     */

    public static void  initDesktop(){
        dc.setCapability("app",getData("CalculatorApp"));
        try {
            driver = new WindowsDriver(new URL(getData("AppiumServerDesktop")),dc);
        } catch (Exception e) {
            System.out.println("Can not Connect to Appium Server, See Details" + e);
        }
        driver.manage().timeouts().implicitlyWait(Long.parseLong(getData("Timeout")), TimeUnit.SECONDS);
        wait = new WebDriverWait(driver,Long.parseLong(getData("Timeout")));
        ManagePages.initCalculator();
    }


     /*
      ################################################
      Annotation Name : BeforeClass
      Method Description: In this intuition we will define everything that will always run before every test
      Method parameters :String
     ################################################
     */


    @BeforeClass
    @Parameters({"PlatformName"})
    public void startSession(String PlatformName){
         platform = PlatformName;
        if (platform.equalsIgnoreCase("web"))
            initBrowser(getData("BrowserName"));
       else if (platform.equalsIgnoreCase("mobile"))
           initMobile();
        else if (platform.equalsIgnoreCase("api"))
            initAPI();
        else if (platform.equalsIgnoreCase("electron"))
            initElectron();

        else if (platform.equalsIgnoreCase("desktop"))
            initDesktop();
        else
            throw  new RuntimeException("Invalid platform name");

        softAssert = new SoftAssert();
        screen = new Screen();

        ManageDB.openConnection(getData("DBURL"), getData("DBUserName"), getData("DBPassword"));
    }


     /*
     ################################################
     Annotation Name : AfterClass
     Method Description: In this intuition we will define everything that will always run after every test
     ################################################
     */

    @AfterClass
    public void closeSession() {
        if (!platform.equalsIgnoreCase("api")) {
            if (!platform.equalsIgnoreCase("mobile"))
                driver.quit();
            else
                mobileDriver.quit();
        }

        ManageDB.closeConnection();
    }


    /*
      ################################################
      Annotation Name : AfterMethod
      Method Description: In this intuition we will define everything that will always run after every Web && Electron test
     ################################################
     */

    @AfterMethod
    public void afterMethod(){
        if (platform.equalsIgnoreCase("web"))
        driver.get(getData("url"));
        else if (platform.equalsIgnoreCase("electron"))
            ElectronFlows.emptyList();
    }

     /*
     ################################################
     Annotation Name : BeforeMethod
     Method Description: In this intuition we will define everything that will always run before every  test (Except API)
     ################################################
     */

    @BeforeMethod
    public void beforeMethod(Method method){
        if (!platform.equalsIgnoreCase("api")) {
            try {
                MonteScreenRecorder.startRecord(method.getName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }
}


