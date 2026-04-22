# Financial API Core - v6.9.1 📊🚀

Este projeto é uma **API REST & Dashboard BI** robusta desenvolvida em Java para o gerenciamento e visualização de transações financeiras. A aplicação evoluiu para uma solução completa de monitoramento, apresentando uma interface unificada, volumetria por hora resiliente e alta performance em consultas no **Microsoft SQL Server**.

Diferente de uma aplicação básica, este projeto implementa lógicas de Business Intelligence, tratamento avançado de objetos JSON e uma interface de usuário otimizada para análise de dados em tempo real com design moderno.

---

## 🛠 Tecnologias Utilizadas

O ecossistema do projeto foi construído e atualizado com ferramentas de nível corporativo:

- **Java 26 (LTS)** – Utilizando as versões mais recentes para máxima performance e segurança.
- **Spring Boot 4.0.5** – Framework base para a construção de microserviços e MVC.
- **Jackson 3.1.0** – Serialização JSON avançada com suporte a objetos complexos de data (LocalDateTime).
- **Thymeleaf** – Engine de renderização de templates para o Dashboard dinâmico.
- **Chart.js** – Renderização de gráficos de volumetria e distribuição no lado do cliente.
- **Spring Data JPA** – Abstração para persistência de dados e consultas otimizadas.
- **Microsoft SQL Server** – Banco de dados relacional para armazenamento seguro e escalável.
- **Lombok** – Automação de códigos repetitivos (Boilerplate).
- **Maven** – Gerenciamento de dependências e automação de build.

---

## 📈 Funcionalidades de BI (v6.9.1 Update)

A aplicação foi otimizada para servir como uma central de visualização de dados financeiros:

* **Dashboard Unificado (New):** KPIs de saldo e gráficos de volumetria agrupados em um container principal (`main-bi-container`), proporcionando uma leitura de dados mais limpa e profissional.
* **Volumetria Blindada:** Motor JavaScript resiliente que processa datas em múltiplos formatos (Array, Objeto ou String ISO), garantindo a plotagem correta no Chart.js independente da versão do Jackson.
* **Acessibilidade (WCAG):** Interface em conformidade com normas de acessibilidade, utilizando labels associadas e ocultas para seletores de filtros, garantindo um código limpo e sem alertas em IDEs.
* **Volumetria por Hora:** Gráfico de linha que agrupa transações por hora cheia, ideal para identificar picos de movimentação financeira.
* **Distribuição de Categoria:** Gráfico Donut para análise rápida da proporção entre Receitas e Despesas.
* **Engine de Filtros Avançada:**
    * **Estado Inicial:** Carrega automaticamente os dados do dia atual (**Today**).
    * **Limpar Filtros:** Reseta os campos de busca visualmente, permitindo uma nova exploração de dados.
    * **Filtro por Tipo:** Dropdown integrado diretamente ao grid para alternar entre RECEITA/DESPESA.
* **Grid de Transações Pro:** Tabela com paginação numérica (Anterior/Próximo) e seletor de volumetria (10, 25, 50, 100 registros) integrado ao cabeçalho.

---

## 🏛 Estrutura de Arquitetura

Seguindo as melhores práticas de **Clean Code** e **Arquitetura em Camadas**:

1. **Controller:** Exposição de endpoints RESTful e gerenciamento das rotas de visualização (View Controller).
2. **Service:** Camada de inteligência onde residem as regras de negócio, agregações e cálculos de saldo.
3. **Repository:** Interface de comunicação de baixa latência com o SQL Server via JPA.
4. **UI (Frontend):** Interface responsiva construída com Thymeleaf e motor JavaScript resiliente para processamento de dados bi-dimensionais.

---

## ⚙️ Como Executar o Projeto

### Pré-requisitos
- **Java 26** instalado.
- **Maven 3.9+**.
- Instância do **Microsoft SQL Server** configurada e rodando.

### 🗄️ Estrutura do Banco de Dados (Tabela: `tb_transactions`)

| Coluna | Tipo | Descrição | Restrições |
| :--- | :--- | :--- | :--- |
| `id` | BIGINT | Identificador único da transação | Primary Key (Identity) |
| `amount` | DECIMAL(18,2) | Valor monetário da operação | NOT NULL |
| `type` | VARCHAR(10) | Tipo (RECEITA/DESPESA) | NOT NULL |
| `status` | VARCHAR(20) | Status (Ex: COMPLETED, PENDING) | NOT NULL |
| `user_email` | VARCHAR(255) | Email do proprietário | NOT NULL |
| `created_at` | DATETIME2 | Data e hora do registro | NOT NULL |

### 📜 Script SQL para Criação
```sql
CREATE TABLE tb_transactions (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    amount DECIMAL(18,2) NOT NULL,
    type VARCHAR(10) NOT NULL,
    status VARCHAR(20) NOT NULL,
    user_email VARCHAR(255) NOT NULL,
    created_at DATETIME2 NOT NULL
)
```

### Passos para Início

1. **Clone o Repositório**
```bash
git clone [https://github.com/SEU-USUARIO/financial-api.git](https://github.com/SEU-USUARIO/financial-api.git)
```
2. **Configuração do application.properties**
```bash
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=db_financial;trustServerCertificate=true
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```
3. **Execute a Aplicação:**
```bash
mvn clean install
mvn spring-boot:run
```

📊 **Principais Rotas**

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `GET` | `/dashboard` | **Painel BI Principal (Interface, Gráficos e Filtros)** |
| `GET` | `/api/transactions` | API: Lista todas as transações (JSON) |
| `POST` | `/api/transactions` | API: Cria uma nova transação (Suporta Bulk Import) |
| `DELETE` | `/api/transactions/{id}` | API: Remove um registro específico do banco |

### 🚀 Próximos Passos (Roadmap v7.0)

[x] **Dashboard BI:** Unificação visual e volumetria por hora.

[ ] **Segurança & Auth:** Página de login e controle de permissões (AuthLevel).

[ ] **Relatórios:** Exportação de fluxos de caixa em formato PDF e Excel.

**Desenvolvido por Gustavo Cabanhas** Analista de Negócios em TI focado em construir soluções escaláveis e arquiteturas modernas.
