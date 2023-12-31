# zoop-paymentinit-sample
Se você é desenvolvedor (ou tem acesso à uma equipe de desenvolvimento) e deseja criar seu próprio aplicativo para cobranças utilizando o Iniciador de Pagamentos Openbanking da Zoop.

Você pode fazer forks do projeto ou simplesmente criar seu próprio repositório com o conteúdo alterado. Como esta é uma solução open-source, queremos o apoio da comunidade. Correções e PRs são super bem vindos.

Caso note algum problema com seu funcionamento, entre em contato com nosso suporte através do e-mail: suporte@zoop.com.br.

## Configuração do sample

Adicione as credenciais `MARKETPLACE_ID`,`SELLER_ID`, `API_KEY` no `local.properties` do projeto com os devidos valores.

Exemplo com valores fictícios:

```
sdk.dir=/Users/zoop/Library/Android/sdk

MARKETPLACE_ID="9876543210"
SELLER_ID="0123456789"
API_KEY="9876543210"
```

Caso tenha dúvidas em relação a essas credenciais entrar em contato com o suporte. 

## Credenciais para download da SDK

A sdk é baixada através de um repositório maven referenciado no `build.gradle.kts` raiz do projeto.

As variáveis de ambiente `GITHUB_USER`,`GITHUB_PAT` devem ser configuradas de acordo com as instruções do repositório maven de pacotes públicos da Zoop:
https://github.com/getzoop/zoop-package-public


## Configuração do AppLink

Para que seu aplicativo seja reaberto corretamente após sair do aplicativo do banco, é necessário associar o aplicativo ao domínio da Zoop

Para tal siga a [documentação oficial do Android](https://developer.android.com/training/app-links/verify-android-applinks?hl=pt-br#web-assoc) para gerar um arquivo `assetlinks.json` com o fingerprint do seu app.

O arquivo gerado deverá ser enviado para Zoop para que possamos vincular ao nosso domínio corretamente.

## Licença

zoop-android-sample está licenciada sob os termos da licença [MIT License](LICENSE) e está disponível gratuitamente.


## Links

* [Documentação](https://getzoop.github.io/zoop-sdk-plugin-paymentinit/)
* [Suporte](suporte@zoop.com.br)