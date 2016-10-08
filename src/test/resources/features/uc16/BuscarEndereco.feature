Feature: Calculador de preco do frete
	Como usuario
	eu quero fornecer meu CEP para o sistema
	para com isso validar meu endereço através do CEP fornecido

Scenario: Buscar endereço
	Given Dado o seguinte CEP valido 13220901
	When quando o sistema solicitar qual o endereço de entrega
	Then o resultado deve ser:
	"""
	Rua nome da rua, N 200 - Bairro, Cidade/ES
	"""