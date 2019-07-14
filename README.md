# Marketplace Catalog

### Simple demonstration application

Key functions:

* Scanning marketplace for new loans each 5 minutes. (interval can be changed in properties file)
* Notifying about new loans in console.
* Saving found loans to hard drive in JSON format.
* On demand latest loans in past 120 minutes via REST endpoint. (interval can be changed in properties file)

### How to install:

1. Clone or download this repo.

2. Download and install apache Maven if necessary.

```
https://maven.apache.org/download.cgi
```

```
https://maven.apache.org/install.html
```
3. Run maven command inside root directory from command line.

```
mvn clean package
```

4. Run Jar file using java command from command line.
```
java -jar marketplace-catalog-1.0.0
```

### Usage

* Application has embedded tomcat in itself, 
so no external Application server / servlet container is required

* Once started, all information is available in console

* Loans are exported to specified folder within home directory of application (specified in application.properties)

* On demand information about latest loans is available at REST endpoint "${application_url}/latestLoans"