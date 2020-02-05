package lk.coder.sequencegenerator;

import org.springframework.stereotype.Component;

import java.sql.*;

/**
 * @Project sequence-generator
 * @Author DILAN on 2/5/2020
 */
@Component
public class DBConnection {

    private final String SQL_CREATE = "CREATE TABLE SEQUENCE"
            + "("
            + " init_seq varchar(20),"
            + " nxt_seq varchar(20),"
            + " type varchar(5)"
            + ")";

    private  final String SQL_INSERT = "INSERT INTO SEQUENCE " +
            "(init_seq, nxt_seq, type) VALUES (?,?,?)";

    private static final String GET_NEXT_SEQ = "SELECT nxt_seq FROM SEQUENCE WHERE TYPE='QR'";

    private static final String GET_INIT_SEQ = "SELECT init_seq FROM SEQUENCE WHERE TYPE='QR'";

    public void createTable() {

        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost/seq_db", "root", "");
             Statement statement = con.createStatement()) {

            // if DDL failed, it will raise an SQLException
            statement.execute(SQL_CREATE);

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Integer insertTable() {

        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost/seq_db", "root", "");
             PreparedStatement pstmt = con.prepareStatement(SQL_INSERT)) {

           pstmt.setString(1, "1000000");
           pstmt.setString(2, "1000001");
           pstmt.setString(3, "QR");

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public Long getNextSeqNo() {
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost/seq_db", "root", "");
             PreparedStatement pstmt = con.prepareStatement(GET_NEXT_SEQ)) {

            ResultSet resultSet = pstmt.executeQuery();
            Long nxt_seq = null;
            while (resultSet.next()) {

                nxt_seq = resultSet.getLong("nxt_seq");
            }

            return nxt_seq;

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Long getInitSeqNo() {
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost/seq_db", "root", "");
             PreparedStatement pstmt = con.prepareStatement(GET_INIT_SEQ)) {

            ResultSet resultSet = pstmt.executeQuery();
            Long init_seq = null;
            while (resultSet.next()) {

                init_seq = resultSet.getLong("init_seq");
            }

            return init_seq;

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Integer updateNextSeq(Long nextSeq) {
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost/seq_db", "root", "");
             PreparedStatement pstmt = con.prepareStatement("UPDATE SEQUENCE SET nxt_seq = ? " +
                     "WHERE type = ?")) {

            pstmt.setLong(1, nextSeq);
            pstmt.setString(2, "QR");

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}
