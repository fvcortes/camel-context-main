# Project used for experimenting with CamelRuntime

## Building the Project

Build the project with:

```bash
mvn clean package
```

## Running the Project

Execute the following command to run the project locally using AWS SAM Local:

```bash
samlocal local invoke -t template.jvm.yml Validate -e events/yaml-validate.json
```
