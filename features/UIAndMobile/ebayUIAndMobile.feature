Feature: To test checkout feature of eBay on Mobile and UI



@AddToCart @Smoke @Regression
Scenario: Testing Add to Cart feature of ebay on UI and Mobile
Given that I navigate to eBay home
When I search for "iPhone 13"
Then I get the search results
When I select an item
Then I see the item details page and add item to cart
Then I view the cart


@AddToCart @Smoke @Regression
Scenario: Test Cart feature of ebay
Given that I navigate to eBay home
When I search for "iPhone 13"
Then I get the search results
When I select an item
Then I see the item details page and add item to cart
Then I view the cart


@AddToCart @Smoke @Regression
Scenario: eBay add to cart feature test with invalid input
Given that I navigate to eBay home
When I search for "asdfasdfsdf"
Then I should not see search results