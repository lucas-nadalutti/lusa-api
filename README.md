# Treinamento em aplicações REST Grails + AngularJS

[Requisitos](#requisitos)

[Arquitetura](#arquitetura)

### Requisitos

RQF-001 - Administração de Produtos

O sistema deverá ser capaz de listar, criar, editar e excluir produtos.

Um produto será caracterizado por: nome, descrição, status (ativo ou inativo), preço de, preço por, imagem, quantidade em estoque, data de criação e de atualização

Não será possível remover produtos que já tenham pedidos associados. Neste caso, podemos somente inativá-los


RQF-002 - Catálogo de produtos

O sistema deverá listar, para o cliente final, produtos disponíveis para compra.

Na listagem, serão exibidos somente produtos com status ativo cujo estoque seja maior que 0.

Para cada item, serão exibidas as informações: nome, preço e quantidade. Além disso, cada item terá um botão de compra que levará ao requisito RQF - 003


RQF-003 - Checkout de Produto

Ao escolher um produto para compra, o cliente será levado à uma nova tela para fechar seu pedido.

Nesta tela, o sistema mostrará um resumo da compra que está sendo feita junto a um formulário onde o cliente final preencherá: nome, email, endereço, forma de pagamento (escolha pelo menos 2 para disponibilizar) e formas de entrega (também no mínimo 2).

Ao preencher e confirmar os dados, o cliente fechará este pedido e receberá um email com os dados de sua compra.

O sistema, portanto, terá gerado um novo pedido para o cliente seguindo as informações inseridas.


### Arquitetura

* A aplicação foi criada com o profile `rest-api` com o intuito de gerar respostas no formato JSON, e não HTML
* As classes de domínio contêm as propriedades pertinentes a cada entidade do sistema, `Product` (produto) e `Checkout` (pedido)
* Os controllers definem o comportamento de cada ação disponível na aplicação (criar pedido, salvar produto, consultar produtos etc), incluindo o resultado de cada uma (e.g. status 200 em caso de resposta, status 400 em caso de erros de validação etc)
* Os serviços contêm a lógica de cada ação. Por exemplo, um pedido pode ser feito via boleto bancário ou cartão de crédito. O serviço de pedidos é responsável por criar um objeto `InvoiceCheckout` ou `CreditCardCheckout` dependendo dos dados da requisição feita pelo client
* A aplicação possui apenas testes de integração, que validam se, feita uma requisição para um determinado endpoint do sistema, a resposta é a esperada
