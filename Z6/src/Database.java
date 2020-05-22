import java.sql.*;

public class Database {
    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:logs.db";

    private static Database db_instance = null;

    private Connection conn;
    private Statement stat;

    private Database() {
        try {
            Class.forName(Database.DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Brak sterownika JDBC");
            e.printStackTrace();
        }

        try {
            conn = DriverManager.getConnection(DB_URL);
            stat = conn.createStatement();
        } catch (SQLException e) {
            System.err.println("Problem z otwarciem polaczenia");
            e.printStackTrace();
        }

        createTable();
    }

    static public Database getInstance() {
        if (db_instance == null) {
            db_instance = new Database();
        }
        return db_instance;
    }

    private void createTable() {
        try {
            stat.execute("DROP TABLE Logs");
            stat.execute("CREATE TABLE IF NOT EXISTS Logs (id INTEGER PRIMARY KEY AUTOINCREMENT, question varchar(255), counting int)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(String question, int count) throws SQLException {
        PreparedStatement prepStmt = conn.prepareStatement("INSERT INTO Logs VALUES (NULL, ?, ?);");
        prepStmt.setString(1, question.toLowerCase());
        prepStmt.setInt(2, count);
        prepStmt.execute();
    }

    public void showAll() throws SQLException {
        ResultSet result = stat.executeQuery("SELECT * FROM Logs;");
        while (result.next()) {
            String question = result.getString("question");
            int count = result.getInt("counting");
            System.out.println(question + ", " + count);
        }
    }

    public void delete(String question) throws SQLException {
        stat.execute("DELETE FROM Logs WHERE question = '" + question + "';");
    }

    synchronized public int getQuestionCount(String question) throws SQLException {
        ResultSet result = stat.executeQuery("SELECT * FROM Logs WHERE question = '" + question.toLowerCase() + "';");
        int count = 0;
        if (result != null && result.next()) {
            count = result.getInt("counting");
        }
        return count;
    }

    synchronized public void increase(String question) throws SQLException {
        int count = getQuestionCount(question);
        if (count > 0) delete(question.toLowerCase());
        insert(question.toLowerCase(), count+1);
    }
}
