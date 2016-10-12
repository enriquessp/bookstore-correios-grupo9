Feature: Calculador de preco do frete
	Como usuario
    eu quero a partir de um produto e o cep de entrega calcular preco
    para com isso nao ter que acessar diretamente os correios para ter o preco de entrega

Scenario: Calcular o frete
	Given Dado um produto valido com peso 10 largura 5 altura 15 comprimento 20 e cep:
	"""
	13220901
	"""
	When quando o cliente perguntar qual o valor do frete
	Then o resultado deve ser 50

Scenario: Calcular o frete com servico dos Correios fora
	Given Dado um produto valido com peso 10 largura 5 altura 5 comprimento 10 e cep:
	"""
	1232134
	"""
	When quando o servico dos Correios estiver fora e o cliente perguntar qual o valor
	Then deveria apresentar um erro com a mensagem:
	"""
	java.net.SocketException: Unexpected end of file from server
	"""

Scenario: Calcular o frete com alguma dimensão do produto inválida
	Given Dado um produto peso negativo
	When quando o cliente perguntar qual o valor do frete
	Then deveria apresentar um erro com a mensagem:
	"""
	Peso/largura/altura e comprimento nao podem ter valor negativo
	"""

Scenario: Calcular o frete quando usuario nao informa o frete
	Given Dado um produto valido com peso 10 largura 5 altura 15 comprimento 20 e cep:
	"""
	
	"""
	When quando o cliente perguntar qual o valor do frete
	Then deveria apresentar um erro com a mensagem:
	"""
	CEP não informado
	"""

Scenario: CEP inválido
	Given Dado um produto valido com peso 10 largura 5 altura 5 comprimento 10 e cep:
	"""
	000
	"""
	When quando o cliente perguntar qual o valor do frete
	Then deveria apresentar um erro com a mensagem:
	"""
	Erro 3: CEP invalido
	"""


Scenario: Calcular o frete sem um produto informado
	Given Dado um produto inválido e um cep válido
	"""
	1232134
	"""
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
	Given Dado um produto valido com peso 10 largura 5 altura 5 comprimento 10 e cep:
	"""
	1232134
	"""
	And tipo de entrega selecionado:
	"""
	XPTO
	"""
	When quando o cliente perguntar qual o valor do frete
	Then o retorno deve ser -1

Scenario: Calcular o tempo de entrega
	Given Dado um produto valido com peso 10 largura 1 altura 1 comprimento 1 e cep:
	"""
	1234567
	"""
	When quando o cliente perguntar qual o tempo do frete
    Then o resultado deve ser 0