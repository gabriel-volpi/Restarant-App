# Aplicativo Android em Kotlin: Guia de Restaurantes
Repositório de um aplicativo Android feito em Kotlin! Este projeto é uma demonstração poderosa das tecnologias mais recentes no desenvolvimento Android, visando proporcionar uma experiência suave e eficiente para os usuários ao explorar e descobrir restaurantes incríveis. Seja bem-vindo à uma jornada culinária!

# Visão Geral do Aplicativo:
O aplicativo é uma plataforma intuitiva e interativa que permite aos usuários explorar uma ampla variedade de restaurantes emocionantes. Com uma interface de usuário moderna e atraente construída inteiramente com Jetpack Compose, nosso aplicativo oferece uma experiência visualmente agradável e altamente responsiva.

# Funcionalidades Principais

Lista de Restaurantes: A primeira tela do nosso aplicativo exibe uma lista dinâmica de restaurantes emocionantes. Essa lista é populada com dados provenientes de uma API, que se conecta a um banco de dados Firebase. Nossos usuários podem navegar por uma ampla gama de opções de restaurantes com apenas alguns toques. 
Além disso, é possível que o usuário escolha dentre os restaurantes da lista os "favoritos" que ficam salvos em memória do dispositivo e não são perdidos caso o aplicativo seja reiniciado.

Tela de "Loading": Como a primeira tela do aplicativo exibe uma lista proveniente de uma requisição online, o aplicativo possui uma tela intermediária de loading proporcionando uma experiência muito melhor ao usuário.

Tela de erro: Caso a primeira requisição do aplicativo não tenha sucesso e o aplicativo fique sem dados para exibir, o aplicativo conta também com uma tela de erro que por sua vez contém uma mensagem de erro e um botão de "retry" que executa a requisição novamente.

Detalhes do Restaurante: Quando um usuário seleciona um restaurante na lista, eles são levados para a segunda tela, onde podem explorar em detalhes todas as informações relevantes sobre o restaurante específico. Navegar pelos detalhes é uma experiência suave e envolvente, graças à navegação do Jetpack Navigation.

# Tecnologias Utilizadas
O projeto incorpora uma gama de tecnologias Android listadas a seguir:

Jetpack Compose: Interface do usuário é construída inteiramente com Jetpack Compose, oferecendo uma experiência moderna, flexível e altamente personalizável aos usuários.

Jetpack ViewModel: Controle eficiente do estado da interface do usuário por meio do Jetpack ViewModel, proporcionando uma separação clara entre a lógica de negócios e a interface do usuário.

Retrofit: Foi utilizado o Retrofit para acessar dados de REST APIs, permitindo uma conexão suave e confiável entre nosso aplicativo e a fonte de dados.

Coroutines: Operações assíncronas são tratadas com eficiência usando Coroutines, garantindo um desempenho suave e uma experiência de usuário responsiva.

Jetpack Navigation: A navegação entre telas é alimentada pelo Jetpack Navigation, proporcionando uma navegação intuitiva e sem esforço para os usuários.

Jetpack Room: Foram implementadas capacidades offline utilizando o Jetpack Room, permitindo que os usuários acessem informações mesmo quando estão desconectados e que a lista de restaurantes favoritos não seja perdida.

Jetpack Hilt: A injeção de dependências é gerenciada pelo Jetpack Hilt, o que nos permite manter nosso código limpo e altamente testável.

Testes Abrangentes: Os princípios de desenvolvimento incluem testes sólidos. Foram realizados testes unitários abrangentes e testes de UI para garantir a qualidade e a robustez do nosso aplicativo.

Clean Architecture e MVVM: Foi adotada uma arquitetura limpa juntamente com o padrão de apresentação MVVM para garantir uma separação clara de responsabilidades e uma base sólida para o desenvolvimento contínuo.

Sinta-se à vontade para explorar o código-fonte, aprender com nossas implementações e até mesmo contribuir com suas ideias de melhorias. 

# Evidência - "Idle State"
![idle_state_compressed](https://github.com/gabriel-volpi/Restarant-App/assets/77797748/498df502-5676-432c-843a-9b8a2f5cbcbf)

# Evidência - "Error State"
![error_state_compressed](https://github.com/gabriel-volpi/Restarant-App/assets/77797748/f8fc3892-4ac5-4429-9b45-c11172bc4d86)
