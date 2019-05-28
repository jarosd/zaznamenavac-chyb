package fri.jarosd.vpa.bugs.datoveEntity;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TypZmenyTypeHandler extends BaseTypeHandler<TypZmeny> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, TypZmeny typZmeny, JdbcType jdbcType) throws SQLException {
        // na i-tu hodnotu v SQL príkaze nájdi preklad ku enumu
        preparedStatement.setString(i, typZmeny.toString());
    }

    @Override
    public TypZmeny getNullableResult(ResultSet resultSet, String s) throws SQLException {
        // mám enum, ale neviem si to preložiť na String - z resultSetu si musím získať hodnotu na hľadanie v enum
        return TypZmeny.getEnum(resultSet.getString(s));
    }

    @Override
    public TypZmeny getNullableResult(ResultSet resultSet, int i) throws SQLException {
        // to isté ako predošlé, avšak jednotlivé atribúty sú číslované
        return TypZmeny.getEnum(resultSet.getString(i));
    }

    @Override
    public TypZmeny getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        // to isté ako predošlé, avšak pre callable statement (neviem presne čo to je)
        return TypZmeny.getEnum(callableStatement.getString(i));
    }
}
