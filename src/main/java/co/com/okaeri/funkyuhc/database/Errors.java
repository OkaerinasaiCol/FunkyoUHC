package co.com.okaeri.funkyuhc.database;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class Errors {
    @Contract(pure = true)
    public static @NotNull String sqlConnectionExecute() {
        return "Couldn't execute MySQL statement: ";
    }

    @Contract(pure = true)
    public static @NotNull String sqlConnectionClose() {
        return "Failed to close MySQL connection: ";
    }

    @Contract(pure = true)
    public static @NotNull String noSQLConnection() {
        return "Unable to retreive MYSQL connection: ";
    }

    @Contract(pure = true)
    public static @NotNull String noTableFound() {
        return "Database Error: No Table Found";
    }
}