Feature: Calculador de preco do frete
	Como usuario
	eu quero fornecer meu CEP para o sistema
	para com isso validar meu endereço através do CEP fornecido

Scenario: Buscar endereço
	Given Dado o seguinte CEP valido: 
	"""
	13220901
	"""
	When quando o sistema solicitar qual o endereço de entrega
	Then o resultado deve ser:
	"""
	Rua nome da rua, N 200 - Bairro, Cidade/ES
	"""
	
Scenario: Buscar endereço com o sistema dos Correios fora
	Given Dado um cep:
	"""
	1232134
	"""
	When quando o servico dos Correios estiver fora e o cliente informar o cep
	Then deveria apresentar um erro com a mensagem:
	"""
	java.net.SocketException: Unexpected end of file from server
	"""
	
Scenario: CEP inválido
	Given Dado um cep invalido:
	"""
	000
	"""
	When quando o cliente buscar o endereco
	Then deve apresentar um erro com a mensagem:
	"""
	Erro 3: CEP invalido
	"""