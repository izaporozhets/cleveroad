package org.cleveroad.service;

import org.cleveroad.entity.User;
import org.cleveroad.util.DBUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;

import javax.sql.DataSource;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class UserService implements UserDAO {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private final DataSource dataSource = DBUtil.getDataSource();

    public void init(Integer amount){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://randomuser.me/api/?results="+ amount +"&inc=name,email,phone&noinfo"))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(this::fillDB)
                .join();
    }

    public void fillDB(String responseBody){
        responseBody = responseBody.substring(11);
        try{
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            String sql = "INSERT INTO users VALUES (null , ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            JSONArray users = new JSONArray(responseBody);
            for(int i = 0; i < users.length(); i++){
                JSONObject user = users.getJSONObject(i);
                preparedStatement.setString(1, user.getJSONObject("name").getString("first"));
                preparedStatement.setString(2, user.getJSONObject("name").getString("last"));
                preparedStatement.setString(3, user.getString("email"));
                preparedStatement.setString(4, user.getString("phone"));
                preparedStatement.executeUpdate();
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> findAll() {
        List<User> userList = new LinkedList<>();
        try {
            connection = dataSource.getConnection();
            String sql = "SELECT * FROM users";
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Integer id = resultSet.getInt(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                String email = resultSet.getString(4);
                String phone = resultSet.getString(5);
                userList.add(new User(id,firstName,lastName,email,phone));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public void save() {

    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }


    @Override
    public int count() {
        return 0;
    }
}
