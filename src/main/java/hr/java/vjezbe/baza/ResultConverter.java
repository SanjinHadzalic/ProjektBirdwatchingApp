package hr.java.vjezbe.baza;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultConverter<T> {
    T converter(ResultSet resultset) throws SQLException;
}
