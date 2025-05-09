package hu.fg3ubd.fszamla.service;

import hu.fg3ubd.fszamla.data.dto.QueryParameters;
import hu.fg3ubd.fszamla.data.dto.QueryResult;
import hu.fg3ubd.fszamla.data.exception.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static hu.fg3ubd.fszamla.constants.ServiceConstants.*;

@ApplicationScoped
@Slf4j
public class AccountHistoryService {

    @Inject
    DataSource dataSource;

    public List<QueryResult> getAccHist(QueryParameters params) throws ValidationException, SQLException {
        var querySelector = validateParameters(params);
        var helper = new BeanListHandler<>(QueryResult.class);
        QueryRunner runner = new QueryRunner(dataSource);
        var query = prepareQuery(params);

        log.info("Preparing query with parameters {},{},{},{}",
                params.getAccountId(),
                params.getTaxNumber(),
                params.getStartDate(),
                params.getEndDate());

        return switch (querySelector) {
            case 1 -> runner.query(query, helper, params.getStartDate(), params.getEndDate(), params.getAccountId());
            case 2 -> runner.query(query, helper, params.getStartDate(), params.getEndDate(), params.getTaxNumber());
            case 3 -> runner.query(query, helper, params.getStartDate(), params.getEndDate(), params.getAccountId(), params.getTaxNumber());
            default -> null;
        };
    }

    public String prepareQuery(QueryParameters params) {
        var baseQuery = BASE_QUERY;
        if (params.getAccountId() != null)
            baseQuery += QUERY_ACCOUNT_ID;
        if (params.getTaxNumber() != null)
            baseQuery += QUERY_TAX_NUMBER;
        return baseQuery;
    }

    public Integer validateParameters(QueryParameters paramsIn) {
        int status = 0;
        boolean isAccountIdPresent = false;
        boolean isTaxNumberPresent = false;

        if (paramsIn.getAccountId() != null) {
            isAccountIdPresent = true;
            Pattern accountIdPattern = Pattern.compile(ACCOUNT_ID_REGEX);
            Matcher accountIdMatcher = accountIdPattern.matcher(paramsIn.getAccountId());
            if (!accountIdMatcher.matches())
                throw new ValidationException(WRONG_ACCOUNT_ID_FORMAT);
        }

        if (paramsIn.getTaxNumber() != null) {
            isTaxNumberPresent = true;
            Pattern taxNumberPattern = Pattern.compile(TAX_NUMBER_REGEX);
            Matcher taxNumberMatcher = taxNumberPattern.matcher(paramsIn.getTaxNumber());
            if (!taxNumberMatcher.matches())
                throw new ValidationException(WRONG_TAX_ID_FORMAT);
        }

        if (isAccountIdPresent && !isTaxNumberPresent)
            status = 1;
        if (!isAccountIdPresent && isTaxNumberPresent)
            status = 2;
        if (isAccountIdPresent && isTaxNumberPresent)
            status = 3;

        if (!isAccountIdPresent && !isTaxNumberPresent)
            throw new ValidationException(LACK_OF_ACCOUNT_ID_TAX_ID);
        return status;
    }


}
