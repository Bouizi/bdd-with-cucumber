Feature: Search Address 
  As a user
  I want to return the address related to the CEP
  So that I can allow the system to deliver the product

Scenario: Valid CEP 
	Given Json data initialized viacep/ViaCepSuccess.json
	And I have a valid CEP
	When There is communication with viacep 
	And I send the CEP to the Post Office 
	Then It should return the address 
	
Scenario: Invalid CEP 
	Given Json data initialized viacep/ViaCepInvalidCep.html
	And I have an invalid CEP 
	When There is communication with viacep
	And I send the CEP to the Post Office
	Then It should show an error message in viacep
	"""
	Invalid CEP.
	"""

Scenario: Address not found
	Given Json data initialized viacep/ViaCepCepNotFound.json
	And I have a valid CEP not foundable 
	When There is communication with viacep
	And I send the CEP to the Post Office
	Then It should show an error message in viacep
	"""
	Address not found.
	"""

Scenario: Communication failed
	Given I have a valid CEP 
	When There is a slow communication with viacep
	And I send the CEP to the Post Office
	Then It should show an error message in viacep
	"""
	Read timed out
	"""
	