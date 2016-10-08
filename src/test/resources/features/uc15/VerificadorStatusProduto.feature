Feature: Consultar status de entrega
  Como usuario
  eu quero a partir de um produto e o cep de entrega calcular preco
  para com isso nao ter que acessar diretamente os correios para ter o preco de entrega

Scenario: Consultar status de entrega
    Given Dado um pedido valido com codigo de rastreio:
  """
  AA425231234
  """
    When quando o cliente perguntar qual o status do pedido
    Then o resultado deve ser:
  """
  Saiu para entrega
  """

