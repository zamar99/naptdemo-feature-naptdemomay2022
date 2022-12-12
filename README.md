# naptdemo

NAPT Demo

mvn test
-Dcucumber.options="--tags @checkout"
-Dcucumber.options="--glue com.napt.ui.gap.steps"
-DimplicitWaitSeconds=5
-DtestType="pc"
-Dcap.browserName="chrome"
-DdriverPath="/Users/nisum/Documents/workspace/napt_automation/Central/drivers/chromedriver_mac"
-Dfeatures="/Users/nisum/Documents/workspace/naptdemo/features/GapBrowse.feature"
-Dexperience="desktop"
-DwebURL="https://www.gap.com"
 
mvn test -Dcucumber.options="--tags @checkout" -Dcucumber.options="--glue com.napt.ui.gap.steps" -DimplicitWaitSeconds=5 -DtestType="pc" -Dcap.browserName="chrome" -DdriverPath="/Users/nisum/Documents/workspace/napt_automation/Central/drivers/chromedriver_mac" -Dfeatures="/Users/nisum/Documents/workspace/naptdemo/features/GapBrowse.feature" -Dexperience="desktop" -DwebURL="https://www.gap.com"
 
 
Appium - Local Android
mvn test
-Dcucumber.options="--tags @checkout"
-Dcucumber.options="--glue com.napt.ui.gap.steps"
-DimplicitWaitSeconds=5
-DtestType="mew"
-Dcap.browserName="chrome"
-Dcap.deviceName="am10"
-Dcap.platformVersion=10
-Dcap.platformName=android
-Dcap.newCommandTimeout=5
-DappiumURL=http://localhost:4723/wd/hub
-Dfeatures="/Users/nisum/Documents/workspace/naptdemo/features/GapBrowse.feature"
-Dexperience="mobile"
-DwebURL="https://www.gap.com"
 
mvn test -Dcucumber.options="--tags @checkout" -Dcucumber.options="--glue com.napt.ui.gap.steps" -DimplicitWaitSeconds=5 -DtestType="mew" -Dcap.browserName="chrome" -Dcap.deviceName="am10" -Dcap.platformVersion=10 -Dcap.platformName=android -Dcap.newCommandTimeout=5 -DappiumURL=http://localhost:4723/wd/hub -Dfeatures="/Users/nisum/Documents/workspace/naptdemo/features/GapBrowse.feature" -Dexperience="mobile" -DwebURL="https://www.gap.com"
 
 

               