package com.gummarajum.automation.restmate.svc;

import com.gummarajum.automation.restmate.ApiException;
import com.gummarajum.automation.restmate.ApiExceptionType;
import io.cucumber.datatable.DataTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DataTableSvc {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataTableSvc.class);

    public static final String FILE_PREFIX = "file:";
    public static final String DATA_TABLE_IS_EMPTY_OR_NULL = "data table is empty or null";

    public List<String> getFirstColsAsList(final DataTable dataTable) {
        if (dataTable == null) {
            LOGGER.error(DATA_TABLE_IS_EMPTY_OR_NULL);
            throw new ApiException(ApiExceptionType.UNDEFINED, DATA_TABLE_IS_EMPTY_OR_NULL);
        }
        return dataTable.asList(String.class);
    }

    public Map<String,String> getTwoColumnsAsMap(final DataTable dataTable){
        if (dataTable == null) {
            LOGGER.error(DATA_TABLE_IS_EMPTY_OR_NULL);
            throw new ApiException(ApiExceptionType.UNDEFINED, DATA_TABLE_IS_EMPTY_OR_NULL);
        }
        return dataTable.asMap(String.class, String.class);
    }
}
