# Financial API Core - v5.5 🚀

Este projeto é uma **API REST robusta** desenvolvida em Java para o gerenciamento de transações financeiras. O foco desta aplicação é oferecer uma base sólida para sistemas de Business Intelligence (BI), com alta performance em consultas no **Microsoft SQL Server** e integração entre microserviços.

Diferente de uma aplicação básica, este projeto implementa lógicas de filtragem avançada por período e categoria, além de suportar operações em lote (Bulk) e integração com serviços de notificação.

---

## 🛠 Tecnologias Utilizadas

O ecossistema do projeto foi construído com ferramentas de nível corporativo:

- **Java 26 (LTS)** – Utilizando as versões mais recentes para performance e segurança.
- **Spring Boot 3.x** – Framework base para a construção de microserviços.
- **Spring Data JPA** – Abstração para persistência de dados e consultas otimizadas.
- **Microsoft SQL Server** – Banco de dados relacional para armazenamento seguro e escalável.
- **Lombok** – Automação de códigos repetitivos (Boilerplate).
- **Feign Client / RestTemplate** – Comunicação assíncrona com serviço de notificações (Porta 8080).
- **Maven** – Gerenciamento de dependências e automação de build.

---

## 📈 Funcionalidades Principais (BI Ready)

A API foi otimizada para servir dashboards de visualização de dados:

* **Filtros de BI:** Engine de busca personalizada que permite filtrar transações por **Data Inicial/Final** e **Tipo (RECEITA/DESPESA)** diretamente via banco de dados.
* **Operações Bulk:** Suporte para criação de múltiplas transações em uma única requisição JSON (ideal para importação de extratos).
* **Cálculo de Saldo Dinâmico:** Algoritmo para processar receitas, despesas e saldo total com base nos filtros aplicados.
* **Integração de Mensageria:** Disparo de notificações automáticas para o serviço de alerta ao registrar novas movimentações.

---

## 🏛 Estrutura de Arquitetura

Seguindo as melhores práticas de **Clean Code** e **Arquitetura em Camadas**:

1.  **Controller:** Exposição dos endpoints RESTful e tratamento de requisições.
2.  **Service:** Camada de inteligência onde residem as regras de negócio e cálculos financeiros.
3.  **Repository:** Interface de comunicação de baixa latência com o SQL Server.
4.  **DTO (Data Transfer Object):** Segurança e integridade na troca de dados entre Front-end e API.

---

## ⚙️ Como Executar o Projeto

### Pré-requisitos
- **Java 26** instalado.
- **Maven 3.9+**.
- Instância do **Microsoft SQL Server** configurada.

### Passos para Início

1. **Clone o repositório:**
```bash
git clone [https://github.com/SEU-USUARIO/financial-api.git](https://github.com/SEU-USUARIO/financial-api.git)
```
2. **Configuração de Banco de Dados**
```bash
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=financial_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```
3. **Execute a Aplicação:**
```bash
mvn clean install
mvn spring-boot:run
```

📊 **Endpoints da API**
| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `GET` | `/api/transactions` | Lista todas as transações |
| `POST` | `/api/transactions` | Cria uma nova transação |
| `DELETE` | `/api/transactions/{id}` | Remove um registro |

🚀 **Próximos Passos (Roadmap)**

[ ] **Segurança:** Implementação de Autenticação via Spring Security & JWT.

[ ] **Relatórios:** Exportação de fluxos de caixa em formato PDF e Excel.

[ ] **Auditoria:** Logs de rastreabilidade de transações (Data Audit).

**Desenvolvido por Gustavo Cabanhas** Analista de Negócios em TI focado em construir soluções escaláveis e arquiteturas modernas.
