Feature: Consultar status de entrega
  Como usuario
  eu quero a partir de um pedido e seu código de rastreio conseguir consultar o status de entrega
  para com isso nao ter que acessar diretamente o site dos correios conseguir essa informação

Scenario: Consultar status de entrega
    Given Dado um pedido valido com codigo de rastreio:
  """
  AA425231234
  """
    When quando o cliente perguntar qual o status do pedido
    Then o resultado deve ser:
  """
  Entregue
  """

Scenario: Consultar status de entrega com servico dos Correios fora
	Given Dado um pedido valido com codigo de rastreio:
	"""
	AA100833276BR
	"""
	When quando o servico dos Correios estiver fora e o cliente perguntar qual o status da entrega
	Then deveria apresentar um erro com a mensagem:
	"""
	java.net.SocketException: Unexpected end of file from server
	"""

Scenario: Consultar status de entrega ainda não identificado pelos correios
	Given Dado um pedido valido com codigo de rastreio:
	"""
	AA100833276BR
	"""
	When quando o cliente perguntar qual o status da entrega
	Then o resultado deve ser:
	"""
	O objeto nao e reconhecido pelos correios
	"""