Feature: Currency converter testing

  @all
  Scenario: adding custom bank interest
    Given EUR is converted to GBP
    When amount to convert is set to 20500
    And bank interest is 5%
    Then converted amount is calculated correctly

  Scenario Outline: checking date ranges
    When date is set to <dateSet>
    Then date should be set to <dateExpected>

    Examples:
      | dateSet    | dateExpected |
      | 12/01/2024 | today        |
      | 01/02/1950 | 01/01/1990   |
      | 03/30/2018 | 03/30/2018   |

  Scenario: flipping currency
    Given EUR is converted to USD
    When amount to convert is set to 9876
    And bank interest is 3.33%
    Then flipped amount is calculated correctly

  Scenario: selection of predefined rates
    Given EUR is converted to USD
    And amount to convert is set to 13
    And predefined interest is 4%
    Then converted amount is calculated correctly