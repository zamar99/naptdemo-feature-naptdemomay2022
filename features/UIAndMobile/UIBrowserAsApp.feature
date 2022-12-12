Feature: Testing Browser As A Hybrid App.


  Scenario: Verify browser back feature of Chrome Browser
    When I switch to the "NATIVE_APP" context
    When I type the url "https://www.saucedemo.com"
    When I switch to the "WEBVIEW_chrome" context
    When I login as "standard_user"
    When I switch to the "NATIVE_APP" context
    Then I click "Back" button of browser


    @DemoSiteMobileApp
    Scenario: To add and remove bookmark
      When I switch to the "NATIVE_APP" context
      When I type the url "https://www.saucedemo.com"
      When I click on the Menu and select "MenuIcon" >> "Bookmark"
      When I click on the Menu and select "MenuItem" >> "Bookmarks"
      Then I validate that the website is bookmarked

