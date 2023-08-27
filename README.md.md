## Aplicações backend que publicam e consomem mensagens de tópicos do Pub/Sub do Google Cloud.

O projeto app-payment-food-pub é um publisher que envia mensagem para um tópico do Pub/Sub do GCP.
O projeto app-payment-food-sub é um subscriber que recebe mensagens de um tópico do Pub/Sub do GCP.

Aqui tem um artigo legal falando a respeito:
https://gabriel-faraday.medium.com/google-cloud-pub-sub-o-que-%C3%A9-como-funciona-cbf70c5edb66

```
Abaixo tem uma descrição interessante pega do ChatGPT:

No Google Cloud Pub/Sub, os tópicos são projetados para armazenar temporariamente as mensagens para que os assinantes (consumidores) possam processá-las. No entanto, as mensagens não são armazenadas permanentemente no tópico. Por padrão, as mensagens em um tópico Pub/Sub expiram após 7 dias.

Se você deseja manter as mensagens no tópico indefinidamente e evitar a expiração, o Pub/Sub não é a melhor ferramenta para esse cenário. O Google Cloud Pub/Sub é mais adequado para fluxos de dados em tempo real, onde as mensagens são processadas e entregues rapidamente aos consumidores.

Se você precisa armazenar as mensagens de forma permanente, considere usar um serviço de armazenamento de dados, como o Google Cloud Storage ou um banco de dados, dependendo dos requisitos da sua aplicação. Esses serviços são projetados para armazenar dados por longos períodos de tempo.

Se o motivo de você querer manter as mensagens no tópico é ter um buffer temporário enquanto os consumidores estão inativos, você pode ajustar os tempos de retenção das mensagens. Isso permite que as mensagens persistam por mais tempo no tópico, mas elas ainda serão removidas eventualmente.

Para modificar o tempo de retenção das mensagens em um tópico Pub/Sub, você pode usar a API do Pub/Sub ou a interface da linha de comando gcloud. No entanto, mesmo com isso, não será possível manter as mensagens indefinidamente no tópico.

Lembre-se de projetar sua arquitetura considerando as necessidades reais do seu sistema e utilizando as ferramentas adequadas para cada cenário.
```

### Caso queira, tem um arquivo 'docker-compose.yml' na raiz do projeto que cria um emulador do Pub/Sub localmente.

### Opção gráfica para configurar projetos Pub/Sub, visualizar, enviar mensagens, etc.
https://github.com/NeoScript/pubsub-emulator-ui

### Abaixo tem alguns exemplos para usar o emulador do GCP para publicar e receber mensagens do PubSub:

172.17.0.1 - Usado no Linux
localhost - Usado no Windows

- Criar um tópico... necessário rodar o comando abaixo para criar o tópico no emulador:

curl -X PUT -H "Content-Type: application/json" -d '{}' http://172.17.0.1:8085/v1/projects/<PROJECT_ID>/topics/<TOPIC>

OBS: O IP mencionado é do host interno do Docker. Ou seja, estamos acessando de fora do container.

- Listar tópicos criados:

curl -X GET http://172.17.0.1:8085/v1/projects/<PROJECT_ID>/topics

- Para criar um subscription que receba mensagens (pull):

curl -X PUT -H "Content-Type: application/json" -d '{"topic": "projects/<PROJECT_ID>/topics/<TOPIC>"}' http://172.17.0.1:8085/v1/projects/<PROJECT_ID>/subscriptions/<SUBSCRIPTION_PULL>

- Para criar um subscription que notifique em um endpoint assim que há uma mensagem chegando no tópico (trigger/push), é necessário passar as seguintes configurações:

Ficando assim:
curl -X PUT -H "Content-Type: application/json" -d '{"topic": "projects/<PROJECT_ID>/topics/<TOPIC>", "pushConfig": {"pushEndpoint": "http://<HOST_NOTIFICATION>:<PORT_NOTIFICATION>/<PATH_NOTIFICATION>"}}' http://172.17.0.1:8085/v1/projects/<PROJECT_ID>/subscriptions/<SUBSCRIPTION_PUSH>

É possível também passar um tempo de retenção das mensagens. Exemplo:

{
  "topic": ...,
  "pushConfig": {
    ...
  },
  "messageRetentionDuration": "604800s" // Aqui você pode configurar o tempo de retenção das mensagens
}

- Publicando uma mensagem no tópico:

curl -X POST -H "Content-Type: application/json" -d '{"messages": [{"data": "SGVsbG8gV29ybGQh"}]}' http://172.17.0.1:8085/v1/projects/<PROJECT_ID>/topics/<TOPIC>:publish

OBS: a mensagem enviada acima é em Base64, e dentro contém o valor Hello World.

- Listar mensagens de um subscription:

curl -X POST -H "Content-Type: application/json" -d '{"returnImmediately": true, "maxMessages": 10}' http://172.17.0.1:8085/v1/projects/<PROJECT_ID>/subscriptions/<SUBSCRIPTION_PULL>:pull