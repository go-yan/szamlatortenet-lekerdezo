package hu.fg3ubd.fszamla.constants;

public final class ServiceConstants {

    public static final String BASE_QUERY = "SELECT * FROM Fszamla.szamla_tortenet WHERE dateOfTransaction BETWEEN ? AND ?";
    public static final String QUERY_ACCOUNT_ID = " AND accountId = ?";
    public static final String QUERY_TAX_NUMBER = " AND originTaxNumber = ?";

    public static final String ACCOUNT_ID_REGEX = "^\\d{16}$";
    public static final String TAX_NUMBER_REGEX = "^\\d{10}$";

    public static final String WRONG_ACCOUNT_ID_FORMAT = "Hibás számlaszám formátum!";
    public static final String WRONG_TAX_ID_FORMAT = "Hibás adóazonosító jel formátum!";
    public static final String LACK_OF_PARAMETERS = "Számlaszámot vagy Adóazonosító jelet kötelező megadni!";
}
