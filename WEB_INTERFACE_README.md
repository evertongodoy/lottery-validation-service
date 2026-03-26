# Lottery Statistics - Interface Web

## Visão Geral

Interface web moderna desenvolvida com **Thymeleaf**, **Tailwind CSS** e **JavaScript** para simulação e análise de loterias.

## Funcionalidades Implementadas

### ✅ Sistema de Menu Controlado
- Menu dinâmico que exibe opções diferentes baseado no status de autenticação
- Opções públicas acessíveis sem login
- Opções protegidas que requerem autenticação

### ✅ Simulação de Loteria
- Seleção interativa de números com interface de bolas clicáveis
- Suporte para **Lotofácil** (25 números) e **Mega-Sena** (60 números)
- Sem limite de números para seleção
- Visualização dos resultados comparando com sorteios anteriores

### ✅ Dark Mode
- Toggle de tema claro/escuro
- Preferência salva no localStorage
- Transições suaves entre temas

### ✅ Design Responsivo
- Layout adaptável para desktop, tablet e mobile
- Menu mobile com navegação hamburger
- Grid de bolas adaptável ao tamanho da tela

## Estrutura de Arquivos

```
src/main/
├── java/.../infrastructure/
│   ├── adapters/input/web/
│   │   └── LotteryWebController.java      # Controller web com @Controller
│   └── config/
│       ├── ThymeleafConfig.java           # Configuração do Thymeleaf
│       └── WebFluxStaticResourceConfig.java # Configuração de recursos estáticos
│
└── resources/
    ├── templates/
    │   ├── layout/
    │   │   └── base.html                  # Layout base (não usado atualmente)
    │   ├── fragments/
    │   │   ├── header.html                # Fragmento do header e menu
    │   │   └── footer.html                # Fragmento do footer
    │   └── lottery/
    │       ├── home.html                  # Página inicial
    │       ├── simulate.html              # Página de simulação
    │       └── simulate-result.html       # Página de resultados
    │
    └── static/
        ├── css/
        │   ├── custom.css                 # Estilos gerais customizados
        │   └── lottery-balls.css          # Estilos específicos das bolas
        └── js/
            ├── theme-toggle.js            # Lógica do dark mode
            ├── menu-mobile.js             # Lógica do menu mobile
            └── lottery-simulator.js       # Lógica de seleção de números
```

## Endpoints Disponíveis

### Páginas Web

| Endpoint | Método | Descrição | Autenticação |
|----------|--------|-----------|--------------|
| `/web/` ou `/web/home` | GET | Página inicial | Não |
| `/web/simulate` | GET | Página de simulação | Não |
| `/web/simulate/{lotteryType}` | POST | Processar simulação | Não |

## Como Usar

### 1. Acessar a Aplicação

Inicie o servidor e acesse:
```
http://localhost:8080/web/home
```

### 2. Fazer uma Simulação

1. Acesse o menu **Simular** ou clique em "Começar Simulação"
2. Escolha o tipo de loteria:
   - **Lotofácil**: 25 números disponíveis (1-25)
   - **Mega-Sena**: 60 números disponíveis (1-60)
3. Clique nas bolas para selecionar seus números
   - Não há limite de seleção
   - Bolas selecionadas ficam destacadas em azul
4. Clique em **Simular Loteria**
5. Visualize os resultados comparando seus números com sorteios anteriores

### 3. Alternar Dark Mode

- Clique no ícone de lua/sol no menu superior
- A preferência é salva automaticamente
- No mobile, acesse via menu hamburger

## Tecnologias Utilizadas

### Backend
- **Spring WebFlux**: Framework reativo
- **Thymeleaf**: Template engine para renderização server-side
- **Java 21**: Linguagem de programação

### Frontend
- **Tailwind CSS**: Framework CSS utility-first (via CDN)
- **JavaScript Vanilla**: Sem frameworks adicionais
- **Font Awesome**: Ícones
- **HTML5**: Estrutura semântica

## Paleta de Cores

### Tema Claro
- **Primary**: Tons de azul (#0ea5e9)
- **Accent**: Tons de roxo/magenta (#d946ef)
- **Background**: Gradiente cinza/azul (#f9fafb to #eff6ff)

### Tema Escuro
- **Primary**: Azul claro (#38bdf8)
- **Accent**: Magenta claro (#f0abfc)
- **Background**: Gradiente cinza escuro (#111827 to #1f2937)

## Personalização

### Adicionar Nova Loteria

1. Adicione o novo tipo em `LotteryType.java`:
```java
public enum LotteryType {
    LOTOFACIL,
    MEGASENA,
    NOVA_LOTERIA  // Adicionar aqui
}
```

2. Configure em `lottery-simulator.js`:
```javascript
const lotteryConfig = {
    'NOVA_LOTERIA': {
        maxNumbers: 80,
        recommended: 20,
        color: 'from-red-500 to-red-600'
    }
};
```

3. Adicione o botão em `simulate.html`

### Mudar Cores do Tailwind

Edite a configuração em qualquer arquivo HTML:
```javascript
tailwind.config = {
    theme: {
        extend: {
            colors: {
                primary: { ... },
                accent: { ... }
            }
        }
    }
}
```

## Melhorias Futuras

### Funcionalidades Planejadas
- [ ] Sistema de login/registro
- [ ] Salvamento de apostas favoritas
- [ ] Estatísticas detalhadas (números mais sorteados)
- [ ] Histórico de simulações
- [ ] Notificações de resultados
- [ ] Compartilhamento de apostas
- [ ] Gerador automático de números

### Otimizações
- [ ] Migrar Tailwind CSS para build local
- [ ] Implementar service workers para PWA
- [ ] Adicionar testes de integração para páginas
- [ ] Implementar lazy loading de recursos
- [ ] Otimizar animações para dispositivos móveis

## Observações Importantes

### WebFlux com Thymeleaf
Este projeto usa **Spring WebFlux** (reativo) com Thymeleaf, que requer configuração específica através de `SpringWebFluxTemplateEngine` e `ThymeleafReactiveViewResolver`.

### Autenticação
O sistema está preparado para integração com autenticação JWT. O controller verifica o header `Authorization` e passa a flag `isAuthenticated` para os templates.

### Tailwind via CDN
Atualmente usa Tailwind via CDN para desenvolvimento rápido. Para produção, recomenda-se configurar o build do Tailwind localmente para otimizar o tamanho do CSS.

## Suporte

Para dúvidas ou problemas, consulte:
- Documentação do Spring WebFlux
- Documentação do Thymeleaf
- Documentação do Tailwind CSS

## Autores

Desenvolvido para o projeto Lottery Statistics.
