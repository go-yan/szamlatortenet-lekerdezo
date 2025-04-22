package hu.fg3ubd.fszamla.data.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class QueryResult {

  public String accountId;

  public String accountOwner;

  public Integer trAmount;

  public String trCurrency;

  public String trMessage;

  public String destAccountId;

  public String destAccountOwner;

  public Date dateOfTransaction;

  public String originTaxNumber;
}