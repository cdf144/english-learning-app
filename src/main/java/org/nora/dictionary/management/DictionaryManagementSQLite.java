package org.nora.dictionary.management;

import org.nora.dictionary.entities.Word;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DictionaryManagementSQLite implements IDictionaryManagement {
    private static final Logger LOGGER = Logger.getLogger(DictionaryManagement.class.getName());

    public static final String PATH_DICTMGMT_SQLITE_LOG = System.getProperty("user.dir")
            + File.separator + "log"
            + File.separator + "logDictionaryManagementSQLite.log";

    private static final FileHandler logFileHandler;
    static {
        try {
            logFileHandler = new FileHandler(PATH_DICTMGMT_SQLITE_LOG, false);
            logFileHandler.setLevel(Level.INFO);
            LOGGER.addHandler(logFileHandler);
        } catch (IOException e) {
            throw new RuntimeException("Could not initalize DictionaryManagementSQLite log " +
                    "FileHandler!", e);
        }
    }

    protected List<Word> searchResultList;

    public static final String PATH_EV_DB = System.getProperty("user.dir")
            + File.separator + "src"
            + File.separator + "main"
            + File.separator + "resources"
            + File.separator + "dict_hh.db";

    public static final String URL_EV_DB = "jdbc:sqlite:" + PATH_EV_DB;

    public Connection connection;

    public DictionaryManagementSQLite() {
        searchResultList = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(URL_EV_DB);
            LOGGER.info("Connection to SQLite DB successful.");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new RuntimeException(e);
        }
    }

    public List<Word> getSearchResultList() {
        return searchResultList;
    }

    public void setSearchResultList(List<Word> searchResultList) {
        this.searchResultList = searchResultList;
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public String dictionaryLookup(String wordTarget) {
        String sql = "SELECT html FROM av WHERE word = ?";

        String explain;
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, wordTarget);
            ResultSet resultSet = preparedStatement.executeQuery();
            explain = resultSet.getString("html");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return explain;
    }

    public Word dictionaryLookupWord(String wordTarget) {
        String sql = "SELECT html FROM av WHERE word = ?";

        String explain;
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, wordTarget);
            ResultSet resultSet = preparedStatement.executeQuery();
            explain = resultSet.getString("html");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (explain == null) {
            return null;
        }

        return new Word(wordTarget, explain);
    }

    @Override
    public void dictionarySearcher(String wordTarget) {
        searchResultList.clear();
        String sql = "SELECT word, html FROM av WHERE word LIKE ? ORDER BY word ASC LIMIT 20000";

        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, wordTarget + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                searchResultList.add(
                        new Word(resultSet.getString("word"), resultSet.getString("html"))
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean wordExist(String wordTarget) {
        return dictionaryLookup(wordTarget) != null;
    }

    @Override
    public void addToDictionary(String wordTarget, String wordExplain) {
        String sql = "INSERT INTO av(word,html) VALUES(?,?)";

        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, wordTarget);
            preparedStatement.setString(2, wordExplain);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addToDictionary(String wordTarget, String wordExplain, String shortDesc,
                                String pronunciation) {
        String sql = "INSERT INTO av(word,html,description,pronounce) VALUES(?,?,?,?)";

        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, wordTarget);
            preparedStatement.setString(2, wordExplain);
            preparedStatement.setString(3, shortDesc);
            preparedStatement.setString(4, pronunciation);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean removeFromDictionary(String wordTarget) {
        if (!wordExist(wordTarget)) {
            return false;
        }

        String sql = "DELETE FROM av WHERE word = ?";

        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, wordTarget);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    @Override
    public void updateInDictionary(String wordTarget, String wordExplain) {
        String sql = "UPDATE av SET html = ? WHERE word = ?";

        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, wordExplain);
            preparedStatement.setString(2, wordTarget);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
            LOGGER.info("Connection to SQLite DB closed.");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }
}
