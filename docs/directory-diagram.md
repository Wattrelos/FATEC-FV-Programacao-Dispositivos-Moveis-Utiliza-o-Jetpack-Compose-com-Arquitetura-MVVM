Product:
É a data class que representa um produto no sistema. Ela guarda os dados principais que serão salvos no SQLite: id, name, price e quantity.

ProductDbHelper:
É a classe responsável por criar, acessar e gerenciar o banco de dados SQLite. Ela herda de SQLiteOpenHelper, criando a tabela de produtos e disponibilizando métodos para inserir, listar, atualizar e excluir registros.

ProductRepository:
É a classe que faz a ponte entre o banco de dados e o restante do aplicativo. Ela chama os métodos do ProductDbHelper, mas evita que o ViewModel precise acessar o SQLite diretamente.

ProductViewModel:
É a classe responsável por controlar os dados e as ações da tela. Ela guarda a lista de produtos, o texto da busca, o produto que está sendo editado e qual tela está sendo exibida. Também possui os métodos para adicionar, editar, excluir, pesquisar e atualizar a lista de produtos.

ProductViewModelFactory:
É a classe usada para criar o ProductViewModel passando o ProductRepository como parâmetro. Ela é necessária porque o ViewModel precisa receber o repositório para conseguir acessar os dados do sistema.

PProductListScreen:
É a tela responsável por exibir a lista de produtos cadastrados. Nela aparecem o botão de adicionar produto, o campo de pesquisa e os produtos cadastrados com as opções de editar e excluir.

ProductFormScreen:
É a tela usada para cadastrar um novo produto ou editar um produto já existente. Ela possui os campos de nome, preço e quantidade, além dos botões de salvar e cancelar.

ProductItem:
É o componente visual que representa cada produto dentro da lista. Ele mostra o nome, o preço, a quantidade e os botões de editar e excluir.

MainActivity:
É a tela principal do aplicativo. Ela inicializa o ProductDbHelper, cria o ProductRepository, configura o ProductViewModel e ativa o Jetpack Compose com setContent. A MainActivity também chama a função principal do app, que decide se será exibida a tela de listagem ou a tela de cadastro.
