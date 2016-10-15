Feature: Calculador de preco do frete
	Como usuario
	eu quero fornecer meu CEP para o sistema
	para com isso validar meu endereço através do CEP fornecido

Scenario: Buscar endereço
	Given Dado o seguinte CEP valido 13220901
	When Quando o banco de dados dos correios estiver no ar
    And e o sistema solicitar qual o endereço de entrega
	Then o resultado deve ser:
	"""
	Rua nome da rua, N 200 - Bairro, Cidade/ES
	"""

Scenario: Buscar endereço com banco de dados dos correios fora
  Given Dado o seguinte CEP valido 13220901
  When Quando o banco de dados dos correios estiver fora do ar
  And e o sistema solicitar qual o endereço de entrega
  Then deveria apresentar mensage de erro ser:
  """
  Connection timeout
  """

Scenario: Buscar endereço com cep inválido
	Given Dado o seguinte CEP inválido 10101010
	When Quando o banco de dados dos correios estiver no ar
    And e o sistema solicitar qual o endereço de entrega
	Then o resultado deve ser:
	"""
	Endereço não encontrado.
    """