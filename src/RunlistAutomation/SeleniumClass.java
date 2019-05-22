package RunlistAutomation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class SeleniumClass {

	
	public void performSeleniumScripts(String object, HashMap<String,String[]> layoutProfileAssignmentMap) throws InterruptedException {
		 // declaration and instantiation of objects/variables
    	System.setProperty("webdriver.chrome.driver",".//driver//chromedriver");
		WebDriver driver = new ChromeDriver();
    	
        String baseUrl = LoginInfo.LOGINURL;
        
        // launch Fire fox and direct it to the Base URL
        driver.get(baseUrl);
        driver.findElement(By.id("username")).sendKeys(LoginInfo.USERNAME);
        driver.findElement(By.id("password")).sendKeys(LoginInfo.PASSWORD);   
        driver.findElement(By.id("Login")).click();   
        Thread.sleep(4000);
        
        //**********Input
        //For standard objects name, for custom objects need to put objectid
        driver.get(LoginInfo.LOGINURL+"/lightning/setup/ObjectManager/"+object+"/PageLayouts/viewPageAssignments?0.source=aloha");
        //**********Input
        
        driver.manage().timeouts().implicitlyWait(9000, TimeUnit.SECONDS);
        WebElement frame=driver.findElement(By.xpath("//*[@id=\"setupComponent\"]/div/div/force-aloha-page/div/iframe"));
        driver.switchTo().frame(frame);
        driver.manage().timeouts().implicitlyWait(9000, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[@id=\"topButtonRow\"]/input")).click();
        driver.manage().timeouts().implicitlyWait(6000, TimeUnit.SECONDS);
        
        //**********Input
        
        //profileId_recordtypeid(15) for master it is 000000000000000
        Set<String> layoutNamesSet=layoutProfileAssignmentMap.keySet();
        ArrayList<String> layoutNames= new ArrayList<String>();
        layoutNames.addAll(layoutNamesSet);
        //**********Input
        
        for(int i=0; i<layoutProfileAssignmentMap.size();i++) {
        	String profileIds[]=layoutProfileAssignmentMap.get(layoutNames.get(i));
	        for(int j=0; j<profileIds.length;j++)
	        {
		        String profileId=profileIds[j];//+"_000000000000000"; 
		        //**********Input
		        System.out.println("Profile clicked is:" + driver.findElement(By.id(profileId)));
		        driver.findElement(By.id(profileId)).click();
		        
		        Select drpLayoutNames = new Select(driver.findElement(By.xpath("//*[@id=\"pageLayoutSelector\"]")));
		        //System.out.println("Dropdown values are:"+drpLayoutNames);
		        
		        drpLayoutNames.selectByVisibleText(layoutNames.get(i));
		    }
        }
        
        driver.findElement(By.name("save")).click();
        
        driver.quit();
     
        System.out.println("Selenium Scripts Executed Successfully!");
            
	}

}
