package hu.fg3ubd.fszamla.data.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class QueryParameters {

    String taxNumber;

    String accountId;

    LocalDate startDate;

    LocalDate endDate;
}
