# Configuração de Secrets no Google Cloud Secret Manager

## 1. Criar o Secret EVOLUTION_API_KEY

### Via Console do Google Cloud:
1. Acesse: https://console.cloud.google.com/security/secret-manager
2. Selecione o projeto: `lottery-validation-service`
3. Clique em **"CREATE SECRET"**
4. Preencha:
   - **Name**: `EVOLUTION_API_KEY`
   - **Secret value**: `429683C4C977415CAAFCCE10F7D57E11`
   - **Regions**: Deixe como padrão ou selecione `us-east1`
5. Clique em **"CREATE SECRET"**

### Via gcloud CLI:
```bash
# Criar o secret
echo -n "429683C4C977415CAAFCCE10F7D57E11" | gcloud secrets create EVOLUTION_API_KEY \
  --project=lottery-validation-service \
  --replication-policy="automatic" \
  --data-file=-

# Verificar se foi criado
gcloud secrets describe EVOLUTION_API_KEY --project=lottery-validation-service

# Listar versões
gcloud secrets versions list EVOLUTION_API_KEY --project=lottery-validation-service
```

## 2. Dar permissões ao Cloud Run

```bash
# Obter o service account do Cloud Run
gcloud run services describe lottery-validation-service \
  --region=us-east1 \
  --format="value(spec.template.spec.serviceAccountName)"

# Dar permissão de acesso ao secret (substitua SERVICE_ACCOUNT pelo valor obtido)
gcloud secrets add-iam-policy-binding EVOLUTION_API_KEY \
  --member="serviceAccount:SERVICE_ACCOUNT" \
  --role="roles/secretmanager.secretAccessor" \
  --project=lottery-validation-service
```

## 3. Verificar se está funcionando

Após deploy, você pode verificar os logs:
```bash
gcloud run logs read lottery-validation-service --region=us-east1 --limit=50
```

## 4. Secrets configurados no projeto

| Secret Name | Uso | Versão |
|------------|-----|--------|
| `PASSWORD_DB` | Senha do MongoDB Atlas | 1 |
| `EVOLUTION_API_KEY` | API Key da Evolution API (WhatsApp) | 1 |
| `JWT_SECRET_KEY` | Chave secreta para assinatura JWT (HS256) | 1 |
| `JWT_EXPIRATION_MINUTES` | Tempo de expiração do token JWT em minutos | 1 |

## 5. Configurar Secrets JWT

### Gerar chave secreta (HS256)

```bash
# Gerar uma chave secreta forte com 256 bits (32 bytes em base64)
openssl rand -base64 32

# Ou gerar uma string aleatória
openssl rand -hex 32
```

### Criar secrets no Google Cloud Secret Manager

```bash
# Criar secret para chave secreta JWT
echo -n "sua-chave-secreta-gerada-aqui" | gcloud secrets create JWT_SECRET_KEY \
  --project=lottery-validation-service \
  --replication-policy="automatic" \
  --data-file=-

# Criar secret para tempo de expiração (30 minutos por padrão)
echo -n "30" | gcloud secrets create JWT_EXPIRATION_MINUTES \
  --project=lottery-validation-service \
  --replication-policy="automatic" \
  --data-file=-

# Dar permissões ao service account para acessar os secrets
gcloud secrets add-iam-policy-binding JWT_SECRET_KEY \
  --member="serviceAccount:SERVICE_ACCOUNT" \
  --role="roles/secretmanager.secretAccessor" \
  --project=lottery-validation-service

gcloud secrets add-iam-policy-binding JWT_EXPIRATION_MINUTES \
  --member="serviceAccount:SERVICE_ACCOUNT" \
  --role="roles/secretmanager.secretAccessor" \
  --project=lottery-validation-service
```

### Para desenvolvimento local

No arquivo `.env`, adicione as variáveis:

```dotenv
JWT_SECRET_KEY=sua-chave-secreta-com-no-minimo-32-caracteres-para-hs256-seguro
JWT_EXPIRATION_MINUTES=30
```

⚠️ **IMPORTANTE**: 
- A chave secreta deve ter no mínimo 256 bits (32 caracteres) para o algoritmo HS256
- Nunca faça commit do arquivo `.env` com a chave secreta real
- Use o arquivo `.env.example` como referência
- Gere uma chave diferente para cada ambiente (desenvolvimento, produção)

## 6. Remover do .env (apenas em produção)

⚠️ **IMPORTANTE**: Após confirmar que está funcionando no GCP, remova a linha do `.env`:
```dotenv
# EVOLUTION_API_KEY=429683C4C977415CAAFCCE10F7D57E11  # Agora no Secret Manager
```

Para desenvolvimento local, mantenha no `.env` (nunca faça commit desse arquivo).
