package worksBeyond;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.data.neo4j.config.BasePackageScanner;
import org.springframework.data.neo4j.rest.SpringRestGraphDatabase;

@Configuration
@EnableNeo4jRepositories(basePackages = "worksBeyond")

public class neo4jConfiguration extends Neo4jConfiguration{
	
	public neo4jConfiguration(){
		setBasePackage("worksBeyond");
	}
	
	@Bean
    GraphDatabaseService graphDatabaseService() {
		//GraphAwareRuntime runtime = GraphAwareRuntimeFactory.createRuntime("accessingdataneo4j.db");
		//return new SpringRestGraphDatabase("http://localhost:7474/db/data/"); //FOR REMOTE NEO4J SERVER...WHEN YOU ARE READY
        return new GraphDatabaseFactory().newEmbeddedDatabase("accessingdataneo4j.db");
    }
	
	
	
	
	
	@Autowired
    GraphDatabase graphDatabase;


}
