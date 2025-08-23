# Teste Técnico

O que deve ser feito:

```markdown
1. Gerenciamento completo (inserir, atualizar e excluir) dos projetos de automação.
2. Gerenciamento completo (inserir, atualizar e excluir) dos ambientes de cada projeto de automação.
3. Gerenciamento completo (inserir, atualizar e excluir) das luminárias de cada ambiente.
4. Garantir segurança dos dados através de login e senha.
 a. Qualquer tipo de autenticação (bearer token, basic auth).
 b. Não é necessário gerenciamento de usuários (as credenciais podem estar em hardcode na API).
5. Visualização em tempo real do estado (ON/OFF) das luminárias.
 a. Utilizar websocket para comunicação em tempo real entre API e INTERFACE.
 b. Os estados das luminárias devem ser gerados randomicamente a cada 10 segundos.

classDiagram
    class Project {
      +int id
      +String name
      +List~Room~ rooms
    }

    class Room {
      +int id
      +String name
      +List~Lamp~ lamps
    }

    class Lamp {
      +int id
      +String name
    }

    Project "1" -- "0..*" Room : contains
    Room "1" -- "0..*" Lamp : contains
```

Front-end (Angular + TypeScript): <https://github.com/v1cferr/team_cloud_base_interface>
Back-end (Java + Springboot): <https://github.com/v1cferr/team_cloud_base_api>
