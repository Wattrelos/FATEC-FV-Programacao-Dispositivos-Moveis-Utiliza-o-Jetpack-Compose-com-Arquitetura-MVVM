# Utilização do Jetpack Compose Com Arquitetura MVVM

O MVVM é uma forma de organizar melhor o projeto, separando o código por responsabilidades.
Em vez de deixar tudo dentro da MainActivity, o projeto é dividido em partes: uma parte cuida dos dados, outra cuida da regra da tela e outra cuida da interface visual.
No nosso caso, isso ajuda a deixar o app mais organizado, porque a tela não acessa diretamente o banco SQLite. A tela conversa com o ViewModel, o ViewModel conversa com o Repository, e o Repository acessa o banco de dados.
Assim, o código fica mais fácil de entender, manter e evoluir.

# O que é Jetpack Compose?

Jetpack Compose é uma forma moderna de criar telas em aplicativos Android usando código Kotlin.
Em vez de montar a interface usando arquivos XML, os componentes da tela são escritos diretamente no código, com funções chamadas @Composable.
Por exemplo, um botão, um campo de texto ou uma lista são criados usando código Kotlin.
Isso deixa a criação da interface mais direta, porque a tela e o comportamento dos componentes ficam mais próximos dentro do projeto.
Neste app, tanto a versão sem padrão de projeto quanto a versão com MVVM utilizam Jetpack Compose para criar as telas.

Com o projeto criado, abra o arquivo build.gradle.kts do módulo app para adicionar as novas dependências.

Atenção: verifique a nomenclatura e certifique-se de editar apenas o build.gradle do Module: app neste exercício, para não modificar o arquivo do projeto por engano.

Aqui ativaremos o bloco buildFeatures para habilitar o uso do Jetpack Compose no projeto.

Em seguida, adicionaremos as dependências necessárias para criar interfaces com Compose, utilizar componentes do Material 3, visualizar telas no Android Studio e integrar a tela com ViewModel.

# O QUE É ISSO EXATAMENTE?

O plugin com.android.application indica que este módulo será um aplicativo Android. Já o plugin kotlin-android permite utilizar Kotlin no projeto, enquanto o plugin compose.compiler habilita a compilação correta das telas criadas com Jetpack Compose.

O bloco buildFeatures { compose = true } ativa o Jetpack Compose no módulo app. Com isso, o projeto passa a permitir a criação de interfaces diretamente em Kotlin, sem depender obrigatoriamente de arquivos XML.

O bloco kotlin { compilerOptions { ... } } define a versão da JVM usada pelo Kotlin. Neste caso, o projeto está configurado para gerar código compatível com Java 8, usando JvmTarget.JVM_1_8.

Para organizar as versões das bibliotecas do Compose, utilizamos o Compose BOM. Ele centraliza o controle das versões e evita conflitos entre dependências como ui, material3 e ferramentas de preview.

A dependência activity-compose permite usar o Compose dentro da MainActivity, possibilitando carregar a interface com setContent { }.

As dependências ui e ui-tooling-preview fornecem a base para construir telas com Compose e visualizar os componentes no Android Studio usando @Preview.

A dependência material3 adiciona os componentes visuais do Material Design 3, como botões, campos de texto, cards, barras superiores e outros elementos modernos de interface.

A dependência lifecycle-viewmodel-compose permite integrar ViewModel com Compose, ajudando a separar a lógica da aplicação da interface visual.

A dependência ui-tooling, usada como debugImplementation, adiciona ferramentas de apoio para inspeção e pré-visualização das telas durante o desenvolvimento.

As dependências core-ktx, appcompat, material, activity e constraintlayout fornecem recursos adicionais do Android, compatibilidade com versões anteriores e suporte a componentes tradicionais usados em projetos Android.

Por fim, as dependências junit, androidx-junit e espresso-core são usadas para testes unitários e testes instrumentados no Android.


## Organização de pastas

Nosso projeto irá ser estruturado dessa forma possuindo as pastas e, dentro de cada pasta especifica você irá criar os seguintes arquivos:
.
├── app/
│   ├── .gitignore
│   ├── build.gradle.kts
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml
│       │   ├── java/
│       │   │   └── com/
│       │   │       └── example/
│       │   │           └── produtossqlite/
│       │   │               ├── MainActivity.kt                # É a tela principal do aplicativo. Ela inicializa o ProductDbHelper, cria o ProductRepository, configura o ProductViewModel e ativa o Jetpack Compose com setContent. A MainActivity também chama a função principal do app, que decide se será exibida a tela de listagem ou a tela de cadastro.
│       │   │               ├── data/                          #
│       │   │               │   ├── local/                     # Cuida diretamente do banco SQLite.
│       │   │               │   │   └── ProductDbHelper.kt     # É a classe responsável por criar, acessar e gerenciar o banco de dados SQLite. Ela herda de SQLiteOpenHelper, criando a tabela de produtos e disponibilizando métodos para inserir, listar, atualizar e excluir registros.
│       │   │               │   └── repository/                #Faz a ponte entre o banco e o ViewModel.
│       │   │               │       └── ProductRepository.kt   # É a classe que faz a ponte entre o banco de dados e o restante do aplicativo. Ela chama os métodos do ProductDbHelper, mas evita que o ViewModel precise acessar o SQLite diretamente.
│       │   │               ├── model/                         # Representa os dados do sistema.
│       │   │               │   └── Product.kt                 # É a data class que representa um produto no sistema. Ela guarda os dados principais que serão salvos no SQLite: id, name, price e quantity.
│       │   │               ├── ui/                            #
│       │   │               │   ├── components/                # Contém partes visuais reutilizáveis.
│       │   │               │   │   └── ProductItem.kt         # É o componente visual que representa cada produto dentro da lista. Ele mostra o nome, o preço, a quantidade e os botões de editar e excluir.
│       │   │               │   └── screens/                   # Contém as telas principais do app.
│       │   │               │       ├── ProductFormScreen.kt   # É a tela usada para cadastrar um novo produto ou editar um produto já existente. Ela possui os campos de nome, preço e quantidade, além dos botões de salvar e cancelar.
│       │   │               │       └── ProductListScreen.kt   # É a tela responsável por exibir a lista de produtos cadastrados. Nela aparecem o botão de adicionar produto, o campo de pesquisa e os produtos cadastrados com as opções de editar e excluir.
│       │   │               └── viewmodel/                     # Controla os dados, estados e ações da tela.
│       │   │                   ├── ProductViewModel.kt        # É a classe responsável por controlar os dados e as ações da tela. Ela guarda a lista de produtos, o texto da busca, o produto que está sendo editado e qual tela está sendo exibida. Também possui os métodos para adicionar, editar, excluir, pesquisar e atualizar a lista de produtos.
│       │   │                   └── ProductViewModelFactory.kt # É a classe usada para criar o ProductViewModel passando o ProductRepository como parâmetro. Ela é necessária porque o ViewModel precisa receber o repositório para conseguir acessar os dados do 