Feature: FEATURE1 To test checkout feature of Sample Site on Mobile and UI

    @DemoSite
    Scenario Outline: FEATURE1 Verify checkout flow of SauceDemo Site
    Given that I navigate SauceDemo Home
      Then I verify that I have landed correctly on the page
    When I login as "standard_user"
      Then I verify that I have logged in successfully
    When I select "<ItemName>"
      Then I can see the item detail page for "<ItemName>"
    When I add the item to cart
      Then I can see the item being added in the cart "<ItemName>"
    When I checkout the item
      Then I verify that I am on the checkout page
    When I fill up firstName as "abc" and lastName as "def" and zipcode as "95054" and continue
      Then I can see the item details for item "<ItemName>" on overview page
    When I click on the finish button
      Then I verify that the order is placed successfully
    Examples:
      | ItemName            |  |
      | Sauce Labs Backpack |  |
  #| Sauce Labs Fleece Jacket |  |


#  Scenario: FEATURE1 Verify incorrect selection of item
#    Given that I navigate SauceDemo Home
#      Then I verify that I have landed correctly on the page
#    When I login as "standard_user"
#      Then I verify that I have logged in successfully
#    When I select a non-existent item "ABC"
#      Then I am not navigated to Item Details Page
