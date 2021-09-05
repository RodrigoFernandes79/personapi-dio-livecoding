Desenvolvendo um sistema de gerenciamento de pessoas em API REST com Spring Boot 

 

PARTE 01: 

 

Vamos instalar as ferramentas de execução para rodar o Java , maven e outras dependências. No nosso caso vamos usar uma ferramenta que engloba todas as versões Java numa única plataforma, sendo assim se o projeto necessitar de alguma versao diferente da que estamos trabalhando, é só instalar através da ferramenta e trocar facilmente. Podemos também trocar o versionamento da JVM e Maven 

O Site para baixar essa ferramenta é: https://sdkman.io/ 

E você entra no gitBash , no diretório c: e digita: curl -s "https://get.sdkman.io" | bash 

E você tem o sdkman na tua máquina. 

Vamos configurar o versionamento do java 

Vamos em c:/ Users , onde o sdkman está instalado, e com o botão direito, clicamos em GitBash e no git, digitamos o comando: sdk version 

E o console nos mostrará a versão do sdkman. Agora vamos alterar a versão do Java. 

Primeiro vamos ver uma lista de versões através do comando: sdk list java I less 

E vamos escolher a versão: sdk install java  11.0.11.hs-adpt . Após a instalação , vamos usar essa nova versão com o comando : sdk use java  11.0.11.hs-adpt 

O mesmo faremos com o Maven , utilizando o  

sdk install maven 3.6.3  -> para instalação 

sdk use maven  3.6.3  -> para usar o maven instalado. 

 

Também utilizaremos uma plataforma para fazer o Deploy na nuvem que é o HEROKU:    https://www.heroku.com/home 

 

 

 

PARTE 02: 

Modelos Rest api possuem métodos principais como: 

Get 

Post 

Put 

Delete 

No quadro abaixo temos os modelos para se fazer um APi Restfull (que são aplicações na convenção Api Rest) e como cada APi deve funcionar: 

 

 

 

PARTE 03: 

Agora vamos pôr a mão na massa no projeto! 

 

Vamos instalar o Spring Boot  :  https://start.spring.io/ 

 

E vamos configurar da seguinte forma: 

 

 

As dependencias serão as seguintes: 

 

Spring DevTools -> sobe as aplicações automaticamente ao atualizar. 

Lombok  -> substitui códigos getters/setters/constructors através de Annotations 

Spring Web -> sobe a aplicação para um localhost. 

Spring Data JPA -> faz a persistencia com o banco de dados  

MySQL Driver -> no meu caso eu usei o banco de dados MySql para persistir dados com a aplicação. 

Spring Boot Actuator -> Monitora aplicações e dependencias, verifica qual dependência foi utilizada naquele momento da aplicação. 

 

Agora vamos gerar o arquivo personapi.zip  e descompactar numa pasta na área de trabalho ou em qualquer Works-pace desejado. Vamos entrar no Eclipse e importar o projeto descompactado.  

 

Vamos disponibilizar agora este projeto na nuvem , através do Heroku: https://dashboard.heroku.com/apps  , clicar no botão   NEW  e clicar em Create App.  

E configurar dessa forma: 

 

E clica no botão Create App. 

E vamos adicionar o método de Deploy pelo GitHub: 

No Heroku vamos clicar no botão Connect to GitHub e digite o nome do repositório criado no GitHub: 

 

Clica no botão Search e depois em Connect 

 

Para conectar, precisamos colocar no projeto em application.properties, a versão do java:  

java.runtime.version=11 

Pois o Heroku só detecta versões do java 8.  

Outro ponto de observação importante é que o diretório “.git” e o arquivo “.gitignore” que estão na pasta do seu projeto (na sua máquina) devem estar dentro da pasta do projeto (no nosso caso, a pasta personapi) se estiverem fora dela, tem que colocar os dois pra dentro. 

Após conectado, o Heroku irá gerar uma uri onde nosso projeto ficará, no nosso caso foi o: 

https://apipeoplelive.herokuapp.com/ 

 

 

PARTE 04: 

Vamos agora criar um modelo de cadastro de Pessoa e mapeá lo em um banco de dados: 

 

  

Então no Eclipse vamos criar um pacote model dentro do projeto e nele iremos criar duas classes: 

Person 

Phone 

 

E dentro dela colocaremos os respectivos atributos e anotações conforme imagens a seguir: 

Na classe Person: 

 

FetchType. LAZY = Isso não carrega os relacionamentos, a menos que você o invoque pelo método getter. FetchType. EAGER = Isso carrega todos os relacionamentos. 

No caso acima, ao consultar a tabela Person, não irá puxar todos os dados da lista da tabela de Phone  

 

Na Classe Phone: 

 

Na classe Phone temos um atributo type do tipo PhoneThype que é herdado da classe enum. Temos que colocar a anotação @Enumerated (EnumType.STRING) para relacionar com a classe enum chamada PhoneType que nos devolve uma String: 

 

Criaremos um pacote chamado enumerate e nela criaremos uma classe tipo enum: 

Na classe enum PhoneType: 

 

Essa classe enum, está relacionada com o atributo type na classe Phone, pois é uma classe de enumeração de opções: HOME, MOBILE, COMMERCIAL que devolve uma String  em seu atributo description. 

 

Feita essas configurações JPA e Hibernate, podemos rodar o projeto e as tabelas serão criadas no banco de dados(MySql no meu caso). 

 

PARTE 05: 

 

 

Agora iremos criar uma classe controller que funciona como uma classe DAO (data access object) . E iremos mapear todos esses dados. 

Porém antes de mapearmos dados, precisamos de criar uma interface onde herdamos do Spring Data todos os métodos e classes do JPA, onde não iremos precisar abrir nem fechar conexões com banco de dados, nem implementar operações para fazer inclusão, leitura nem deletar dados. 

Para criar essa interface, vamos criar um pacote repository e dentro dele iremos criar duas interfaces: 

PersonRepository 

PhoneRepository 

 

Na PersonRepository: 

 

Criamos uma classe tipo interface, clicando com o botão direito sobre o pacote repository e clicamos em interface. Depois vamos herdar de uma outra interface chamada JpaRepository,  que é da dependência Spring Data. E colocamos dentro da generics <>, o tipo da classe e o tipo do id da classe.  No caso da PersonREpository : 

<Person, Long> 

 

Na PhoneRepository: 

 

Fazemos a mesma coisa que fizemos na PersonRepository, sendo que nos        generics <>, passaremos a classe Phone com o tipo do id dela: 

<Phone, Long> 

 

Criada as interfaces, agora iremos criar o pacote controller , onde ficarão as classes que serão feitas pelo padrão DAO (Data Access Object) , e dentro dessas classes ficarão todos os gerenciamentos do banco de dados. É dentro do controller que iremos inserir os métodos REST de aplicação. Entre os principais: 

Create 

Update 

Get 

Delete 

 

 

Vamos ao PersonCreate: 

No pacote controller , iremos criar uma classe PersonCreate: 

 

@RestController -> informa ao Spring boot o tipo da classe "Controller" 

@RequestMapping("/people") -> solicita mapeamento da classe no localhost:8095/people 

private final PersonRepository repository; - > cria um atributo da classe PersonRepository 

@Autowired -> aplica a implementação do PersonRepository no construtor PersonController. Com isso não preciso instanciar o objeto, a aplicação será feita diretamente  

public PersonController(PersonRepository repository) {			t		this.repository = repository; -> construtor passando todos os métodos e atributos da interface Person Repository 

@PostMapping -> vai inserir dados da classe people na tabela e no localhost:8095/people 

public MessageResponseDTO  createPerson(@RequestBody Person person){ 

@RequestBody informa ao Srping Boot que solicitamos o tipo de linguagem JSON ao inserir dados de person 

 Person savedPerson = repository.save(person); 

return MessageResponseDTO.builder() 

.message("Person Created Sucessfully With ID: " + savedPerson.getId()) 

. build (); 

O build possui classes para tratamentos de mensagens que ao inserir ou deletar ou alterar dados, envia mensagens personalizadas. 

 

 

Ao informar a classe MessageResponseDTO no método createPerson(), esta classe deverá ser criada com a anotação @Builder (para personalizar os tratamentos de mensagens) e @Data dentro do pacote controller 

Classe para gerar tratamento de mensagens com o @Builder: 

 

 

PARTE 06: 

Agora iremos testar nossa aplicação com o PersonCreate no Insomnia: 

 

Todas os dados foram inseridos, e no banco de dados MySql: 

Na tabela Person: 

 

Na tabela Phone: 

 

 

PARTE 07: 

Se formos analisar o nosso projeto, o nosso controller é apenas o ponto de entrada da nossa aplicação, seria somente o funcionamento das requisições, então não faz sentido implantar regras de negócio dentro desses métodos, pois se futuramente quisermos testar novas regras, novas dependencias, e conforme aumentam as novas regras de negócio, a classe tende a aumentar e fica de difícil manutenção e visualização. 

Com isso iremos pegar toda a parte de salvar e de interação com repository e vamos colocar numa classe Service: 

Criamos um pacote .service, no pacote raiz e uma classe PersonService: 

 

E iremos criar uma anotação @Service -> que irá injetar uma dependência no nosso projeto Spring Boot, o qual irá gerenciar uma classe onde será responsável por todas as regras de negócio da nossa aplicação.  

Após isso, iremos criar um atributo PersonRepository: 

Private PersonRepository  personRepository; 

 

E iremos criar um construtor com a anotação @Autowired: 

@Autowired 

Public PersonService(PersonRepository personRepository){ 

This.personRepository = personRepository; 

} 

 

E Vamos colocar o método inteiro de createdPerson() na classe PersonService: 

public MessageResponseDTO createPerson( Person person){			Person savedPerson = repository.save(person); 

return MessageResponseDTO.builder() 

.message("Person Created Sucessfully With ID: " + savedPerson.getId()) 

.build(); 

} 

 

Note que tiramos a anotação @RequestBody, pois já temos na outra classe, e não precisamos declarar ela duas vezes. A classe PersonService ficará assim: 

 

Note que refatoramos toda a classe PersonController na classe PersonService, pois é nela que iremos criar toda a regra de negócio, deixando somente as aplicações na classe PersonController. 

  

De volta à classe PersonController, iremos colocar o atributo da classe PersonService: 

 

Private PersonService personService; 

E em seguida iremos fazer a criação do nosso construtor, chamando a anotação @Autowired (com o @Autowired injetamos todas as dependencias e não precisamos instanciar o objeto e implementamos os métodos e atributos diretamente.) 

 

@Autowired  

public PersonController(PersonService personService) { 

super (); 

this.personService = personService; 

} 

 

E dentro do método, vamos retornar o personService.createPerson(): 

@PostMapping  

public MessageResponseDTO createPerson(@RequestBody Person person){ 

return personService.createPerson(person); 

 

} 

A classe PersonController agora ficará assim: 

 

 

Também vamos inserir uma anotação @ResponseStatus (HttpStatus.CREATED) para devolver o código 200 pra nossa aplicação: 

 

 

PARTE 07: 

 

Como vimos anteriormente, temos um pacote model e com suas classes, PersonModel e PhoneModel , essas classes representam um “espelho” do nosso Banco de Dados. Porém queremos implantar nossas regras de negócio e, ao cliente acessar, ele por exemplo, terá que inserir os dados de acordo com essas regras, tais como tamanho mínimo e máximo de caracteres, formato de dia / mês / ano... formato de CPF entre outros. Essa é a "magica" do JPA. As entidades são o reflexo das tabelas, enquanto que o DTO trata dos dados (tamanho, formato tipo data/mês/ano, etc.); e correspondem as regras de negócios. Assim, o desenvolvedor não se preocupa tanto com o tipo de banco de dados, se é MySQL, H2, PostgreSQL, sql server, mongodb. 

 

DTO -> Data Transfer Object -> usado para transferência de dados de objetos. 

Então vamos criar um pacote request no diretório raiz da nossa aplicação, e iremos criar duas classes: 

PersonDTO 

PhoneDTO  

Basicamente essas classes terão os mesmos atributos das entidades PersonModel e PhoneModel , sendo que dentro delas implantaremos algumas regras, que se relacionarão com o usuário ao acessar. O seja, nós vamos fazer a validação no momento em que o usuário insere os dados no formato HTTP. 

Na Classe PhoneDTO: 

Vamos criar as seguintes anotações no atributo number: 

@NotEmpty  

@Size (min=13, max =14)  

Private String number; 

 

@NotEmpty -> que é para não aceitar dados vazios; 

@Size (min=13, max =14) -> para definir o tamanho mínimo e máximo de caracteres a serem inseridos pelo usuário; 

 

 

OBS: essas anotações pertencem ao java.validation, portanto algumas versões do Spring Boot não vêm com esse pacote, então será necessário inserir no POM.XML: 

<dependency> 

 	<groupId>org.springframework.boot</groupId>    			<artifactId>spring-boot-starter-validation</artifactId></dependency> 

 

Então feito isso nossa classe PhoneDto ficará assim: 

 

 

Então vamos agora para a classe PersonDto: 

Iremos colocar as seguintes anotações nos atributos: 

 

private Long id; 

@NotEmpty 

@Size (min = 2, max =100) 

private String firstName; 

@NotEmpty 

@Size (min = 2, max =100) 

private String lastName; 

@NotEmpty 

@CPF 

private String cpf; 

 

private LocalDate BirthDate; 

@Valid 

@NotEmpty 

private List <Phone> phones; 

 

Onde: 

@NotEmpty -> que é para não aceitar dados vazios; 

@Size (min=2, max =100) -> para definir o tamanho mínimo e máximo de caracteres a serem inseridos pelo usuário; 

@CPF -> Onde o próprio Spring Boot irá configurar o formato do cpf , nos moldes brasileiros; 

@Valid -> aciona a validação dos campos anotados JSR 303 de validação (@NotNull, @Email, @Size, etc.), você ainda precisa especificar uma estratégia do que fazer com os resultados dessa validação. Ou seja, no caso da lista de telefone, irá validar os dados de acordo com o padrão JSR 303. 

 

A classe PersonDto ficará assim: 

 

Agora temos que alterar os parâmetros dentro dos métodos nas classes PersonController e PersonService para PersonDto personDto; 

E fazer a validação da anotação @Valid, pois precisamos especificar uma estratégia do que fazer com os resultados dessa validação. Essa anotação ficará dentro do parâmetro do método createPerson(@Valid): 

PersonController: 

@PostMapping  

@ResponseStatus (HttpStatus.CREATED) 

public MessageResponseDTO createPerson(@RequestBody @Valid PersonDto personDto){ 

return personService.createPerson(personDto); 

 

} 

Então nossa classe PersonController ficará assim: 

 

 

PersonService: 

public MessageResponseDTO createPerson(PersonDto personDto){ 

 

 PersonDto savedPerson = repository.save(personDto); 

return MessageResponseDTO.builder() 

.message("Person Created Sucessfully With ID: " + savedPerson.getId()) 

. build ();		 

} 

Então nossa classe PersonService ficará assim: 

 

 

Podemos notar que na PersonService aponta este erro nos métodos .save() e no .getId(): 

 

Isso porque nós não criamos o método pra passar todos os dados de PersonDto, e pra fazer isso eu teria que passar um builder para cada atributo da classe Dto. Isso daria em um código muito extenso. 

 

PARTE 08: 

 

Então para isso, temos um framework muito interessante chamado mapstruck: 

https://mapstruct.org/ 

O mapstruck vai disponibilizar pra gente, através de uma única interface que cria um método para fazer uma conversão do método DTO para uma entidade e de uma entidade para um DTO. Seria uma criação de interface que transita dados entre a Classe DTO e as entidades, porque sem o @Mapper não irá persistir. Os dados serão convertidos através do @Mapper para serem persistidos. 

 

Então no POM.XML iremos adicionar a dependência do mapstruck: 

EM PROPERTIES: 

properties> 

<java.version>1.8</java.version> 

    	<org.mapstruct.version>1.4.2. Final</org.mapstruct.version> 

</properties> 

 

APÓS A DEPENDENCIA LOMBOK: 

<dependency> 

    		<groupId>org.mapstruck</groupId> 

    		<artifactId>mapstruck</artifactId> 

    		<version> 1.4.2. Final </version> 

</dependency> 

 

E na parte de baixo, no <build> do Maven , você precisará informar: 

<annotationProcessorPaths> 

<path> 

      							<groupId>org.projectLombock</groupId> 

<artifactId>lombok</artifactId> 

<version>${lombok.version}</version> 

</path> 

<path> 

                        <groupId>org.mapstruct</groupId> 

                        <artifactId>mapstruct-processor</artifactId> 

                        <version>${org.mapstruct.version}</version> 

                    		</path> 

</annotationProcessorPaths> 

<compilerArgs> 

       <compilerArg> -Amapstruct.defaultComponentModel=spring</compilerArg> 

    </compilerArgs> 

</configuration> 

</plugin> 

</plugins> 

</build> 

 

Feito isso, agora precisamos criar a interface PersonMapper: 

Primeiro criamos um pacote Mapper , e dentro dele criaremos uma interface chamada PersonMapper. 

 

Na interface PersonMapper: 

Criaremos uma anotação @Mapper em cima da interface; 

Criaremos um método para instanciar a classe PersonMapper: 

PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class); 

E criaremos dois métodos, um para transitar dados entre a entidade e o DTO, e outro para transitar dados entre o DTO e a entidade: 

 Person toModel(PersonDto personDto);  -> transita dados do Dto para a entidade; 

PersonDto toDto(Person person);   -> Transita dados da entidade para o Dto. 

Temos que também transitar os dados para que o atributo birthDate seja transferido na classe DTo, pois na classe Dto esse atributo está em String, e queremos que o formato seja em “dd- MM-yyyy". Para isso iremos passar a seguinte anotação @Mapping: 

@Mapping (target = "birthDate", source = "birthDate", dateFormat = "dd-mm-yyyy") 

Onde target é o alvo onde queremos passar essa transferência; 

O source é o local de origem; 

E o dateFormat passa o formato da data 

E com isso a interface PersonMapper ficará assim: 

 

 

Agora iremos mudar os códigos da classe PersonService: 

 

Iremos instanciar a constante PersonMapper INSTANCE pra nos retornar os métodos criados na interface PersonMapper dentro da classe PersonService: 

private final PersonMapper personMapper = PersonMapper.INSTANCE; 

Após isso, iremos converter o personDto para a entidade, alterando o seguinte método: 

public MessageResponseDTO createPerson(PersonDto personDto){ 

 Person personToSave = personMapper.toModel(personDto)  

 Person savedPerson = repository.save(personToSave); 

 

E nossa classe PersonService ficará assim: 

 

 

Feito isso, iremos ir para as outras aplicações Rest, dessa fez com o método GET: 

Começamos pela classe PersonController, e criaremos uma lista que nos retorna os dados. 

Em cima do metodo get , iremos colocar a anotação @GetMapping  

E criaremos um método listAll() que passa uma lista da classe PersonDto e este metodo nos retorna PersonService.listAll();  

 

 

Esse retorno do método será criado em PersonService,  que ficará assim: 

public List<PersonDto> listAll() { 

List<Person> allPeople = repository.findAll(); 

return  allPeople.stream() 

.map(personMapper::toDTO) 

.collect(Collectors.toList()); 

} 

 

 

 

PARTE 09: 

Agora podemos testar nossa aplicação pra ver se funciona:  

No Insomnia criamos uma nova requisição na pasta Person: 

 

E no Banco de Dados MySql: 

 

 

Feito isso, nós iremos criar um novo api Rest, que vai ser o GetById, onde encontraremos um Person pelo seu id: 

 

Na Classe PersonController: 

Criaremos uma anotação @GetMappin(“/{id}”) -> onde esse parâmetro será mapeado por um Id para mostrar os dados de Person. 

Abaixo dessa anotação iremos criar um método getById(Long id) que passa como parâmetro o id dos dados os quais queremos que mostre e que nos retorna o objeto da classe PersonService.getById(id). 

 

 

Notemos que existe uma anotação @PathVariable que tem função parecida com o @RequestBody, sendo que a primeira anotação requisita ou informa ao Spring Boot que estamos passando o id dessa pessoa, e na segunda anotação informa que estamos passando os dados da pessoa em formato JSON. 

  

Definiremos as regras de negócio desse método  findById(), na classe PersonService, 

onde criaremos o seguinte método: 

public PersonDto findById(Long id) throws PersonNotFoundException  { 

Person person = repository.findById(id).orElseThrow(() -> new PersonNotFoundException(id)); 

return personMapper.toDTO(person); 

} 

 

 

Nesse metodo estando criando uma exceção PersonNotFoundException a qual não for encontrada a pessoa pelo parâmetro id, ela nos devolve uma mensagem. 

Essa mensagem foi criada em uma nova classe PersonNotFoundException que estende uma interface Exception para mapear o Status Http  not found  através da anotação @ResponseStatus e que nos retornará uma mensagem ao não encontrar a pessoa: 

 

Na nossa classe PersonController  também deverá lançar a exceção: 

 

 

 

Feito isso, vamos subir nossa aplicação e vamos rodar ela no Insomnia: 

 

 

PARTE 10: 

 

 

Agora vamos para a operação delete (), que é muito parecida com a operação findById(), sendo que a delete () não vai retornar nada(void), apenas procurar o elemento pelo id, e ao encontrar, irá deletar: 

Na classe PersonController: 

 

Iremos criar um método deleteById() onde receberá um parâmetro id e que nos retorna ao personService.delete(id). A anotação @PathVariable significa que vamos informar ao Spring Boot que estamos passando o id dessa pessoa: 

   

@DeleteMapping("/{id}") 

	@ResponseStatus (HttpStatus.NO_CONTENT) 

	Public void deleteById(@PathVariable Long id) throws PersonNotFoundException { 

		return personService.delete(id); 

	} 

 

 

 O método acima, irá requisitar um outro método de regras de negócio na classe PersonService , onde passará um findById() para encontrar o id a ser deletado, se caso não for encontrado, será feita uma exceção mostrando o status notfound e uma mensagem (conforme criado na classe PersonNotFoundException). E ao encontrar, será feita a deleção do elemento através do seu id: 

 

public void delete(Long id) throws PersonNotFoundException { 

		repository.findById(id).orElseThrow(() -> new PersonNotFoundException(id)); 

		repository.deleteById(id); 

		 

	} 

 

 

 

 

No insomnia vamos testar a aplicação DELETE: 

 

Deletamos o Id = 3, e no MYSql o elemento id 3 não existe mais: 

 

Agora iremos fazer o UpdateById (PUT), e vamos começar na classe PersonController onde faremos o seguinte código: 

@PutMapping("/{id}") 

	public MessageResponseDTO updateById(@PathVariable Long id, @RequestBody PersonDto personDto) { 

		return personService.updateById(id, personDto); 

		 

	} 

Onde escrevemos a anotação @PutMapping -> onde o Spring Boot mapeará a requisição PUT através do parâmetro id; 

E no metodo passamos o MessageResponseDTO -> para retornar alguma mensagem ao efetuar o método; 

Chamamos o método de updateById() passando as anotações @PathVariable para informar o id, @RequestBody para informar uma lista JSON no corpo da requisição, e a @Valid que precisará trazer para o método as validações configuradas na classe PersonDto os parâmetros id e personDto onde iremos buscar e atualizar os dados. 

Método updateById(): 

 

 

O método nos retorna um outro metodo que será criado na classe PersonService , onde será criado toda nossa regra de negócio: 

O updateById(), basicamente possui o mesmo método GET, sendo que antes do método GET, inserimos a exceção ao procurar pelo id: findById(): 

O metodo PUT na classe PersonService: 

public MessageResponseDTO updateById(Long id, PersonDto personDto) throws PersonNotFoundException { 

		 

		Person person = repository.findById(id).orElseThrow(() -> new PersonNotFoundException(id)); 

		 

		Person personToSave = personMapper.toModel(personDto); 

		  

		 Person savedPerson = repository.save(personToSave); 

	 

		return MessageResponseDTO.builder() 

				.message("Person Created Sucessfully With ID: " + savedPerson.getId()) 

				. build (); 

	} 

 

A Classe PersonService ficará assim: 

 

 

E a classe PersonController irá receber uma assinatura PersonNotFoundException ficando o método PUT assim: 

 

 

PARTE 11: 

 

Vamos Testar nossa aplicação UPDATE(PUT) lá no Insomnia: 

 

E no MySql , foi alterado o usuário no BD: 

 

 