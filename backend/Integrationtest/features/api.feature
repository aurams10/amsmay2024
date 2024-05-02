Feature: test API request

  Background:
    Given I Set url
  Scenario: Create users
      When user makes  POST rquest "/api/users" with body
      '''
        {
          "name": "aura",
          "job": "performance"
        }
      '''
      Then the response status code is 201
  Scenario: get users
      When user makes  GET rquest "/api/users/2" 
      Then the response status code is 200
  Scenario: update users
      When user makes  GET rquest "/api/users/2" 
      Then the response status code is 200
       And the response should contain "Success"
       And the responde should be json
         '''
          {
           "updatedAt": "string"
           }
         '''

  Scenario: update users
      When user makes  DELETE rquest ""/api/users/1" 
      Then the response status code is 204
       And the response should contain "Success"

      