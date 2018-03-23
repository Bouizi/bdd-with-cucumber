Feature: Check the Status 
  As a user
  I want to return the status
  So that I can view the details of the deliver

Scenario: Valid code 
	Given Soap XML data initialized rastrowsdl/ConsultarStatusSuccess.xml
	And I have a valid code 
	When There is communication with buscaEventos
	And I send the code to the Post Office
	And The Post Office finds the delivery 
	Then Time cycle is quick response from buscaEventos
	And It should show the code and status 
	
#	Invalid code engloba tanto o caso de codigo expirado quanto invalido,
#	pois possuem a mesma resposta
Scenario: Invalid code
	Given Soap XML data initialized rastrowsdl/ConsultarStatusInvalidCode.xml 
	And I have an invalid code 
	When There is communication with buscaEventos
	And I send the code to the Post Office
	Then Time cycle is quick response from buscaEventos 
	And It should show an error message in soap xml
	"""
	Invalid object.
	"""

Scenario: Communication failed
	Given I have a valid code 
	When There is slow communication with buscaEventos
	And I send the code to the Post Office
	Then Time cycle is slow response from buscaEventos 
	And It should show an error message in soap xml
	"""
	Read timed out
	"""
	