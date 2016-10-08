Feature: Calculador de preco do frete
	Como usuario
  eu quero a partir de um produto e o cep de entrega calcular preco
  para com isso nao ter que acessar diretamente os correios para ter o preco de entrega

Scenario: Calcular o frete
	Given Dado um produto valido com peso 10 largura 5 altura 15 comprimento 20 e cep 13220901
	And tipo de entrega:
	"""
	PAC
	"""
	When quando o cliente perguntar qual o valor do frete
	Then o resultado deve ser 50

Scenario: Calcular o frete com servico dos Correios fora
	Given Dado um produto valido com peso 10 largura 5 altura 5 comprimento 10 e cep 1232134
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
