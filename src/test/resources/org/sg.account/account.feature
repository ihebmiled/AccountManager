Feature: Test account operations


  Scenario Outline: account creation for the first time
    When create a new account
    Then the account should be created
    And the account balance should be <BALANCE>
    And the account History should contain exactly "<HISTORY>"

    Examples:
      | BALANCE | HISTORY |
      | 0       | CREATE  |

  Scenario Outline: make a deposit in my account
    Given create a new account
    When the client makes a deposit of amount <AMOUNT>
    Then the account balance should be <BALANCE>
    And the account History should contain exactly "<HISTORY>"

    Examples:
      | AMOUNT | BALANCE | HISTORY        |
      | 0      | 0       | CREATE,DEPOSIT |
      | 100    | 100     | CREATE,DEPOSIT |
      | 1000   | 1000    | CREATE,DEPOSIT |

  Scenario Outline: make a deposit in my account (KO Cases)
    Given create a new account
    Then the client's deposit of amount "<AMOUNT>" is refused with status <HTTP_STATUS>
    And the system should return an error with error code "<ERROR_CODE>"

    Examples:
      | AMOUNT | HTTP_STATUS | ERROR_CODE                |
      | -10    | 400         | PARAM_NEGATIVE            |
      | XXXX   | 400         | PARAM_NUMBER_FORMAT_ERROR |

  Scenario Outline: make a withdrawal from my account
    Given create a new account
    And the client makes a deposit of amount <DEPOSIT_AMOUNT>
    When the client makes a withdrawal of amount <WITHDRAWAL_AMOUNT>
    Then the account balance should be <BALANCE>
    And the account History should contain exactly "<HISTORY>"

    Examples:
      | DEPOSIT_AMOUNT | WITHDRAWAL_AMOUNT | BALANCE | HISTORY                 |
      | 0              | 0                 | 0       | CREATE,DEPOSIT,WITHDRAW |
      | 100            | 0                 | 100     | CREATE,DEPOSIT,WITHDRAW |
      | 100            | 100               | 0       | CREATE,DEPOSIT,WITHDRAW |
      | 100            | 50                | 50      | CREATE,DEPOSIT,WITHDRAW |
      | 1000           | 50                | 950     | CREATE,DEPOSIT,WITHDRAW |

  Scenario Outline: make a deposit in my account (KO Cases)
    Given create a new account
    When the client makes a deposit of amount <DEPOSIT_AMOUNT>
    Then the client's withdrawal of amount "<WITHDRAWAL_AMOUNT>" is refused with status <HTTP_STATUS>
    And the system should return an error with error code "<ERROR_CODE>"

    Examples:
      | DEPOSIT_AMOUNT | WITHDRAWAL_AMOUNT | HTTP_STATUS | ERROR_CODE                |
      | 0              | -10               | 400         | PARAM_NEGATIVE            |
      | 0              | XXXX              | 400         | PARAM_NUMBER_FORMAT_ERROR |
      | 0              | 1                 | 412         | NOT_ENOUGH_FUNDS          |
      | 50             | 3000              | 412         | NOT_ENOUGH_FUNDS          |
