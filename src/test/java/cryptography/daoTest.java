package cryptography;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
class daoTest {

    @Test
    void encryptData() throws SQLException, ClassNotFoundException {
        dao dbManager = new dao();
        String key = "key";
        String data = "Hello";
        String encryptedData = dbManager.encryptData(key, data);
        String actualEncryptedData = "F|?#";
        assertEquals(encryptedData, actualEncryptedData);
        assertNotEquals(encryptedData, actualEncryptedData+"a");
    }

    @Test
    void decryptData() throws SQLException, ClassNotFoundException {
        dao dbManager = new dao();
        String key = "key";
        String encryptedData = "F|?#";
        String decryptedData = dbManager.decryptData(key, encryptedData);
        String originalData = "Hello";
        assertEquals(decryptedData, originalData);
        assertNotEquals(decryptedData, originalData+"a");
    }

    @Test
    void insertDataIntoDb() throws SQLException, ClassNotFoundException {
        dao dbManager = new dao();
        String name = "Ram";
        String data = "New York is good city";
        int id = dbManager.insertDataIntoDb(name, data);
        String[] row = dbManager.retrieveDataFromDb(id);
        String fetchedData = row[0];
        String fetchedName = row[1];
        assertEquals(name, fetchedName);
        assertNotEquals(name, fetchedName+"a");
        assertEquals(data, fetchedData);
        assertNotEquals(data, fetchedData+"a");
    }

    @Test
    void retrieveDataFromDb() throws SQLException, ClassNotFoundException {
        dao dbManager = new dao();
        int id = 7;
        String expectedName = "Ram";
        String expectedData = "New York is good city";
        String[] row = dbManager.retrieveDataFromDb(id);
        String fetchedData = row[0];
        String fetchedName = row[1];
        assertEquals(expectedName, fetchedName);
        assertNotEquals(expectedName, fetchedName+"a");
        assertEquals(expectedData, fetchedData);
        assertNotEquals(expectedData, fetchedData+"a");
    }
}