<body>
<h2>Bem vindo ao meu projeto com spring boot</h2>

|Aviso: O projeto está em desenvolvimento, porém é totalmente utilizável|
| --- |

<div>
<h3>Flyway</h3>

|Os usuários são criados após a inicialização do projeto|
| --- |

<p>Não se preucupe com a criação de usuários,
utilizando a ferramenta flyway foi desenvolvido os sqls que são nescessários
para toda a aplicação, tais como os produtos e os usuários de autenticação</p>

> Você pode criar seu próprio usuário ou produto se quiser
</div>

<div>
<h3>Usuários gerados</h3>

<p>Não foi utilizado usuários mais complicadas devido a aplicação ser por
fins próprios e para facilitar quem quer testa-lá.</p>

<ul>
<p><b>Usuário com acesso de administrador</b></p>
username: <b>developeradmin</b>
<br/>
senha: senha123

<hr/>

<p><b>Usuário com acesso comum</b></p>
username: <b>developeruser</b>
<br/>
senha: senha123

</ul>

</div>

<div>
<h3>Avisos sobre a inicialização</h3>

|Aviso: Antes de qualquer coisa, veja se o docker ou o banco de dados está ativado e sendo usado com sucesso!|
| --- |

|Aviso: Sempre inicialize o discovery primeiro, ele precisa regitrar os micro-serviços contidos, caso não queira se preucupar, é so inicializar o projeto pelo script|
| --- |

</div>

<div>
<h3>Scripts contidos no projeto</h3>

| Script         | Descrição           |
| --------- | -------------- |
| yarn commit          |  Este script utiliza uma ferramenta chamada commitizen, com ela que eu organizo todos os commits da aplicação, de forma organizada e fácil|
| yarn dcdu          | Simplesmente um alias para docker-compose down && docker-compose up, removendo e logo após iniciando o docker da aplicação           |
| yarn spring-run:discovery | Inicia o modulo discovery da aplicação |
| yarn spring-run:auth | Inicia o módulo auth da aplicação |
| yarn spring-run:gateway | Inicia o módulo gateway da aplicação |
| yarn spring-run:products | Inicia o módulo products da aplicação |
| yarn spring-run:monitor | Inicia o módulo monitor da aplicação |
| yarn spring-run:all | Inicia todos os módulos da aplicação |

</div>

<div>
<h3>Sobre a segurança</h3>

<h6>No projeto é utilizado o JWT (JWS + JWE)</h6>

<p>É bem simples para quem tem acesso aos usuários.
Para gerar o seu token eu criei algumas metodologias dos quais são</p>

[Swagger Documentation](http://localhost:8083/swagger-ui/)
> Para agilizar o teste da aplicação, utilizei a geração e a verificação do token
> pelo próprio swagger:


> O método getUserToken recebe como request body o usuário e senha
> e retorna seu respectivo token

> O método getUserInfo recebe o token no header, com o prefixo Authorization: bearer + token
> , e retorna as claims responsáveis pelo token

<hr/>

[Request body do próprio context do spring security](http://localhost:8080/auth/login/)
> Está metodologia é mais usual para quem quer testar em serviços
> como postman, você precisa apenas colocar o usuário e senha
> de autenticação na request body, e a response será seu token

<hr/>

<h6>Acesso aos serviços</h6>

<p>Para acessar os serviços você precisa colocar o token no header, com o prefixo
<b>Authorization</b>, o token precisa estar válido, assinado e encriptografado</p>

<p><b>Swagger documentation</b></p>

[Swagger dos produtos](http://localhost:8082/swagger-ui/)

Com o swagger documentation você pode colocar o token apenas uma vez e validar todos os endpoints necessários. Você verá
um simbolo de cadeado em todos os endpoints, porém no topo do swagger também tem um cadeado que desbloqueia todos os
endpoints, você só precisa inserir o token
</div>

<div>
<h3>Sobre os testes da aplicação</h3>

Como a estrutura é baseada em micro-serviços, temos modulos gerais como o token e o core... Estes micro-serviços não
podem fazer os testes, pois não possuem beans spring boot compatíveis. Então quando for executar os testes referente aos
módulos gerais, sempre vá em cada módulo que utiliza ele por exemplo: o módulo auth utiliza a entidade applicationUser
que está no core. E o seu teste está dentro do módulo auth e não no core

> caso vá fazer covarage da aplicação, faça os testes em um escopo aberto, como a pasta java.

</div>

<div>
<h3>Monitoramento</h3>

[Monitoramento](http://localhost:8084)

|Ao entrar no módulo de monitoramento você verá uma página de login, o login utilizado é o mesmo do application User|
| --- |

> Lembre-se de registrar os modulos no discovery, pois o monitoramento gráfico tem relação com tudo que está monitorado lá

Caso você use o role: USER... Não terá acesso ao monitoramento de products e de auth, verá um erro ao entrar. não se
preucupe é apenas porque você não é autorizado a monitorar o modulo

Caso você use o role: ADMIN... Terá acesso a todo monitoramento.

</div>

<div>

###### By Samuel Elias || samueldev ||

`Estrutura de micro serviços, com java + spring boot`

</div>
</body>