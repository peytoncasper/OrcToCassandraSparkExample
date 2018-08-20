# Start The Cloudera Docker Instance
```
sudo docker run --hostname=quickstart.cloudera --privileged=true -t -i -p 8888 -p 50010 -p 8020 -p 10000 -p 9083 -d cloudera/quickstart:latest /usr/bin/docker-quickstart
```

# Obtain The HDFS URL
```
sudo docker ps

CONTAINER ID        IMAGE                        COMMAND                  CREATED             STATUS              PORTS                                                                                                                           NAMES
5ce78754331f        cloudera/quickstart:latest   "/usr/bin/docker-qui…"   About an hour ago   Up About an hour    0.0.0.0:32782->8020/tcp, 0.0.0.0:32781->8888/tcp, 0.0.0.0:32780->9083/tcp, 0.0.0.0:32779->10000/tcp, 0.0.0.0:32778->50010/tcp   distracted_kirch
```

In our case the HDFS 8020 port mapped to 32782 which means that our HDFS URL will be 

```
localhost:32782
```

# Compile The Project

```
sbt package
```

# Load The Sample Data into Hive

```
dse spark-submit --class LoadSampleData target/scala-2.11/orctocassandrasparkexample_2.11-0.1.jar localhost:32782 file:///home/peyton/Documents/FloridaBlueDataTransformer/src/main/data/sample.dat
```

# Attach to the Cloudra Docker Instance

### Fetch the container hash

```
sudo docker ps

CONTAINER ID        IMAGE                        COMMAND                  CREATED             STATUS              PORTS                                                                                                                           NAMES
5ce78754331f        cloudera/quickstart:latest   "/usr/bin/docker-qui…"   About an hour ago   Up About an hour    0.0.0.0:32782->8020/tcp, 0.0.0.0:32781->8888/tcp, 0.0.0.0:32780->9083/tcp, 0.0.0.0:32779->10000/tcp, 0.0.0.0:32778->50010/tcp   distracted_kirch
```

### Attach to the docker container
```
sudo docker attach 5ce78754331f
```


# Create Sample Schema

### Create the schema file
```
touch /home/cloudera/schema.ddl
```

Utilize vi to paste /src/main/data/hadoop_data_sample.ddl into /home/cloudera/schema.ddl



# Create Cassandra Schema

### Fetch your Cassandra DC name
```
nodetool status

Datacenter: SearchGraphAnalytics
================================
Status=Up/Down
|/ State=Normal/Leaving/Joining/Moving
--  Address    Load       Owns    Host ID                               Token                                    Rack
UN  127.0.0.1  147.97 MiB  ?       7cec3ce0-81f5-4afd-94ab-e7a6f8722b93  -6424889293112165957                     rack1

Note: Non-system keyspaces don't have the same replication settings, effective ownership information is meaningless
```

### Update the Cassandra keyspace with your DC name

Modify src/main/data/schema.cql

```
CREATE KEYSPACE orc WITH replication = {'class': 'NetworkTopologyStrategy', 'SearchGraphAnalytics': 1 };
``` 

### Load the Cassandra Schema
```
cqlsh -f src/main/data/schema.cql
```

# Copy Orc file to Cassandra

```
dse spark-submit --class Main target/scala-2.11/orctocassandrasparkexample_2.11-0.1.jar localhost:32782
```