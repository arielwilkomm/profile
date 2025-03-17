# Descrição do Negócio

A API "Profile" permite gerenciar perfis de usuários e seus respectivos endereços. Abaixo, segue a descrição detalhada de cada endpoint da aplicação.

## Endpoints de Perfil

### 1. **GET /v1/profile/{cpf}**
- **Descrição:** Recupera as informações do perfil de um usuário pelo CPF.
- **Parâmetros:**
    - `cpf` (path): CPF do usuário.
- **Respostas:**
    - **200 OK:** Retorna as informações do perfil do usuário.
    - **404 Not Found:** Perfil não encontrado.
    - **500 Internal Server Error:** Erro inesperado.

### 2. **PUT /v1/profile/{cpf}**
- **Descrição:** Atualiza as informações de um perfil existente. Se houver um endereço associado, ele também será atualizado.
- **Parâmetros:**
    - `cpf` (path): CPF do usuário.
    - Corpo da requisição: JSON com os dados atualizados do perfil.
- **Respostas:**
    - **200 OK:** Perfil atualizado com sucesso.
    - **404 Not Found:** Perfil não encontrado.
    - **500 Internal Server Error:** Erro inesperado.

### 3. **DELETE /v1/profile/{cpf}**
- **Descrição:** Exclui um perfil de usuário existente. Caso o perfil possua um endereço vinculado, ele também será removido.
- **Parâmetros:**
    - `cpf` (path): CPF do usuário.
- **Respostas:**
    - **200 OK:** Perfil excluído com sucesso.
    - **404 Not Found:** Perfil não encontrado.
    - **500 Internal Server Error:** Erro inesperado.

### 4. **POST /v1/profile**
- **Descrição:** Cria um novo perfil de usuário. Caso um endereço seja informado, ele será salvo junto ao perfil.
- **Corpo da requisição:** JSON com as informações do novo perfil.
- **Respostas:**
    - **200 OK:** Perfil criado com sucesso.
    - **404 Not Found:** Perfil não encontrado.
    - **500 Internal Server Error:** Erro inesperado.

## Endpoints de Endereço

### 5. **GET /v1/profile/{cpf}/address/{addressId}**
- **Descrição:** Recupera as informações de um endereço vinculado a um usuário.
- **Parâmetros:**
    - `cpf` (path): CPF do usuário.
    - `addressId` (path): ID do endereço.
- **Respostas:**
    - **200 OK:** Retorna as informações do endereço.
    - **404 Not Found:** Perfil ou endereço não encontrado.
    - **500 Internal Server Error:** Erro inesperado.

### 6. **PUT /v1/profile/{cpf}/address/{addressId}**
- **Descrição:** Atualiza um endereço existente de um usuário. A atualização será realizada apenas se o CEP fornecido for válido.
- **Parâmetros:**
    - `cpf` (path): CPF do usuário.
    - `addressId` (path): ID do endereço.
    - Corpo da requisição: JSON com os dados atualizados do endereço.
- **Respostas:**
    - **200 OK:** Endereço atualizado com sucesso.
    - **404 Not Found:** Perfil ou endereço não encontrado.
    - **500 Internal Server Error:** Erro inesperado.

### 7. **DELETE /v1/profile/{cpf}/address/{addressId}**
- **Descrição:** Exclui um endereço de um usuário.
- **Parâmetros:**
    - `cpf` (path): CPF do usuário.
    - `addressId` (path): ID do endereço.
- **Respostas:**
    - **200 OK:** Endereço excluído com sucesso.
    - **404 Not Found:** Perfil ou endereço não encontrado.
    - **500 Internal Server Error:** Erro inesperado.

### 8. **POST /v1/profile/{cpf}/address**
- **Descrição:** Adiciona um novo endereço ao perfil de um usuário. O endereço será salvo apenas se o CEP informado for válido.
- **Parâmetros:**
    - `cpf` (path): CPF do usuário.
    - Corpo da requisição: JSON com os dados do novo endereço.
- **Respostas:**
    - **200 OK:** Endereço criado com sucesso.
    - **404 Not Found:** Perfil não encontrado.
    - **500 Internal Server Error:** Erro inesperado.

## Endpoints de Código Postal

### 9. **GET /v1/postal-code/{postalCode}**
- **Descrição:** Recupera as informações de um código postal e armazena os dados na base local para consultas futuras.
- **Parâmetros:**
    - `postalCode` (path): Código postal.
- **Respostas:**
    - **200 OK:** Retorna as informações do código postal.
    - **404 Not Found:** Código postal não encontrado.
    - **500 Internal Server Error:** Erro inesperado.
