package sanity;

import exteensions.Verifications;
import io.qameta.allure.Description;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utilities.CommonOps;
import workflows.MobileFlows;

@Listeners(utilities.Listeners.class)
public class MortgageMobile extends CommonOps {

    @Test(description = "Test01 - Verify Mortgage")
    @Description("This Test fill in mortgage fields and calculate repayment")
    public void test01_verifyMortgage(){
        MobileFlows.calculateMonrtgage("1000","3","4");
        Verifications.verifyTextElement(mortageMain.getTxt_repayment(),"£30.03");
    }
}
