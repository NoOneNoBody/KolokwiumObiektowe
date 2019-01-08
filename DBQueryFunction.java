import java.sql.ResultSet;
import java.sql.SQLException;

public interface DBQueryFunction {
    void execute(ResultSet rs) throws SQLException;
}
