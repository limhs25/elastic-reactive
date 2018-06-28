# Documentation
- Getting started
    - Install node and angular.
    - Install Elasticsearch and create the schema.
    - Maven commands and java commands.
- Project design
    - Reactive programming.
    - Frontend architecture.
    - Backend architecture.

## Getting started
You need to set up your development environment before you can do anything.
### Homebrew
Homebrew is a package manager for the Mac — it makes installing most open source sofware (like Node) as simple as writing brew install node. You can learn more about Homebrew at the Homebrew website. To install Homebrew just open Terminal and type 

```bash
ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
``` 

### Node
Installing Node.js and NPM is pretty straightforward using Homebrew. Homebrew handles downloading, unpacking and installing Node and NPM on your system.

```bash
brew install node
```

Check for versions angular need at least Node.js version 8.x or greater and npm version 5.x or greater
```bash
node -v
npm -v
```

### Angular
To install angular cli you just need to type in the terminal 

```bash
npm install -g @angular/cli
```

### Elasticsearch
You need to install elasticsearch. You can check their web site to download and start Elasticsearch [Elasticsearch](https://www.elastic.co/downloads/elasticsearch).
Then create an index using this command.

```bash
curl -X PUT "localhost:9200/logic-distributions" -H 'Content-Type: application/json' -d'
{
  "settings": {
    "number_of_shards": 1
  },
  "mappings": {
    "logic": {
      "properties": {
        "active": {
          "type": "boolean"
        },
        "id": {
          "type": "text"
        },
        "logic": {
          "type": "text"
        },
        "service": {
          "type": "text"
        },
        "share": {
          "type": "long"
        }
      }
    }
  }
}
'
```
### Java and maven
You need to have java 8 or greater and maven installed obviously...

To compile the project just run 
```bash
mvn clean install -DskipTests
```
this will take some time as maven downloads the necessary dependencies and compile Angular frontend part then package everything into one jar file located in target folder.

And to run the application like prod mode type in a terminal
```bash
java -jar <jar_path>
```

## Project design

### Reactive programming.
Reactive Programming, is a development model structured around asynchronous data streams. With reactive programming, you observe these streams and react when a value is emitted.
[Link to article](https://dzone.com/articles/5-things-to-know-about-reactive-programming) for better understanding of the Reactive paradigm.

In this application the Reactive part is seen from the Rest endpoints that translates to service transformations and a backend datasource accessed with the [Java High Level REST Client](https://www.elastic.co/guide/en/elasticsearch/client/java-rest/master/java-rest-high.html#java-rest-high) which is an asynchronous client for Elasticsearch.

The request is handled completely in a asynchronous way.

```java
@Override
public Mono<Logic> updateLogic(String id, Logic logic) {
    return loadLogic(id)
            .map(e -> new UpdateRequest(INDEX, TYPE, id).doc(ACTIVE, logic.getActive().toString()))
            .flatMap(logicRepository::update)
            .doOnSuccess(processorService::onNext)
            .doOnError(Throwable::printStackTrace);
}
```

### Frontend architecture.
Folders
- app
    - dashboards
    - logics
        - logic
        - logic-editor
        - logic-search

###  Backend architecture.
Packages 
- com.rauten.dashboard.logic
    - api
    - config
    - model
    - repository
    - service
