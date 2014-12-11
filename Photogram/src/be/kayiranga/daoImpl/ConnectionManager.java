package be.kayiranga.daoImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
	private static ConnectionManager instance = new ConnectionManager();

	private ConnectionManager() {
	}

	private Connection createConnection() {
		Connection connection = null;
		Properties props = new Properties();
		FileInputStream fis = null;
		try {
			URL url = getClass().getResource("dbProps.properties");
			fis = new FileInputStream(new File(url.getPath()));
			props.load(fis);
			Class.forName(props.getProperty("DB_DRIVER_CLASS"));
			connection = DriverManager.getConnection(
					props.getProperty("DB_URL"), props.getProperty("DB_USER"),
					props.getProperty("DB_PASSWORD"));
		} catch (SQLException | IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (!fis.equals(null)) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return connection;
	}

	public static Connection getConnection() {
		return instance.createConnection();
	}
}