package Aerospike.Examples;

import java.security.MessageDigest;
import java.util.ArrayList;

public class UserController {
	public void create(String name, Integer age, ArrayList<String> addresses, String login, String password) {
		String md5password = getMD5(password);
		if ("".equals(md5password)) {
			System.out.println("Erro durante a codificacao MD5");
			return;
		}
		UserDAO userDAO = UserDAO.getInstance();
		userDAO.create(name, age, addresses, login, md5password);
	}

	public void readByLogin(String login) {
		UserDAO userDAO = UserDAO.getInstance();
		UserModel user = userDAO.readByLogin(login);
		if (user.getLogin() != null) {
			System.out.println("Usuario encontrado. Info:");
			System.out.println("Name:		" +
								user.getName());
			System.out.println("Age:		" +
								user.getAge());
			int addressNumber = 1;
			for (String address : user.getAddresses()) {
				System.out.println("Address" + addressNumber++ + ":	" +
									address);
			}
			System.out.println("Login:		" + user.getLogin());
			System.out.println("Password:	" + user.getPassword());
		} else {
			System.out.println("Usuario nao encontrado.");
		}
	}

	public void updateAddAddress(String login, String address) {
		UserDAO userDAO = UserDAO.getInstance();
		userDAO.updateAddAddress(login, address);
	}
	
	public void updateRemoveAddress(String login, String address) {
		UserDAO userDAO = UserDAO.getInstance();
		userDAO.updateRemoveAddress(login, address);
	}

	public void deleteUser(String login) {
		UserDAO userDAO = UserDAO.getInstance();
		userDAO.deleteUser(login);
	}
	
	public String getMD5(String password) {
		try {
			final MessageDigest digest = MessageDigest.getInstance("md5");
			digest.update(password.getBytes());
			final byte[] bytes = digest.digest();
			final StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(String.format("%02X", bytes[i]));
			}
			return sb.toString();
		} catch (Exception exc) {
			return "";
		}
	}
}
