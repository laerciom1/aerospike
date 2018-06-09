package Aerospike.Examples;

import java.util.ArrayList;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.query.Filter;
import com.aerospike.client.query.RecordSet;
import com.aerospike.client.query.Statement;

public class UserDAO {
	public Integer lastId;
	private AerospikeClient client;
	private static UserDAO instance;
	
	private UserDAO () {
		this.client = new AerospikeClient("127.0.0.1", 3000);
		this.client.writePolicyDefault.sendKey = true;
	}
	
	public static UserDAO getInstance() {
		if (instance == null) {
			instance = new UserDAO();
			return instance;
		}
		return instance;
	}
	
	public void create(String name, Integer age, ArrayList<String> addresses, String login, String password) {    	
    	Integer id = getLastId();
    	Key key = new Key("aerospike", "users", id);

    	Bin nameBin = new Bin("Name", name);
    	Bin ageBin = new Bin("Age", age);
    	Bin addressBin = new Bin("Addresses", addresses);
    	Bin loginBin = new Bin("Login", login);
    	Bin passwordBin = new Bin("Password", password);
    	
    	this.client.put(null, key, nameBin, ageBin, addressBin, loginBin, passwordBin);
	}
	
	@SuppressWarnings("unchecked")
	public UserModel readByLogin(String login) {
		UserModel user = new UserModel();
		Statement stmt = new Statement();
		stmt.setNamespace("aerospike");
		stmt.setSetName("users");
		stmt.setFilters(Filter.equal("Login", login));
		
		RecordSet rs = client.query(null, stmt);
		try {
		    while (rs.next()) {
		        Record record = rs.getRecord();
		        user.setName(record.getString("Name"));
		        user.setAge(record.getInt("Age"));
		        user.setAddresses((ArrayList<String>)record.getList("Addresses"));
		        user.setLogin(record.getString("Login"));
		        user.setPassword(record.getString("Password"));
		    }
		}
		finally {
		    rs.close();
		}
		return user;
	}
	
	@SuppressWarnings("unchecked")
	public void updateAddAddress(String login, String address) {
		Statement stmt = new Statement();
		stmt.setNamespace("aerospike");
		stmt.setSetName("users");
		stmt.setFilters(Filter.equal("Login", login));
		
		RecordSet rs = client.query(null, stmt);
		try {
		    while (rs.next()) {
		    	Key key = rs.getKey();
		        Record record = rs.getRecord();
		        ArrayList<String> addresses = (ArrayList<String>) record.getList("Addresses");
		        addresses.add(address);
		        Bin addressBin = new Bin("Addresses", addresses);
		        this.client.put(null, key, addressBin);
		    }
		}
		finally {
		    rs.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void updateRemoveAddress(String login, String address) {
		Statement stmt = new Statement();
		stmt.setNamespace("aerospike");
		stmt.setSetName("users");
		stmt.setFilters(Filter.equal("Login", login));
		
		RecordSet rs = client.query(null, stmt);
		try {
		    while (rs.next()) {
		    	Key key = rs.getKey();
		        Record record = rs.getRecord();
		        ArrayList<String> addresses = (ArrayList<String>) record.getList("Addresses");
		        for (int i = 0; i < addresses.size(); i++) {
		        	if (addresses.get(i).equals(address)) {
		        		addresses.remove(i);
		        	}
		        }
		        Bin addressBin = new Bin("Addresses", addresses);
		        this.client.put(null, key, addressBin);
		    }
		}
		finally {
		    rs.close();
		}
	}
	
	public void deleteUser(String login) {
		Statement stmt = new Statement();
		stmt.setNamespace("aerospike");
		stmt.setSetName("users");
		stmt.setFilters(Filter.equal("Login", login));
		
		RecordSet rs = client.query(null, stmt);
		try {
		    while (rs.next()) {
		    	Key key = rs.getKey();
		        this.client.delete(null, key);
		    }
		}
		finally {
		    rs.close();
		}
	}
	
	public int getLastId() {
		int lastId=1;
		Statement stmt = new Statement();
		stmt.setNamespace("aerospike");
		stmt.setSetName("users");
		
		RecordSet rs = this.client.query(null, stmt);
		try {
		    while (rs.next()) {
		    	lastId++;
		    }
		}
		finally {
		    rs.close();
		}
		return lastId;
	}
}
