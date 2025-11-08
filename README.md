# Harry Potter App - das-potterapp

Aplicativo Android desenvolvido em Kotlin para consumir a [HP-API](https://hp-api.onrender.com/) e demonstrar conhecimentos em corrotinas e web services.

## 📋 Descrição do Projeto

Este projeto foi desenvolvido como parte da disciplina de Desenvolvimento Mobile II e tem como objetivo consumir a HP-API para listar personagens, professores e estudantes do universo Harry Potter.

## 🎯 Funcionalidades

O aplicativo possui as seguintes funcionalidades:

### 1. Dashboard Principal
- Tela inicial com 4 botões principais:
  - **Personagem por ID**: Buscar um personagem específico pelo ID
  - **Professores de Hogwarts**: Listar todos os professores da escola
  - **Estudantes por Casa**: Listar estudantes de uma casa específica
  - **Sair**: Fecha o aplicativo

### 2. Buscar Personagem por ID
- Permite ao usuário informar um ID para busca
- Exibe o nome e a casa do personagem encontrado
- Validação de campo vazio

### 3. Listar Professores de Hogwarts
- Exibe uma lista com todos os professores da escola
- Utiliza RecyclerView para apresentação dos dados
- Mostra nome e casa de cada professor

### 4. Listar Estudantes por Casa
- Permite selecionar uma das 4 casas através de RadioButtons:
  - Gryffindor
  - Slytherin
  - Ravenclaw
  - Hufflepuff
- Exibe lista de estudantes da casa selecionada
- Validação para garantir que uma casa seja selecionada

## 🛠️ Tecnologias Utilizadas

- **Linguagem**: Kotlin
- **Arquitetura**: MVVM (Model-View-ViewModel)
- **Networking**: 
  - Retrofit 2.9.0
  - OkHttp 4.12.0
  - Moshi 1.15.0 (JSON parsing)
- **Concorrência**: Kotlin Coroutines
- **UI**: 
  - ViewBinding
  - RecyclerView
  - Material Components
- **Lifecycle**: 
  - ViewModel
  - LiveData

## 📱 Requisitos

- **minSdk**: 28 (Android 9.0 - Pie)
- **targetSdk**: 34 (Android 14)
- **compileSdk**: 34

## 🏗️ Estrutura do Projeto

```
app/src/main/java/br/com/das/potterapp/
├── data/
│   ├── api/
│   │   ├── PotterApi.kt          # Interface Retrofit
│   │   └── RetrofitClient.kt     # Configuração do Retrofit
│   ├── model/
│   │   └── Character.kt          # Modelo de dados
│   └── repository/
│       └── PotterRepository.kt   # Repositório de dados
├── ui/
│   ├── main/
│   │   └── MainActivity.kt       # Dashboard principal
│   ├── characterbyid/
│   │   ├── CharacterByIdActivity.kt
│   │   └── CharacterByIdViewModel.kt
│   ├── staff/
│   │   ├── HogwartsStaffActivity.kt
│   │   └── HogwartsStaffViewModel.kt
│   ├── house/
│   │   ├── StudentsByHouseActivity.kt
│   │   └── StudentsByHouseViewModel.kt
│   └── CharacterAdapter.kt       # Adapter do RecyclerView
└── util/
    └── Result.kt                 # Sealed class para estados
```

## 🚀 Como Executar

1. Clone o repositório:
```bash
git clone https://github.com/seu-usuario/das-potterapp.git
```

2. Abra o projeto no Android Studio

3. Sincronize as dependências do Gradle

4. Execute o aplicativo em um emulador ou dispositivo físico com Android 9.0 ou superior

## 🌐 API Utilizada

O aplicativo consome os seguintes endpoints da HP-API:

- **Base URL**: `https://hp-api.onrender.com/api/`
- **Personagem por ID**: `GET /character/{id}`
- **Professores**: `GET /characters/staff`
- **Estudantes por Casa**: `GET /characters/house/{house}`

## 📝 Observações

- O aplicativo requer conexão com a internet para funcionar
- Tratamento de erros implementado para falhas de rede
- Interface responsiva com feedback visual (loading, erro, sucesso)
- Validações de entrada do usuário implementadas

## 👥 Integrantes do Grupo

[Adicione aqui os nomes completos dos integrantes do grupo]

## 📄 Licença

Este projeto foi desenvolvido para fins educacionais como parte da disciplina de Desenvolvimento Mobile II.

## 🔗 Links

- [HP-API Documentation](https://hp-api.onrender.com/)
- [Repositório GitHub](https://github.com/celsofabri/das-potterapp)
