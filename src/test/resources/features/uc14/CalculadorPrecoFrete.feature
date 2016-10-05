Feature: Calculador de preco do frete
	Como usuario
  eu quero a partir de um produto e o cep de entrega calcular preco
  para com isso nao ter que acessar diretamente os correios para ter o preco de entrega

Scenario: Calcule o frete
	Given Dado um produto valido com peso 10 largura 5 altura 15 comprimento 20 e cep 13220901
	When quando o cliente perguntar qual o valor do frete
	Then o resultado deve ser 50

