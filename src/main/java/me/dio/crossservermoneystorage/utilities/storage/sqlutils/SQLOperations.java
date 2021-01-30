package me.dio.crossservermoneystorage.utilities.storage.sqlutils;

public class SQLOperations {

    static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS Account (uuid VARCHAR(36), balance DOUBLE)";
    static String OBTAIN = "SELECT balance FROM Account where uuid = ?";
    static String SAVE = "INSERT INTO Account(uuid,balance) VALUES ?, ? ON DUPLICATE KEY UPDATE uuid=VALUES(uuid), money=VALUES(balance)";

}