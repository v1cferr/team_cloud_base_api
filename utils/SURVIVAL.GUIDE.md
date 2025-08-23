# Guia de Sobrevivência: Java Spring Boot (Tradução para quem vem do Front-end)

**Mindset:** O objetivo não é saber tudo de cor. É entender o fluxo, saber onde mexer e demonstrar boas práticas de programação. Você é um resolvedor de problemas.

## O Fluxo da Informação (A Visão Geral)

Lembre-se sempre deste caminho. 90% das tarefas se resolvem seguindo ele.

```bash
[Seu Angular] <--> [Controller] <--> [Service] <--> [Repository] <--> [Banco de Dados]
     |                  |              |                 |                  |
 (Navegador)       (O Porteiro)     (O Cérebro)    (O Mensageiro)      (A Memória)
```

---

## Dicionário Rápido: O que é cada peça?

### 1\. `@RestController` (O Porteiro)

- **O que é?** A classe que recebe as requisições da internet (do seu front-end Angular). É a única camada que fala "HTTP".
- **Analogia:** É o porteiro do prédio. Ele recebe o chamado do Angular, vê para qual "apartamento" (método) a requisição se destina (`@GetMapping`, `@PostMapping`, etc.) e a encaminha.
- **Como identificar?** Arquivos que terminam com `Controller.java` e têm a anotação `@RestController` no topo.

### 2\. `@Service` (O Cérebro)

- **O que é?** A classe que contém a lógica de negócio. Validações, regras, cálculos... tudo acontece aqui.
- **Analogia:** É o morador inteligente do apartamento. O porteiro (Controller) avisa "tem uma encomenda para você", e o Service decide o que fazer com ela: "ok, preciso verificar se o pacote não está danificado (validar) antes de guardar no armário (chamar o Repository)".
- **Como identificar?** Arquivos que terminam com `Service.java` e têm a anotação `@Service`.

### 3\. `@Repository` (O Mensageiro do Banco)

- **O que é?** A interface que conversa diretamente com o banco de dados.
- **Analogia:** É o "delivery" ou o "mensageiro" que o Service chama para buscar ou guardar algo no grande depósito (o banco de dados). Ele já sabe os comandos básicos como `salvar`, `buscarPorId`, `deletar`.
- **Como identificar?** Uma interface que `extends JpaRepository`.

### 4\. `@Entity` / Domain (O Molde do Dado)

- **O que é?** A classe que representa a estrutura dos seus dados, como uma tabela do banco.
- **Analogia:** É a planta baixa de um apartamento ou o molde de um bolo. Define que um `Project` TEM que ter um `id` e um `projectName`.
- **Como identificar?** Uma classe simples (POJO) com a anotação `@Entity` no topo. Geralmente fica num pacote chamado `domain` ou `model`.

### 5\. DTO - Data Transfer Object (O Carteiro)

- **O que é?** Um objeto intermediário usado para transportar dados entre as camadas, principalmente entre o front e o back.
- **Analogia:** É um carteiro que leva uma encomenda específica. Em vez de entregar a pessoa inteira (a `@Entity`, com todos os seus dados internos), o DTO entrega apenas um pacote com as informações necessárias para aquela operação (ex: `id` e `name`, mas não a senha do usuário).
- **Por que usar?** Para segurança (não expor dados sensíveis como senhas) и para performance (enviar apenas os dados necessários).
- **Como identificar?** Classes simples, parecidas com a Entity, mas com o sufixo `DTO`. Ex: `ProjectDTO.java`.

### 6\. Exceptions (O Alarme de Incêndio)

- **O que é?** O mecanismo padrão do Java para sinalizar que um erro ocorreu.
- **Analogia:** É o alarme de incêndio. Quando algo dá errado (ex: projeto não encontrado), o Service "puxa o alarme" (`throw new ProjectNotFoundException()`). Isso para a execução normal e avisa quem chamou (o Controller) que deu problema.
- **Como identificar?** Blocos `try-catch` ou a palavra-chave `throw new ...`.

### 7\. `@Autowired` / Injeção de Dependência (A Mágica da Conexão)

- **O que é?** É o jeito do Spring "conectar" as peças para você.
- **Como funciona?** Em vez de você mesmo criar um `ProjectService` dentro do `ProjectController` (com `new ProjectService()`), você apenas declara a variável com `@Autowired` e o Spring te entrega uma instância pronta para usar. É o que liga o Porteiro ao Cérebro.
- **Como identificar?** A anotação `@Autowired` em cima de uma declaração de variável.

### 8\. Tests (O Controle de Qualidade)

- **O que são?** Códigos que testam o seu código de produção para garantir que tudo funciona como esperado.
- **Tipos comuns:**
  - **Testes de Unidade:** Testam a menor parte possível (um único método no Service, por exemplo) de forma isolada.
  - **Testes de Integração:** Testam como as partes funcionam juntas (ex: simulam uma chamada do Controller até o banco de dados).
- **Como identificar?** Ficam em uma pasta separada, geralmente `src/test/java`.

## Checklist de "Boas Práticas" para Demonstrar na Prática

- **[ ] Comunicação:** Narre sua lógica ANTES de codar. Use comentários se precisar.
- **[ ] Clareza:** Dê nomes bons para variáveis e métodos.
- **[ ] Validação:** Sempre pense: "E se esse dado vier nulo ou vazio?". Adicione um `if` para checar.
- **[ ] Simplicidade:** Faça a solução mais simples e direta que resolver o problema.

**Frase final para seu mindset:** "Eu não sou um especialista em sintaxe Java, eu sou um resolvedor de problemas que está usando Java para implementar uma solução lógica."

> Boa sorte\!\! Você está mais preparado do que imagina.

## Legado

1. **O Controller (`ProjectController.java`):**

   - **O que é?** É o porteiro. Ele recebe as requisições HTTP que vêm do seu Angular (GET para listar, POST para criar, etc.).
   - **O que procurar?** Procure por anotações como `@RestController`, `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`. Veja como os métodos dele recebem os dados e o que eles retornam. **O Controller é quem o Angular chama.**

2. **O Service (`ProjectService.java`):**

   - **O que é?** É o cérebro. O Controller chama o Service para executar a lógica de negócio (validar dados, fazer cálculos, etc.).
   - **O que procurar?** Veja os métodos que o Controller chama. `createProject()`, `updateProject()`, `deleteProject()`. É aqui que a "mágica" acontece.

3. **O Repository (`ProjectRepository.java`):**

   - **O que é?** É o "fofoqueiro" que fala com o banco de dados. O Service manda nele.
   - **O que procurar?** Você vai ver uma interface que `extends JpaRepository`. A mágica do Spring é que métodos como `.save()`, `.findById()`, `.deleteById()` já vêm prontos! O Service usa esses métodos para persistir os dados.

4. **A Entity / Model (`Project.java`):**
   - **O que é?** É o "molde" do seu dado. É uma classe simples que representa a tabela `Project` no banco de dados.
   - **O que procurar?** Anotações `@Entity` e `@Id`. Veja os atributos da classe (id, projectName). É o que define a estrutura do seu "projeto".

**O Fluxo é sempre esse:**
`Angular -> Chama o Controller -> Controller chama o Service -> Service usa o Repository -> Repository salva/lê a Entity no Banco de Dados.`

Entender esse fluxo é 80% do que você precisa.
