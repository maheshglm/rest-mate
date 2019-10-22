@api
Feature: To Test Holiday Api

  Scenario: This scenario is to test PublicHoliday Api
    Given I set Api baseUri as "https://kayaposoft.com"
    And I set Api endpoint as "/enrico/json/v2.0/"
    And I set the endpoint params for Api as:
      | action  | isPublicHoliday |
      | date    | 15-06-2018      |
      | country | sg              |

    When I GET the request
    Then I expect status code as 200
    And I expect response should contain:
    """
    {"isPublicHoliday":true}
    """
    And I expect response including following json key in any order
      | isPublicHoliday | true |

  Scenario: This scenario is Google Api

    Given I set Api baseUri as "https://www.googleapis.com"
    Given I set Api endpoint as "/books/v1/volumes"
    And I set the endpoint params for Api as:
      | q | isbn:9781451648546 |

    When I GET the request
    Then I expect status code as 200
    And I expect response including following json key in any order
      | items.volumeInfo.title     | Steve Jobs         |
      | items.volumeInfo.publisher | Simon and Schuster |
      | items.volumeInfo.pageCount | 630                |


