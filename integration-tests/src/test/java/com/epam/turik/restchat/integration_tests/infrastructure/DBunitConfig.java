package com.epam.turik.restchat.integration_tests.infrastructure;

import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.DataTypeException;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.springframework.context.annotation.Bean;

import java.sql.Types;

public class DBunitConfig {

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
}
