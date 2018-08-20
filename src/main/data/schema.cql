CREATE KEYSPACE orc WITH replication = {'class': 'NetworkTopologyStrategy', 'SearchGraphAnalytics': 1 };
CREATE TABLE orc.sample_txt (
    orderkey text,
    custkey text,
    orderstatus text,
    totalprice text,
    orderdate text,
    orderpriority text,
    clerk text,
    shippriority text,
    comment text,
    PRIMARY KEY ((orderkey))
);