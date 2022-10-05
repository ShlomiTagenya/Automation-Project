package sanity;

import exteensions.Verifications;
import io.qameta.allure.Description;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utilities.CommonOps;
import workflows.ApiFlows;

@Listeners(utilities.Listeners.class)
public class GrafanaAPI extends CommonOps {

    @Test(description = "Test 01: Get Team From Grafana")
    @Description("This Test Verify Team Property")
    public void test01_verifyTeam() {
        Verifications.verifyText(ApiFlows.getTeamProperty("teams[0].name"), "kuku");
    }

    @Test(description = "Test 02: add Team And Verify")
    @Description("This Test Adds a Team To Grafana and Verify It")
    public void test02_addTeamAndVerify() {
        ApiFlows.postTeam("ShlomiTeam", "ShlomiT@gmail.com");
        Verifications.verifyText(ApiFlows.getTeamProperty("teams[0].name"), "ShlomiTeam");
    }

    @Test(description = "Test 03: Update Team And Verify")
    @Description("This Test Updates a Team To Grafana and Verify It")
    public void test03_updateTeamAndVerify() {
        String id = ApiFlows.getTeamProperty("teams[0].id");
        ApiFlows.updateTeam("ShlomiTeam", "ShlomiTeam@gmail.co.il", id);
        Verifications.verifyText(ApiFlows.getTeamProperty("teams[0].email"), "ShlomiTeam@gmail.co.il");
    }

    @Test(description = "Test 04: Delete Team And Verify")
    @Description("This Test Deletes a Team To Grafana and Verify It")
    public void test04_deleteTeamAndVerify() {
        String id = ApiFlows.getTeamProperty("teams[0].id");
        ApiFlows.deleteTeam(id);
        Verifications.verifyText(ApiFlows.getTeamProperty("totalCount"), "1");


    }
}


