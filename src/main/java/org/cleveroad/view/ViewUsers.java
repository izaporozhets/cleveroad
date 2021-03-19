package org.cleveroad.view;

import org.cleveroad.entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ViewUsers {
    private JPanel rootPanel;
    private JTable usersTable;
    private final List<User> userList;

    public ViewUsers(List<User> userList){
        this.userList = userList;
        createTable();
    }

    public JPanel getRootPanel(){
        return rootPanel;
    }

    private void createTable(){
        Object[][] data = new Object[userList.size()][];
        for(int i = 0; i < userList.size(); i++){
            data[i] = new Object[]{
                    userList.get(i).getId(),
                    userList.get(i).getName(),
                    userList.get(i).getSurname(),
                    userList.get(i).getEmail(),
                    userList.get(i).getPhone()};
        }
        usersTable.setModel(new DefaultTableModel(
                data,
                new String[]{"ID","First name","Second name","Email","Phone"}
        ));
    }

}
