package com.hcl.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class ProductServiceApplication {
			public static void main (String[]args) throws ClassNotFoundException, SQLException {
			SpringApplication.run(ProductServiceApplication.class, args);
			/*Class.forName("com.mysql.jdbc.Driver");
			Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/product_schema", "root", "root2");
			System.out.println("connection is achived....2");*/
	}
}