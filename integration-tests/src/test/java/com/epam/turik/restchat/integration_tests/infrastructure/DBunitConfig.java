package com.epam.turik.restchat.integration_tests.infrastructure;

import com.github.springtestdbunit.bean.DatabaseConfigBean;
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean;
import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.DataTypeException;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.Types;

public class DBunitConfig {

    @Value("${spring.datasource.url}")
    private String datasourceUrl;
    @Value("${spring.datasource.username}")
    private String datasourceUsername;
    @Value("${spring.datasource.password}")
    private String datasourcePassword;

    @Bean(name="dataTypeFactory")
    PostgresqlDataTypeFactory dataTypeFactory() {
        return new PostgresqlDataTypeFactory(){
            @Override
            public boolean isEnumType(String sqlTypeName) {
                return "user_status".equalsIgnoreCase(sqlTypeName) || "chat_permission".equalsIgnoreCase(sqlTypeName);
            }

            @Override
            public DataType createDataType(int sqlType, String sqlTypeName) throws DataTypeException {
                if (isEnumType(sqlTypeName)) {
                    sqlType = Types.OTHER;
                }
                return super.createDataType(sqlType, sqlTypeName);
            }
        };
    }

    @Bean(name="dataSource")
    DriverManagerDataSource dataSource() {
        return new DriverManagerDataSource(datasourceUrl, datasourceUsername, datasourcePassword);
    }

    @Bean
    DatabaseConfigBean databaseConfig(PostgresqlDataTypeFactory dataTypeFactory) {
        DatabaseConfigBean databaseConfig = new DatabaseConfigBean();
        databaseConfig.setEscapePattern("\"");
        databaseConfig.setDatatypeFactory(dataTypeFactory);
        return databaseConfig;
    }

    @Bean
    DatabaseDataSourceConnectionFactoryBean dbUnitDatabaseConnection(DriverManagerDataSource dataSource, DatabaseConfigBean databaseConfigBean) {
        DatabaseDataSourceConnectionFactoryBean connection = new DatabaseDataSourceConnectionFactoryBean(dataSource);
        connection.setDatabaseConfig(databaseConfigBean);
        return connection;
    }
}
