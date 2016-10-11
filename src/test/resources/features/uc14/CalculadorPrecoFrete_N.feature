Feature: Validação de CEP
	Como usuario
  	eu quero a partir de um produto e o cep de entrega calcular preco
  	para com isso nao ter que acessar diretamente os correios para ter o preco de entrega

Scenario: CEP inválido
	Given Dado um produto valido com peso 10 largura 5 altura 5 comprimento 10 e cep:
	"""
	000
	"""
	When quando o cliente perguntar qual o valor do frete
	Then o retorno deve ser -3

Scenario: Calcular o frete sem um produto informado
	Given Dado um produto inválido
	And um cep válido 1232134
	When quando o cliente perguntar qual o valor do frete
	Then deveria apresentar um erro com a mensagem:
	"""
	produto inválido
	"""

Scenario: Calcular o valor do frete com o banco de dados fora do ar
	Given Dado um produto valido com peso 10 largura 5 altura 5 comprimento 10 e cep:
	"""
	1232134
	"""
	When quando o cliente perguntar qual o valor do frete
	Then deveria apresentar um erro com a mensagem:
	"""
	java.sql.SQLException: Falha ao conectar com o banco de dados
	"""

Scenario: Calcular o frete com tipo de entregas inválido
	Given Dado um produto valido com peso 10 largura 5 altura 5 comprimento 10 e cep: """1232134"""
	And tipo de entrega selecionado: 
	"""
	XPTO
	"""
	When quando o cliente perguntar qual o valor do frete
	Then o retorno deve ser -1

Scenario: Calcular o tempo de entrega
	Given Dado um produto valido com peso 10 largura 1 altura 1 comprimento 1 e cep: """1234567"""
	When quando o cliente perguntar qual o tempo do frete
	Then O resultado deveria ser 0