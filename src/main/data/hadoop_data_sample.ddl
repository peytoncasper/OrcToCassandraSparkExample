DROP DATABASE IF EXISTS sample_database;
CREATE DATABASE sample_database;

USE sample_database;

DROP TABLE IF EXISTS sample_txt;
CREATE TABLE sample_txt( 
	orderkey STRING, 
	custkey STRING, 
	orderstatus STRING, 
	totalprice STRING, 
	orderdate STRING, 
	orderpriority STRING, 
	clerk STRING, 
	shippriority STRING, 
	comment STRING) 
ROW FORMAT DELIMITED 
FIELDS TERMINATED BY '|' 
STORED AS TEXTFILE
;


DROP TABLE IF EXISTS sample_orc;
create table sample_orc( 
	orderkey STRING, 
	custkey STRING, 
	orderstatus STRING, 
	totalprice STRING, 
	orderdate STRING, 
	orderpriority STRING, 
	clerk STRING, 
	shippriority STRING, 
	comment STRING) 
STORED AS ORC  tblproperties ("orc.compress"="SNAPPY");
;

-- Load into Text table
LOAD DATA INPATH '/sample.dat' INTO TABLE sample_txt;

-- Copy to ORC table
INSERT INTO TABLE sample_orc SELECT * FROM sample_txt;
