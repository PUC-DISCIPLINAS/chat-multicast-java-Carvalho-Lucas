# chat-multicast-java-Carvalho-Lucas

## Nome: Lucas Pereira de Carvalho 

## Projeto 

Este trabalho tem por objetivo praticar programação em redes utilizando sockets.

Vocês deverão desenvolver uma aplicação de Chat em Java utilizando o protocolo Multicast.

Os requisitos básicos são:

1 - O servidor deve gerenciar múltiplas salas de bate papo.

2 - O cliente deve ser capaz de solicitar a lista de salas.

3 - O cliente deve ser capaz de solicitar acesso à uma das salas de bate papo.

3 - O servidor deve manter uma lista dos membros da sala.

4 - O cliente deve ser capaz de enviar mensagens para a sala.

5 - O cliente deve ser capaz de sair da sala de bate papo.

Antes de iniciar o desenvolvimento, você deve projetar o protocolo de comunicação com as seguintes mensagens:

CRIAR SALA
LISTAR SALAS
ENTRAR NA SALA(ID_SALA)
LISTAR MEMBROS DA SALA
SAIR DA SALA
ENVIAR MENSAGEM
Para realizar o trabalho você deverá seguir os seguintes passos:

1 - Criar um repositório individual para a sua aplicação através do Github Classroom, no link https://classroom.github.com/a/XHVKZkc1 (Links para um site externo.).

2 - Desenvolver a aplicação.

3 - Escrever no README.md do projeto a documentação do trabalho.

4 - Enviar no Canvas a URL do repositório do trabalho.

Mãos à obra e bom trabalho.


## Arquivos

Para o desenvolvimento desse projeto foram criadas as classes:  
ClienteSwing.java, Comandos.java, GerenciadorCliente.java, Servidor.java.

. Classe ClienteSwing.java:
Métodos:
- IniciarLeitor/IniciarEscritor: Lê a mensagem enviada para o servidor, recebe o texto digitado pelo usuário, identifica cada usuário logado no servidor por nome (Não aceita nomes repetidos).
Seleciona usuário no servidor para conversa privada.

- listaDeUsuarios: Responsável por listar usuários disponíveis para chat.

A classe ClienteSwing ainda implementa JFrame, de forma a dar interface para o chat.

. Classe Servidor.java: Classe resposável por abrir porta do servidor. 

. Classe Comandos.java: Classe responsável pelos comandos do chat, tais como: sair, listar_clientes, msg (seleciona outro usuário para iniciar chat), Login (Entrar com nome de usuário), Login_Aceito (Usuário não repetido e cadastrado no chat), Login_Negado (Nome do usuário já está sendo usado no chat, colocar outro nome para utilizar chat).

. Classe GerenciadorCliente: Responsável por realizar a conversa entre os clientes do chat.
Essa classe possui o método do tipo Void chamado Run.
Passa todas as informações necessários do nome do usuário que iniciou conversa no chat, além de verificar se o cliente fechou conexão com o servidor.

Ainda na classe Gerenciador, é possível notar o método "atualizarListaUsuarios", nesse método sempre é atualizado os usuários que fizeram nova conexão e que estão disponíveis para conversa.


## Funcionamento

Para o funcionamento correto do chat deve se iniciar a classe Servidor, para dar um Strat no mesmo, logo em seguida deve iniciar a classe ClienteSwing responsável por abrir a interface do chat.





