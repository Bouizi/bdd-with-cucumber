Feature: Calculate freight and delivery time 
  As a user
  I want the system to return the freight and time to deliver the products
  So that I know how much my purchase will cost and when it will be delivered

Scenario: All the information sent
	Given Price and Period data initialized calcprecoprazo/CalculaPrecoPrazoSuccess.xml
	And I have the products data
	And I have the type of the delivery
	And I have the origin address returned by the Post Office
	And I have the delivery address returned by the Post Office 
	When There is communication with CalcPrecoPrazo
	And I send the products data
	Then It should store the freight and delivery time in the database
	And Time cycle is quick response from CalcPrecoPrazo
	And It should return the freight and delivery time
	
Scenario: Missing products information  
	Given Price and Period data initialized calcprecoprazo/CalculaPrecoPrazoNoProduct.xml
	And I do not have the products data
	And I have the type of the delivery
	And I have the origin address returned by the Post Office
	And I have the delivery address returned by the Post Office 
	When There is communication with CalcPrecoPrazo
	And I send the products data
	Then It should not store the freight and delivery time in the database
	And Time cycle is quick response from CalcPrecoPrazo
	And It should show an error message in xml
	"""
	Para definicao do preco deverao ser informados, tambem, o comprimento, a largura e altura do objeto em centimetros (cm).
	"""
	
Scenario: Missing delivery type information  
	Given Price and Period data initialized calcprecoprazo/CalculaPrecoPrazoNoType.xml
	And I have the products data
	And I do not have the type of the delivery
	And I have the origin address returned by the Post Office
	And I have the delivery address returned by the Post Office 
	When There is communication with CalcPrecoPrazo
	And I send the products data
	Then It should not store the freight and delivery time in the database
	And Time cycle is quick response from CalcPrecoPrazo
	And It should show an error message in xml
	"""
	Nenhum valor retornado
	"""

Scenario: Missing origin address information    
	Given Price and Period data initialized calcprecoprazo/CalculaPrecoPrazoNoOrigin.xml
	And I have the products data
	And I have the type of the delivery
	And I do not have the origin address returned by the Post Office
	And I have the delivery address returned by the Post Office 
	When There is communication with CalcPrecoPrazo
	And I send the products data
	Then It should not store the freight and delivery time in the database
	And Time cycle is quick response from CalcPrecoPrazo
	And It should show an error message in xml
	"""
	CEP de origem invalido.
	"""


Scenario: Missing delivery address information    
	Given Price and Period data initialized calcprecoprazo/CalculaPrecoPrazoNoDestination.xml
	And I have the products data
	And I have the type of the delivery
	And I have the origin address returned by the Post Office
	And I do not have the delivery address returned by the Post Office 
	When There is communication with CalcPrecoPrazo
	And I send the products data
	Then It should not store the freight and delivery time in the database
	And Time cycle is quick response from CalcPrecoPrazo
	And It should show an error message in xml
	"""
	CEP de destino invalido.
	"""

Scenario: Communication failed
	Given I have the products data
	And I have the type of the delivery
	And I have the delivery address returned by the Post Office 
	When There is slow communication with CalcPrecoPrazo
	And I send the products data
	Then It should not store the freight and delivery time in the database
	And Time cycle has a slow response from CalcPrecoPrazo
	And It should show an error message in xml
	"""
	Read timed out
	"""