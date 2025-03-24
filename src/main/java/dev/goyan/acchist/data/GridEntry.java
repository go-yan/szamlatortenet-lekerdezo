package dev.goyan.acchist.data;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.Data;

import java.time.LocalDate;

@ApplicationScoped
@RegisterForReflection
@Data
public class GridEntry {

    boolean reportStatus;

    boolean isNavReport;

    LocalDate dateOfReport;

    boolean isDone;

    boolean State;

    Long gwbAddress;

    String nameOfClient;

    Long taxNumber;

    Long taxId;

    Long accountId;
}
