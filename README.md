# Projeto Sistema Granja de Porcos :pig2:


* Este foi um projeto realizado como trabalho para a disciplina de Programação Orientada a Objetos II. Esse Sistema é responsável por registrar as entradas de porcos em uma granja, sendo possível gerenciar os galpões onde os porcos são alojados, os registros de entradas de porcos além de gerar gráficos e relatórios.
* Vale ressaltar que o objetivo do trabalho era desenvolver um Sistema de determinada área de um tema escolhido. Sendo assim, escolhi o tema de uma Granja de Porcos, na área de recebimentos desses animais. Por isso, esse Sistema não apresenta todos os módulos necessários para o gerenciamento e controle total de uma Granja de Porcos.
* Como requisito do trabalho, foi criado uma regra de negócio na qual é estabelecido um limite diário de recebimentos de porcos para cada galpão cadastrado, portanto não é permitido inserir mais porcos que esse limite e nem ultrapassar a capacidade máxima do galpão.

## :desktop_computer: Tecnologias e Conceitos utilizados 
- Linguagem **Java** e a tecnologia **JavaFx** para construção das interfaces gráficas. 
- Biblioteca **JFoenix** que implementa o _Material Design_ utilizando componentes Java.
- **Padrão MVC** (Model, View e Controller) que divide o projeto em camadas de responsabilidades. 
- **Padrão DAO** (Data Acess Object) que isola as regras de negócio das regras de persistência de dados.
- **Criptografia SHA-256** para criptografar a senha de cada usuário.
- **PostgreSQL** como SGBD para armazenar e gerenciar os dados da aplicação.
- **Padrão Factory** para gerar a instância de um determinado SGBD para persistência dos dados, permitindo gerar uma instância de outro SGBD como o MySql.  
- **Jasper Reports** para geração de relatórios de entrada de animais com filtro de data.

---

## :computer_mouse: Demonstração do Sistema

![DemoSistemaGranja](https://user-images.githubusercontent.com/101357910/175350387-3dc2e431-339d-4609-8560-e4a110737b99.gif)


---

## :man_technologist: Como executar o Sistema

- Instale o Java 8 em seu Computador.
- Faça um clone deste repositório.
- Abra o projeto em sua IDE de preferência (Net Beans, VsCode, etc).
- Certifique-se que as bibliotecas do diretório 'lib' estão sendo utilizados.
- Crie um Banco de Dados no PostgreSQL com nome de 'db_granja' e altere suas credenciais de acesso ao SGBD (login, senha) dentro do código fonte (DatabasePostgreSQL.java).
- Execute o código SQL dentro do arquivo 'script_bd.sql' para criar a estrutura do banco de dados da aplicação.
- Usuários e senhas para testar o sistema: (_Usuario_: 1, _Senha_: admin | _Usuario_: 2, _Senha_: 123456).
